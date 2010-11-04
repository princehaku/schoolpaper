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
 *  Created on : 2010-10-27, 21:36:23
 *  Author     : princehaku
 */

package net.techest.schoolpaper;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import java.util.ArrayList;

/**多边形
 *
 * @author princehaku
 */
public class Polygon {
    /**这个位图绘图层
     *@return Bitmap 
     */
    private Bitmap drawBitmap;
    /**这个位图是缓冲层 用于动画
     *@return Bitmap 
     */
    private Bitmap cacheBitmap;
    /**缓冲画布层
     *@return Canvas
     */
    private Canvas canvasTemp;
    /**画布的高度
     *
     */
    private int width;
    /**画布的宽度
     *
     */
    private int height;
    /**画笔
     *
     */
    private Paint p;
    /**上个点的序号
     *
     */
    private int lastPoingIndex=0;
    /**多边形的顶点序列
     *
     */
    private ArrayList<PolygonPoint> points=new ArrayList();

    public Polygon() {
        //建立缓冲图像
        width = 320;height = 380;
        drawBitmap = Bitmap.createBitmap(width,height,Config.ARGB_8888);
        //创建一个画布 给绘图层使用
        canvasTemp = new Canvas(drawBitmap);
        //初始化画笔
        p = new Paint();
        p.setColor(Color.GRAY);
        p.setStrokeWidth(2);
    }
    /**重置图像为空
     * @deprecated
     */
    public void reset(){
        drawBitmap = Bitmap.createBitmap(width,height,Config.ARGB_8888);
        canvasTemp = new Canvas(drawBitmap);
        cacheBitmap=null;
        lastPoingIndex=0;
        points.clear();
    }
    /**得到绘图层
     *@return Bitmap
     */
    public Bitmap getBitmap(){
        this.update();
        return drawBitmap;
    }
    /**得到缓冲层 
     *@return Bitmap
     */
    public Bitmap getBitmapFromCache(){
        //第一次将原始位图写入缓冲区
        if(cacheBitmap==null){
            Log.i("","CacheBitmap Created");
            cacheBitmap=drawBitmap.copy(Config.ARGB_8888, true);
        }
        //之后每次永远都从缓冲层复制图像
        drawBitmap=cacheBitmap.copy(Config.ARGB_8888, true);

        return drawBitmap;
    }
    /**绘制一条扫描线
     *
     */
    public void drawScanLine(int y){
        
        canvasTemp = new Canvas(getBitmapFromCache());
        //画条直线
        p.setStrokeWidth(3);
        p.setColor(Color.GREEN);
        canvasTemp.drawLine(0, y,width, y, p);
        //画虚线
        p.setColor(Color.RED);
	int[] xs=new int[256];
	PolygonPoint pt0;
	PolygonPoint pt1;
        int cn = 0;
        for (int i = 0; i < points.size(); i++) {
            pt0 = points.get(i);
            int idx = (i + 1) % points.size();
            pt1 = points.get(idx);
            //跳过水平边
            if (pt0.y() == pt1.y()) {
                continue;
            }
            if (pt0.y() > pt1.y()) {
                //交换点坐标
                PolygonPoint pt = pt0;
                pt0 = pt1;
                pt1 = pt;
            }
            if (y == pt1.y()) {
                continue;
            }
            if (y >= pt0.y() && y <= pt1.y()) {
                int x = pt0.x() + (y - pt0.y()) * (pt1.x() - pt0.x()) / (pt1.y() - pt0.y());
                int j = 0;
                for (j = cn - 1; j >= 0; j--) {
                    if (x <= xs[j]) {
                        break;
                    }
                    xs[j + 1] = xs[j];
                }
                xs[j + 1] = x;
                cn++;
            }
        }
        for (int i = 0; i < cn; i += 2) {
            canvasTemp.drawLine(xs[i], y, xs[i + 1], y, p);
        }

    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
    /**判断点是否在图形内
     *
     */
    public boolean isInPolygon(double x,double y){
        
        return true;
    }
    /**用最后一个绘图的点更新缓冲区内的图像
     * 
     */
    public void update()
    {
        //前个点
        PolygonPoint p0 = null;
        //新点
        PolygonPoint p1;
        
        //Log.i("", "points :"+points.size());
        for(int i=lastPoingIndex;i<points.size();i++){
            if(i==lastPoingIndex)
            {
                p0=points.get(i);
            }
            p1=points.get(i);
            //从前个点画到新的点
            canvasTemp.drawLine(p0.x,p0.y, p1.x,p1.y,p);
            //新点变旧点
            p0=p1;
        }
        lastPoingIndex++;
    }
    /**画上封闭线 (建议在按键按起的时候调用)
     *
     */
    public void enClose(){
        canvasTemp.drawLine(points.get(0).x,points.get(0).y, points.get(points.size()-1).x,points.get(points.size()-1).y,p);
    }
    /**增加一个顶点
     *
     * @param x
     * @param y
     */
    public void addPoint(float x,float y){
        points.add(new PolygonPoint(x,y));
    }
    /**判断这个点附近是否有其他像素存在
     * @deprecated
     * @param X
     * @param Y
     * @return
     */
    boolean hasPixNearBy(float x, float y) {
                boolean noPixNearBy=false;
                for(int i=(int) (x - 2);i<(int)(x+2);i++){
                    for(int j=(int) (y - 2);j<(int)(y+2);j++){
                        try{
                            int pixColor=drawBitmap.getPixel(i, j);
                            if(pixColor==Color.GRAY){
                                noPixNearBy=true;
                            }
                        }catch(Exception ex){
                            Log.i("","Out of Range");
                        }
                    }
                }
                return noPixNearBy;
    }
    /**填充多边形
     *
     */
    public void fill() {

        int min,max;
	min=points.get(0).y();
	max=points.get(0).y();
	for(int i=0;i<points.size();i++)
	{
		if(min>points.get(i).y())min=points.get(i).y();
		if(max<points.get(i).y())max=points.get(i).y();
	}
	Log.i("","min:"+min+" - max:"+max);
	for(int u=min;u<max;u++)
	{
            drawScanLine(u);
	}

    }


}
