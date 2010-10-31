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

package net.techest.schoolpaper;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;
import java.util.ArrayList;

/**
 *
 * @author princehaku
 */
public class PaperOverlay extends ItemizedOverlay{
    Context mContext;
    public PaperOverlay(Drawable defaultMarker) {
      super(boundCenterBottom(defaultMarker));
    }

    public PaperOverlay(Drawable defaultMarker, Context context) {
      super(boundCenterBottom(defaultMarker));
      mContext = context;
    }
    @Override
    protected boolean onTap(int index) {
      OverlayItem item = mOverlays.get(index);
      AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
      dialog.setTitle(item.getTitle());
      dialog.setMessage(item.getSnippet());
      dialog.show();
      return true;
    }
    
    private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();

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
