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
 *  Created on : 2010-10-25, 21:17:29
 *  Author     : princehaku
 */

package com.techest.schoolpaper;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

/**
 *
 * @author princehaku
 */
public class TestActivity extends Activity  implements OnTouchListener{

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // ToDo add your GUI initialization code here
        setContentView(R.layout.main);
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setLongClickable(true);
        tv.setOnTouchListener((OnTouchListener) this);
    }
    public boolean onTouch(View v, MotionEvent event) {
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText(event.getAction()+"");
         return false;
     }  

}
