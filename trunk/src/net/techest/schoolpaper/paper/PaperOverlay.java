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
 *  Created on : 2010-10-31, 21:05:07
 *  Author     : princehaku
 */

package net.techest.schoolpaper.paper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;
import java.util.ArrayList;
import net.techest.schoolpaper.PaperActivity;
import net.techest.schoolpaper.PublicData;

/**地图上的标注
 *
 * @author princehaku
 */
public class PaperOverlay extends ItemizedOverlay{

    /**资源
     *
     */
    private static Activity res;
    Context mContext;
    /**内部数组 保存挂件
     *
     */
    private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
    /**纸片的id号码
     *
     */
    private final int paperId;
    /**
     * 
     * @param defaultMarker
     * @param context
     */
    public PaperOverlay(Drawable defaultMarker, Context context,Activity res,int paperId) {
      super(boundCenterBottom(defaultMarker));
      mContext = context;
      this.res=res;
      this.paperId=paperId;
    }
    /**点击时显示到新的PaperActivity
     *
     * @param index
     * @return
     */
    @Override
    protected boolean onTap(int index) {
      OverlayItem item = mOverlays.get(index);
      PublicData.nowPaperId=paperId;
      AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
      dialog.setTitle(item.getTitle());
      dialog.setMessage("请稍后 读取数据中...");
      dialog.show();
      Intent intent = new Intent();
      intent.setClass(mContext, PaperActivity.class);
      mContext.startActivity(intent);
      this.res.finish();
      return true;
    }

    public void addOverlay(OverlayItem overlay) {
        mOverlays.add(overlay);
        populate();
    }
    @Override
    protected OverlayItem createItem(int i) {
      return mOverlays.get(i);
    }

   @Override
    public int size() {
      return mOverlays.size();
    }

}
