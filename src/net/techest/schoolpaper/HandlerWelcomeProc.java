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
 *  Created on : 2010-11-8, 14:10:39
 *  Author     : princehaku
 */

package net.techest.schoolpaper;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

/**处理欢迎界面的proc消息
 *
 * @author princehaku
 */
 class HandlerWelcomeProc extends Handler {

        private static Activity res;

        public HandlerWelcomeProc(Activity aThis) {
             this.res = aThis;
        }
        @Override
        public void handleMessage(Message msg) {
            int prc = msg.what;
            if(prc > 0){
                ((WelcomeActivity)res).tips.setText("Tips: "+res.getString(R.string.tips1));
            }
            if(prc > 50){
                ((WelcomeActivity)res).tips.setText("Tips: "+res.getString(R.string.tips2));
            }
            if (prc > 100) {
                /*加载最初点
                XmlToPapers xp=new XmlToPapers(res.getString(R.string.serverbase)+"getPoints.php?f=1");
                try {
                    PublicData.papers=xp.parse();
                } catch (Exception ex) {
                    Log.i("","First data Read error");
                }*/
                //启动主界面
                Intent intent = new Intent();
                intent.setClass(res, MainActivity.class);
                res.startActivity(intent);
                if(((WelcomeActivity)res).tr!=null)((WelcomeActivity)res).tr.cancel();
                ((WelcomeActivity)res).finish();
            }
            ((WelcomeActivity)res).myProgressBar.setProgress(prc);
        }
    }