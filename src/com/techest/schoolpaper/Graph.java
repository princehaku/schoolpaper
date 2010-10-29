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

package com.techest.schoolpaper;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import java.util.ArrayList;

/**
 *
 * @author princehaku
 */
public class Graph {
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
    /**上次动画的y值
     *
     */
    private int lasty=0;
    /**上个点的序号
     *
     */
    private int lastPoingIndex=0;
    /**扫描线方向
     *
     */
    private int direction=0;
    /**多边形的顶点序列
     *
     */
    private ArrayList<wPoint> points=new ArrayList();

    public Graph() {
        //建立缓冲图像
        width = 320;height = 380;
        drawBitmap = Bitmap.createBitmap(width,height,Config.ARGB_8888);
        //创建一个画布 给绘图层使用
        canvasTemp = new Canvas(drawBitmap);
        //初始化画笔
        p = new Paint();
        p.setColor(Color.CYAN);
        p.setStrokeWidth(3);
    }
    /**重置图像为空
     * 
     */
    public void reset(){
        drawBitmap = Bitmap.createBitmap(width,height,Config.ARGB_8888);
        cacheBitmap=null;
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
    private Bitmap getBitmapFromCache(){
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
        //扫描线附进的多边形顶点
        ArrayList<wPoint> nearPoints=new ArrayList();
        //扫描线与多边形顶点的交点
        ArrayList<wPoint> linePoints=new ArrayList();
        if(y<0)y=0;
        if(y>=height)y=height-1;
        int color1=cacheBitmap.getPixel(0, y);
        int color2=cacheBitmap.getPixel(0, y);
        //计算并统计附近可能存在的多边形顶点
        for(int i=0;i<points.size();i++){
            if(Math.abs(points.get(i).y-y)<=5){
                nearPoints.add(points.get(i));
            }
        }
        //记录交点
        for(int i=0;i<width;i++){
            
            color2=cacheBitmap.getPixel(i, y);

            int cross=0;

            for(int j=0;j<nearPoints.size();j++){
                if(nearPoints.get(j).x==i){
                    cross=1;
                }
            }
            //如果颜色发生了变化 且变化期间未通过任意一个附近的多边形顶点
            if(color2!=color1&&cross==0){
                //记录点
                linePoints.add(new wPoint(i, y));
            }
        }
        for(int i=0;i<linePoints.size();i++){
            //总是从奇数点向偶数点画线
            if(i%2==0&&i!=0){
                canvasTemp.drawLine(linePoints.get(i-1).x, y,linePoints.get(i).x, y, p);
            }
        }

    }
    /**下一帧动画
     *
     */
    public void nextFrame(){
        
        canvasTemp = new Canvas(getBitmapFromCache());

        if(direction==0){
            p.setColor(Color.GREEN);
            lasty-=5;
            Log.i("", lasty+"");
            if(lasty<=0){
                direction=1;
            }
        }
        else{
            p.setColor(Color.RED);
            lasty+=5;
            Log.i("", lasty+"");
            if(lasty>=height){
                direction=0;
                Log.i("","Change ");
            }
        }
        drawScanLine(lasty);
    }
    /**用最后一个绘图的点更新缓冲区内的图像
     * 
     */
    public void update()
    {
        //前个点
        wPoint p0 = null;
        //新点
        wPoint p1;
        
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
        points.add(new wPoint(x,y));
    }


}
