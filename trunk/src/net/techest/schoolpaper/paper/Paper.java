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
 *  Created on : 2010-11-2, 21:49:13
 *  Author     : princehaku
 */
package net.techest.schoolpaper.paper;

import android.graphics.Point;
import android.util.Log;
import java.util.StringTokenizer;

/**纸片
 *
 * @author princehaku
 */
public class Paper extends Point {

    private int id;
    /**纸片类型
     *
     */
    private PaperType type;
    /**标题
     *
     */
    private String title;
    /**图片名称
     *
     */
    private String imageName;
    /**内容
     *
     */
    private String content;
    /**色深
     *
     */
    private int deepth;
    /**纸片的时间
     *
     */
    private String paperDate;
    /**填加的时间
     *
     */
    private String addDate;
    /**图片路径
     *
     */
    private String imagePath;

    public String getImagePath() throws Exception {
        StringTokenizer st = new StringTokenizer(getAddDate(), " ");
        imagePath = "uploads/" + st.nextToken().replaceAll("-", "") + "/";
        Log.i("", imagePath);
        return imagePath;
    }

    public Paper() {
    }

    /**
     * @param id
     * @param x 字符串为小数点前+小数点后6位组成 比如30.673103 => 30673103
     * @param y 字符串为小数点前+小数点后6位组成 比如30.673103 => 30673103
     * @param title 标题
     * @param type 纸的类型
     * @param content 全文
     * @param paperDate
     * @param deepth
     */
    public Paper(int id, int x, int y, PaperType type, String title, String imageName, String content, String addDate, int deepth) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.type = type;
        this.imageName = imageName;
        this.title = title;
        this.content = content;
        this.paperDate = addDate;
        this.deepth = deepth;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getPaperDate() {
        return paperDate;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setPaperDate(String paperDate) {
        this.paperDate = paperDate;
    }

    public int getDeepth() {
        return deepth;
    }

    public void setDeepth(int deepth) {
        this.deepth = deepth;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PaperType getType() {
        return type;
    }

    public void setType(PaperType type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
