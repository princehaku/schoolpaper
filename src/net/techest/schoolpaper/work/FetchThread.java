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
    /**创建msg 并发送给主线程
     *
     */
    @Override
    public void run() {
        HttpConnecter c=new HttpConnecter();
        try {
            c.get("http://schoolpaper.techest.net/getPoints.php?", "utf8");
        } catch (Exception ex) {
            Log.i("","Error connecting Server :"+ex.getMessage());
        }
        res.finishHandler.sendEmptyMessage(1);
    }
    /**增加一个坐标到地图视图
     *
     * @param x 字符串为小数点前+小数点后6位组成 比如30.673103 => 30673103
     * @param y 字符串为小数点前+小数点后6位组成
     * @param title 标题
     * @param type 纸的类型
     * @param description
     * @param deepth
     */
    void addOverlay(String x,String y,String title,PaperType type,String description,String deepth){
        Message msg = new Message();
        Bundle ble = new Bundle();
        ble.putString("x","30.673103");
        ble.putString("y","104.140734");
        ble.putString("title","副本1");
        ble.putString("type",type.name());
        ble.putString("description","description");
        ble.putString("deepth","5");
        msg.setData(ble);
        res.mapPointAddHandler.sendMessage(msg);
    }
}
