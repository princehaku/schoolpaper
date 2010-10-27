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
import java.util.ArrayList;

/**
 *
 * @author princehaku
 */
public class Graph {
    //这个位图是缓冲图.绘图的层
    private Bitmap bitmapCache;
    //画布的高度
    private int width;
    //画布的宽度
    private int height;
    //多边形的顶点
    private ArrayList<wPoint> points=new ArrayList();

    public Graph() {
        //建立缓冲图像
        width = 320;height = 380;
        bitmapCache = Bitmap.createBitmap(width,height,Config.ARGB_8888);
    }
    public Bitmap getBitmap(){
        this.update();
        return bitmapCache;
    }
    /**更新缓冲区内的图像
     *
     */
    public void update()
    {
        bitmapCache = Bitmap.createBitmap(width,height,Config.ARGB_8888);
        //创建一个画布 给缓冲层使用
        Canvas canvasTemp = new Canvas(bitmapCache);
        Paint p = new Paint();
        p.setColor(Color.CYAN);
        p.setStrokeWidth(3);
        //前个点
        wPoint p0 = null;
        //新点
        wPoint p1;
        for(int i=0;i<points.size();i++){
            if(i==0)
            {
                p0=points.get(i);
            }
            p1=points.get(i);
            //从前个点画到新的点
            canvasTemp.drawLine(p0.x,p0.y, p1.x,p1.y,p);
            //新点变旧点
            p0=p1;
        }
        //画上封闭线
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
