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
 *  Created on : 2010-11-4, 8:08:00
 *  Author     : princehaku
 */

package net.techest.schoolpaper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import net.techest.schoolpaper.paper.Paper;
import net.techest.schoolpaper.util.XmlToPapers;

/**
 *
 * @author princehaku
 */
public class PaperActivity extends Activity {
    //警告窗口
    public AlertWindow alert;
    /**当前查看的纸片序号
     *
     */
    private static int nowIndex=0;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        alert=new AlertWindow(this);
        setContentView(R.layout.paperview);
        // ToDo add your GUI initialization code here
        setViewContent(nowIndex);
    }
    private void setViewContent(int idx){
        ArrayList<Paper> papers=PublicData.papers;
        ((TextView)findViewById(R.id.cc2)).setScrollContainer(true);
        ((TextView)findViewById(R.id.title)).setText(""+papers.get(idx).getTitle());
        ((TextView)findViewById(R.id.date)).setText(""+papers.get(idx).getAddDate());
        ((TextView)findViewById(R.id.cc2)).setText(papers.get(idx).getContent());
        Log.v("","http://schoolpaper.techest.net/"+papers.get(idx).getImagePath());
        ((ImageView)findViewById(R.id.thumb)).setImageURI(Uri.parse("http://schoolpaper.techest.net"+papers.get(idx).getImagePath()));
    }
    /**按下后退的时候返回到主界面
     *
     */
    @Override
    public void onPause(){
      super.onPause();
      Intent intent = new Intent();
      intent.setClass(this, MainActivity.class);
      this.startActivity(intent);
      this.finish();
    }

}
