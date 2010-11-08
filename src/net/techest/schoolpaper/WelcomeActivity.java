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
 *  Created on : 2010-11-8, 11:17:58
 *  Author     : princehaku
 */

package net.techest.schoolpaper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import java.util.Timer;
import java.util.TimerTask;
import net.techest.schoolpaper.util.XmlToPapers;

/**欢迎界面
 *
 * @author princehaku
 */
public class WelcomeActivity extends Activity {
    //进度条的进度
    static int idx=0;
    //进度条
    protected ProgressBar myProgressBar;
    
    private XmlToPapers XmlToPapers;
    
    Timer tr;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // ToDo add your GUI initialization code here
        setContentView(R.layout.welcome);
        myProgressBar=(ProgressBar)findViewById(R.id.loading);
        //持续滚动
        tr=new Timer();
        tr.schedule(new TimerTask(){
            @Override
            public void run() {
                WelcomeActivity.idx++;
                process.sendEmptyMessage(WelcomeActivity.idx);
            }
        }, 0,100);
        //加载最初点
        XmlToPapers =new XmlToPapers(getString(R.string.serverbase)+"getPoints.php?f=1");

    }
    private Handler process=new HandlerWelcomeProc(this);

}
