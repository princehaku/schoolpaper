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

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import net.techest.schoolpaper.MainActivity;
import net.techest.schoolpaper.net.HttpConnecter;
import net.techest.schoolpaper.paper.Paper;
import net.techest.schoolpaper.paper.PaperType;

/**得到从服务器返回的数据
 *
 * @author princehaku
 */
public class FetchThread extends Thread {

    /**资源
     *
     */
    private static MainActivity res;

    public FetchThread(MainActivity res) {
        FetchThread.res = res;
    }
    /**从服务器获取数据并显示
     *
     */
    @Override
    public void run() {
        HttpConnecter c=new HttpConnecter();
        try {
            c.get("http://schoolpaper.techest.net/getPoints.php?", "utf8");
            
        } catch (Exception ex) {
            Log.i("","Error connecting Server :"+ex.getMessage());
            res.alert.show("错误啦~", "对不起.连接服务器失败 T_T");
        }
        res.finishHandler.sendEmptyMessage(1);
    }
    /**增加一个纸片到地图视图
     *
     */
    void addOverlay(Paper p){
        Message msg = new Message();
        Bundle ble = new Bundle();
        ble.putString("x",""+p.getX());
        ble.putString("y",""+p.getY());
        ble.putString("title",p.getTitle());
        ble.putString("type",p.getType().name());
        ble.putString("description",p.getContent());
        ble.putString("deepth",""+p.getDeepth());
        msg.setData(ble);
        res.mapPointAddHandler.sendMessage(msg);
    }
}
