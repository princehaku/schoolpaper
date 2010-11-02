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
import net.techest.schoolpaper.MainActivity;
import net.techest.schoolpaper.net.HttpConnecter;

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
        Message msg = new Message();
        Bundle ble = new Bundle();
        ble.putString("x","30.673103");
        ble.putString("y","104.140734");
        ble.putString("title","副本1");
        ble.putString("type","1");
        ble.putString("description","description");
        ble.putString("deepth","5");
        msg.setData(ble);
        res.mapPointAddHandler.sendMessage(msg);
    }
}
