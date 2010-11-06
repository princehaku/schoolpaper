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
 *  Created on : 2010-11-5, 10:09:19
 *  Author     : princehaku
 */
package net.techest.schoolpaper;

import com.google.android.maps.GeoPoint;
import java.util.ArrayList;
import net.techest.schoolpaper.paper.Paper;

/**存放Activity的公共数据
 *
 * @author princehaku
 */
public class PublicData {

    /**纸片数据
     * 
     */
    public static ArrayList<Paper> papers = null;
    /**当前查看的纸片的id号
     *
     */
    public static int nowPaperId = 0;
    /**当前地图中心
     *
     */
    public static GeoPoint centerPoint = null;

    /**重设所有数据
     *
     */
    public void reset() {
        papers = null;
        nowPaperId = 0;
        centerPoint = null;
    }
}
