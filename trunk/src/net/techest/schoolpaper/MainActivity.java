/*  Copyright 2010 princehaku
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  Created on : 2010-10-18, 21:05:57
 *  Author     : princehaku
 */
package net.techest.schoolpaper;

import net.techest.schoolpaper.common.PublicData;
import net.techest.schoolpaper.graph.Polygon;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import java.util.List;
import net.techest.schoolpaper.paper.Paper;
import net.techest.schoolpaper.paper.PaperOverlay;

/**
 *
 * @author princehaku
 */
public class MainActivity extends MapActivity implements OnTouchListener {

    protected MapView map;
    //测试用的文本框
    private TextView infoBar;
    //测试用的文本框
    private TextView searchText;
    //多边形
    protected Polygon polygon;
    //地图上的渲染层
    private ImageView renderMapContainer;
    //动画线程
    MovieThread t;
    //c/s模块的请求线程
    FetchThread f;
    //警告窗口
    protected AlertWindow alert;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        alert = new AlertWindow(this);
        setContentView(R.layout.mapview);
        searchText = (TextView) findViewById(R.id.searchtext);
        infoBar = (TextView) findViewById(R.id.infoBar);
        //初始化圈圈的画布
        renderMapContainer = (ImageView) findViewById(R.id.render_map);
        renderMapContainer.setLongClickable(true);
        renderMapContainer.setOnTouchListener((OnTouchListener) this);
        //初始化地图
        map = (MapView) findViewById(R.id.my_map);
        if (PublicData.centerPoint == null) {
            map.getController().setCenter(new GeoPoint(30674949, 104143127));
        } else {
            map.getController().setCenter(PublicData.centerPoint);
        }
        map.getController().setZoom(18);
        map.setBuiltInZoomControls(true);
        map.setLongClickable(true);
        map.setFocusable(true);
        map.setClickable(true);
        //根据map虚拟化一张画布填充至画布层
        map.setDrawingCacheEnabled(false);
        renderMapContainer.setVisibility(View.INVISIBLE);
        //绑定圈圈的点击事件
        ((Button) findViewById(R.id.quan)).setOnClickListener(
                new OnClickListener() {

                    public void onClick(View v) {
                        renderMapContainer.setFocusableInTouchMode(true);
                        renderMapContainer.setFocusable(true);
                        renderMapContainer.requestFocus();
                        if (f != null && f.isAlive()) {
                            return;
                        }
                        map.setClickable(false);
                        renderMapContainer.setVisibility(View.INVISIBLE);
                        //清空已经有的坐标
                        map.getOverlays().clear();
                        //更新下画布
                        PointStatu.reset();
                        if (polygon == null) {
                            polygon = new Polygon();
                        } else {
                            renderMapContainer.setImageBitmap(map.getDrawingCache());
                            polygon = new Polygon();
                        }
                        //使画布显示 覆盖在mapview之上 开始工作
                        renderMapContainer.setVisibility(View.VISIBLE);
                        renderMapContainer.setLongClickable(true);
                    }
                });
        //绑定搜索按钮的点击事件
        ((Button) findViewById(R.id.search)).setOnClickListener(
                new OnClickListener() {

                    public void onClick(View arg0) {
                        //隐藏软键盘
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        View view = getCurrentFocus();
                        if (view != null) {
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        if (searchText.getText().length() < 1) {
                            alert.show("注意", "关键词请输入大于一个字");
                            return;
                        }
                        alert.show("提示", "请稍后 正在为您搜索纸片");
                        PublicData.centerPoint=null;
                        //清空已经有的坐标
                        map.getOverlays().clear();
                        //从服务器获取信息
                        f = new FetchThread(MainActivity.this, searchText.getText().toString());
                        f.start();
                    }
                });
        //如果存在数据 重绘标点
        if (PublicData.papers != null) {
            for (int i = 0; i < PublicData.papers.size(); i++) {
                Paper p = PublicData.papers.get(i);
                addOverlay(p);
            }
        }
    }

