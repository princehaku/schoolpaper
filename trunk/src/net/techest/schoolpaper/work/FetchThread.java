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
 *  Created on : 2010-10-29, 23:44:24
 *  Author     : princehaku
 */
package net.techest.schoolpaper.work;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import net.techest.schoolpaper.MainActivity;
import net.techest.schoolpaper.PublicData;
import net.techest.schoolpaper.paper.Paper;
import net.techest.schoolpaper.util.XmlToPapers;

/**得到从服务器返回的数据
 *
 * @author princehaku
 */
public class FetchThread extends Thread {

    /**资源
     *
     */
    private static Activity res;
    
    double pointx1;
    double pointy1;
    double pointx2;
    double pointy2;
    
    public FetchThread(Activity res,double x1,double x2,double y1,double y2) {
        FetchThread.res = res;
        //换算成标准的XX.XXXXXXX
        this.pointx1=x1/1000000;
        this.pointy1=y1/1000000;
        this.pointx2=x2/1000000;
        this.pointy2=y2/1000000;
    }
    /**从服务器获取数据并显示
     *
     */
    @Override
    public void run() {
        //HttpConnecter c=new HttpConnecter();
        try {
            XmlToPapers xp=new XmlToPapers("http://schoolpaper.techest.net/getPoints.php?x1="+this.pointx1
                    +"&x2="+this.pointx2+"&y1="+this.pointy1+"&y2="+this.pointy2);
            Log.i("", "URL IS :http://schoolpaper.techest.net/getPoints.php?x1="+this.pointx1
                    +"&x2="+this.pointx2+"&y1="+this.pointy1+"&y2="+this.pointy2);
            PublicData.papers = xp.parse();
            for(int i=0;i<PublicData.papers.size();i++){
                Paper p=PublicData.papers.get(i);
                addOverlay(p);
            }
            //c.get("http://schoolpaper.techest.net/getPoints.php?x=123&y=123&w=123&h=123", "utf8");
        } catch (Exception ex) {
            Log.i("","Error connecting Server :"+ex.getMessage());
            ((MainActivity)res).alert.show("错误啦~", "对不起.连接服务器失败 T_T");
        }
        ((MainActivity)res).finishHandler.sendEmptyMessage(1);
    }
    /**增加一个纸片到地图视图
     *
     */
    private void addOverlay(Paper p){
        Message msg = new Message();
        Bundle ble = new Bundle();
        ble.putString("id",""+p.getId());
        ble.putString("x",""+p.getX());
        ble.putString("y",""+p.getY());
        ble.putString("title",p.getTitle());
        ble.putString("type",p.getType().name());
        ble.putString("deepth",""+p.getDeepth());
        msg.setData(ble);
        ((MainActivity)res).mapPointAddHandler.sendMessage(msg);
    }
}
