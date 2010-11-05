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
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        alert=new AlertWindow(this);
        setContentView(R.layout.paperview);
        XmlToPapers xp=new XmlToPapers("http://schoolpaper.techest.net/getPoints.php?x1=30.672509&x2=30.673551&y1=104.140208&y2=104.141324");
        ArrayList<Paper> papers=null;
        try {
            papers = xp.parse();
        } catch (Exception ex) {

        }
        ((TextView)findViewById(R.id.cc2)).setScrollContainer(true);
        ((TextView)findViewById(R.id.title)).setText(""+papers.get(0).getTitle());
        ((TextView)findViewById(R.id.date)).setText(""+papers.get(0).getAddDate());
        ((TextView)findViewById(R.id.cc2)).setText(papers.get(0).getContent());
        Log.v("","http://schoolpaper.techest.net/"+papers.get(0).getImagePath());
        ((ImageView)findViewById(R.id.thumb)).setImageURI(Uri.parse("http://schoolpaper.techest.net"+papers.get(0).getImagePath()));
        // ToDo add your GUI initialization code here
    }
    @Override
    public void onPause(){
        this.finish();
    }

}