    /**必须重载此方法
     *
     * @return
     */
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    /**这个方法是给地图层上的画图层 触摸的时候画线
     * 离开的时候计算并发送信息给服务器
     * @param v
     * @param event
     * @return
     */
    public boolean onTouch(View v, MotionEvent event) {
        infoBar.setText("");
        //在线程运行时禁止点击
        if (t != null && t.isAlive()) {
            return false;
        }
        PointStatu.X = event.getX();
        PointStatu.Y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                PointStatu.updatePoint(PointStatu.X, PointStatu.Y);
                polygon.addPoint(PointStatu.lastX, PointStatu.lastY);
                break;
            case MotionEvent.ACTION_MOVE:
                //如果当前点的x或者y范围超过5且5像素内无其他像素则画图并设置到新点
                if ((Math.abs(PointStatu.Y - PointStatu.lastY) > 5 || Math.abs(PointStatu.X - PointStatu.lastX) > 5)) {
                    PointStatu.updatePoint(PointStatu.X, PointStatu.Y);
                    polygon.addPoint(PointStatu.lastX, PointStatu.lastY);
                    //更新图像
                    renderMapContainer.setImageBitmap(polygon.getBitmap());
                }
                break;
            case MotionEvent.ACTION_UP:
                //保存地图中心坐标到公共数据区
                PublicData.centerPoint = map.getMapCenter();
                PointStatu.updatePoint(PointStatu.X, PointStatu.Y);
                renderMapContainer.setLongClickable(false);
                map.setClickable(false);
                //释放的时候计算坐标成地理坐标.上传给服务器
                GeoPoint p1 = map.getProjection().fromPixels((int) PointStatu.maxX, (int) PointStatu.maxY);
                GeoPoint p2 = map.getProjection().fromPixels((int) PointStatu.minX, (int) PointStatu.minY);
                GeoPoint temp = new GeoPoint(0, 0);
                Log.i("", "minX:" + (int) PointStatu.minX + "maxX:" + (int) PointStatu.maxX + "minY:" + (int) PointStatu.minY + "maxY:" + (int) PointStatu.maxY);
                //infoBar.setText(p1.toString());
                //画封闭线
                polygon.enClose();
                //开始动画
                t = new MovieThread(this);
                t.start();
                //从服务器获取信息
                f = new FetchThread(this, p1.getLatitudeE6() - temp.getLatitudeE6(),
                        p2.getLatitudeE6() - temp.getLatitudeE6(), p2.getLongitudeE6() - temp.getLongitudeE6(), p1.getLongitudeE6() - temp.getLongitudeE6());
                f.start();
                break;
        }
        return false;
    }
    /**更新渲染层图像
     *
     */
    public Handler cacheUpdateHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    renderMapContainer.setImageBitmap(polygon.getBitmap());
                    break;
            }
            super.handleMessage(msg);
        }
    };
    /**终止动画线程
     *
     */
    public Handler finishHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            //终止动画线程
            if (t != null) {
                t.setIsEnd(true);
                while (t.isAlive()) {
                    Log.i("", "Wait for Movie Thread to end");
                }
                t = null;
            }
            f = null;
            PointStatu.reset();
            renderMapContainer.setVisibility(View.INVISIBLE);
            //-------------
            map.setClickable(true);
            map.requestFocus();
            if (PublicData.centerPoint != null) {
                map.getController().setCenter(PublicData.centerPoint);
            }
            alert.destory();
            if(PublicData.papers.isEmpty()){
                alert.show("Oh~", "对不起..一张纸片都没有找到 T T");
            }
        }
    };
    /**在地图上增加信息点
     *
     */
    public Handler mapPointAddHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            String id = (String) Message.obtain(msg).getData().get("id");
            String x = (String) Message.obtain(msg).getData().get("x");
            String y = (String) Message.obtain(msg).getData().get("y");
            String title = (String) Message.obtain(msg).getData().get("title");
            String type = (String) Message.obtain(msg).getData().get("type");
            String deepth = (String) Message.obtain(msg).getData().get("deepth");
            List<Overlay> mapOverlays = map.getOverlays();
            //处理色深
            Drawable drawable = getResources().getDrawable(R.drawable.overlay0);

            if (deepth.equals("1")) {
                drawable = getResources().getDrawable(R.drawable.overlay1);
            }
            if (deepth.equals("2")) {
                drawable = getResources().getDrawable(R.drawable.overlay2);
            }
            if (deepth.equals("3")) {
                drawable = getResources().getDrawable(R.drawable.overlay3);
            }
            if (deepth.equals("4")) {
                drawable = getResources().getDrawable(R.drawable.overlay4);
            }
            if (deepth.equals("5")) {
                drawable = getResources().getDrawable(R.drawable.overlay5);
            }
            //增加坐标
            PaperOverlay itemizedoverlay = new PaperOverlay(drawable, map.getContext(), MainActivity.this, Integer.parseInt(id));
            GeoPoint point = new GeoPoint(Integer.parseInt(x), Integer.parseInt(y));
            //PublicData.centerPoint = point;
            OverlayItem overlayitem = new OverlayItem(point, title, "");
            itemizedoverlay.addOverlay(overlayitem);
            mapOverlays.add(itemizedoverlay);
            super.handleMessage(msg);
        }
    };

    /**增加一个纸片到地图视图
     *
     */
    private void addOverlay(Paper p) {
        Message msg = new Message();
        Bundle ble = new Bundle();
        ble.putString("id", "" + p.getId());
        ble.putString("x", "" + p.getX());
        ble.putString("y", "" + p.getY());
        ble.putString("title", p.getTitle());
        ble.putString("type", p.getType().name());
        ble.putString("deepth", "" + p.getDeepth());
        msg.setData(ble);
        this.mapPointAddHandler.sendMessage(msg);
    }
}
