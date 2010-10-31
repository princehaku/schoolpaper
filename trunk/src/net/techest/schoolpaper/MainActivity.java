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

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author princehaku
 */
public class MainActivity extends MapActivity implements OnTouchListener{

    private MapView map;
    //测试用的文本框
    private TextView mTextView01;
    //
    Polygon polygon;
    //
    private ImageView renderMapContainer;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.mapview);
        mTextView01 = (TextView) findViewById(R.id.TextView01);
        polygon=new Polygon();
        //初始化圈圈的画布
        renderMapContainer=(ImageView)findViewById(R.id.render_map);
        renderMapContainer.setLongClickable(true);
        renderMapContainer.setOnTouchListener((OnTouchListener)this);
        //初始化地图
        map = (MapView)findViewById(R.id.my_map);
        map.getController().setCenter(new GeoPoint(30673894,104143757));
        map.getController().setZoom(18);
        map.setBuiltInZoomControls(true);
        map.setLongClickable(true);
        map.setFocusable(true);
        map.setClickable(true);
        //根据map虚拟化一张画布填充至画布层
        map.setDrawingCacheEnabled(true);
        renderMapContainer.setImageBitmap(map.getDrawingCache());
        renderMapContainer.setVisibility(View.INVISIBLE);
        //这里是圈圈的点击事件
        ((Button)findViewById(R.id.quan)).setOnClickListener(

         new OnClickListener(){

            public void onClick(View v) {
                //使画布显示 覆盖在mapview之上 开始工作
                renderMapContainer.setVisibility(View.VISIBLE);
            }
            }
        );
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
        //mTextView01.setText(event.getAction()+"-"+event.getPressure()+"");
        PointStatu.X=event.getX();
        PointStatu.Y=event.getY();
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                PointStatu.lastX=PointStatu.X;
                PointStatu.lastY=PointStatu.Y;
                polygon.addPoint(PointStatu.lastX, PointStatu.lastY);
                break;
            case MotionEvent.ACTION_MOVE:
                //如果当前点的x或者y范围超过5且5像素内无其他像素则画图并设置到新点
                if((Math.abs(PointStatu.Y-PointStatu.lastY)>5||Math.abs(PointStatu.X-PointStatu.lastX)>5)){
                    PointStatu.lastX=PointStatu.X;
                    PointStatu.lastY=PointStatu.Y;
                    polygon.addPoint(PointStatu.lastX, PointStatu.lastY);
                    //更新图像
                    renderMapContainer.setImageBitmap(polygon.getBitmap());
                }
                break;
            case MotionEvent.ACTION_UP:
                renderMapContainer.setLongClickable(false);
                map.setClickable(false);
                //释放的时候计算坐标成地理坐标.上传给服务器
                GeoPoint p1= map.getProjection().fromPixels((int)PointStatu.X,(int)PointStatu.Y);
                //mTextView01.setText(p1.toString());
                //画封闭线
                polygon.enClose();
                //开始动画
                MovieThread t=new MovieThread(this);
                //t.start();
                FetchThread f=new FetchThread(this);
                f.start();
                break;
        }
        return false;
    }

    public Handler cacheUpdateHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:
                    renderMapContainer.setImageBitmap(polygon.getBitmap());
                    break;
            }
            super.handleMessage(msg);
        }
    };
    
    public Handler mapPointUpdateHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            map.setClickable(true);
            String x =(String)Message.obtain(msg).getData().get("x");
            String y =(String)Message.obtain(msg).getData().get("y");
            String title =(String)Message.obtain(msg).getData().get("title");
            String description =(String)Message.obtain(msg).getData().get("description");
            String deepth =(String)Message.obtain(msg).getData().get("deepth");
            mTextView01.setText(title.toString());
            List<Overlay> mapOverlays = map.getOverlays();
            Drawable drawable = getResources().getDrawable(R.drawable.paper0);
            PaperOverlay itemizedoverlay = new PaperOverlay(drawable,map.getContext());
            GeoPoint point = new GeoPoint(30673390,104140412);
            OverlayItem overlayitem = new OverlayItem(point, title, description);
            itemizedoverlay.addOverlay(overlayitem);
            mapOverlays.add(itemizedoverlay);
            super.handleMessage(msg);
        }
    };
}
