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
import android.os.Bundle;
import android.widget.TextView;
import net.techest.schoolpaper.util.XmlToPapers;

/**
 *
 * @author princehaku
 */
public class PaperActivity extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.paperview);
        XmlToPapers xp=new XmlToPapers(this);
        ((TextView)findViewById(R.id.cc2)).setScrollContainer(true);
        ((TextView)findViewById(R.id.cc2)).setText("ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ToDo add your GUI initialization code here    ");
        // ToDo add your GUI initialization code here
        xp.parse();
    }

}
