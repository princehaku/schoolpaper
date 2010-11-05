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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import net.techest.schoolpaper.paper.Paper;

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
    private static int nowIndex = 0;
    private boolean showBigPic = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        alert = new AlertWindow(this);
        setContentView(R.layout.paperview);
        // ToDo add your GUI initialization code here
        setViewContent(this.getIdxByid(PublicData.nowPaperId));
    }

    private void setViewContent(int idx) {
        ArrayList<Paper> papers = PublicData.papers;
        String deepth = papers.get(idx).getDeepth() + "";
        //处理色深
        Drawable drawable = getResources().getDrawable(R.drawable.bg0);

        if (deepth.equals("1")) {
            drawable = getResources().getDrawable(R.drawable.bg1);
        }
        if (deepth.equals("2")) {
            drawable = getResources().getDrawable(R.drawable.bg2);
        }
        if (deepth.equals("3")) {
            drawable = getResources().getDrawable(R.drawable.bg3);
        }
        if (deepth.equals("4")) {
            drawable = getResources().getDrawable(R.drawable.bg4);
        }
        if (deepth.equals("5")) {
            drawable = getResources().getDrawable(R.drawable.bg5);
        }
        ((AbsoluteLayout)findViewById(R.id.widget46)).setBackgroundDrawable(drawable);
        Log.i("", "" + papers.size());

        String imgurl = "http://schoolpaper.techest.net" + papers.get(idx).getImagePath();
        ((TextView) findViewById(R.id.cc2)).setScrollContainer(true);
        ((TextView) findViewById(R.id.title)).setText("" + papers.get(idx).getTitle());
        ((TextView) findViewById(R.id.date)).setText("" + papers.get(idx).getPaperDate());
        Spanned text = Html.fromHtml(papers.get(idx).getContent());
        ((TextView) findViewById(R.id.cc2)).setText(text);
        Log.v("", "http://schoolpaper.techest.net" + papers.get(idx).getImagePath());
        ImageView image = ((ImageView) findViewById(R.id.thumb));
        image.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                showBigPic = true;
                showBigPic();
            }
        });
    }

    /**全屏显示图片
     *
     */
    private void showBigPic() {
        ArrayList<Paper> papers = PublicData.papers;
        String imgurl = "http://schoolpaper.techest.net" + papers.get(nowIndex).getImagePath();
        try {
            URL url = new URL(imgurl);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            Bitmap bm = BitmapFactory.decodeStream(is);
            ImageView image = new ImageView(this);
            image.setImageBitmap(bm);
            image.setOnClickListener(new OnClickListener() {

                public void onClick(View arg0) {
                    setContentView(R.layout.paperview);
                    setViewContent(nowIndex);
                    showBigPic = false;
                }
            });
            setContentView(image);
            is.close();
        } catch (Exception e) {
            alert.show("失败", "读取图片失败..T T");
        }
    }

    /**按下后退的时候的时间
     *
     */
    @Override
    public void onPause() {
        super.onPause();
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        this.startActivity(intent);
        this.finish();
    }

    /**根据id得到序号
     *
     * @param nowPaperId
     * @return
     */
    private int getIdxByid(int nowPaperId) {
        for (int i = 0; i < PublicData.papers.size(); i++) {
            if (nowPaperId == PublicData.papers.get(i).getId()) {
                return i;
            }
        }
        return 0;
    }
}
