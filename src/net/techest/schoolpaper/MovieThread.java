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
 *  Created on : 2010-10-28, 23:02:17
 *  Author     : princehaku
 */
package net.techest.schoolpaper;

import android.app.Activity;
import net.techest.schoolpaper.MainActivity;

/**用于显示动画
 *
 * @author princehaku
 */
public class MovieThread extends Thread {

    /**资源
     *
     */
    private static Activity res;
    /**扫描线y值
     *
     */
    private int lasty = 0;
    /**扫描线方向
     *
     */
    private int direction = 0;

    public MovieThread(Activity res) {
        MovieThread.res = res;
    }
    /**用于外部终止线程
     *
     */
    private boolean isEnd = false;

    @Override
    public void run() {
        while (1 == 1 && isEnd == false) {
            this.nextFrame();
            ((MainActivity) res).cacheUpdateHandler.sendEmptyMessage(1);
        }
    }

    /**下一帧动画
     *
     */
    public void nextFrame() {

        if (direction == 0) {
            lasty -= 3;
            if (lasty <= 0) {
                direction = 1;
            }
        } else {
            lasty += 3;
            if (lasty >= ((MainActivity) res).polygon.getHeight()) {
                direction = 0;
            }
        }
        //Log.i("","line at:"+lasty);
        ((MainActivity) res).polygon.drawScanLine(lasty);
    }

    public boolean isIsEnd() {
        return isEnd;
    }

    public void setIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }
}
