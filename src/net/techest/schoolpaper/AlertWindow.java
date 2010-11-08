/**
 * Copyright (c) 2010 princehaku
 * All right reserved.
 * Author princehaku
 * Site http://3haku.net
 * Created on : 2010-8-8, 10:12:28
 */
package net.techest.schoolpaper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**消息提示类
 *
 * @author princehaku
 */
public class AlertWindow {

    /**资源引用
     *
     */
    private static Context res;
    
    private Dialog alert;

    /**从上级资源中构建
     * res一般是当前活动的Activity
     * @param res
     */
    AlertWindow(Context res) {
        AlertWindow.res = res;
    }

    /**显示对话框
     *
     * @param title
     * @param message
     */
    public void show(String title, String message) {
        Message msg = new Message();
        Bundle ble = new Bundle();
        ble.putString("title", title);
        ble.putString("msg", message);
        msg.setData(ble);
        this.alertHandler.sendMessage(msg);
    }

    /**隐藏对话框
     *
     */
    public void destory() {
        if (alert != null) {
            alert.dismiss();
        }
    }

    /**隐含的方法
     * 显示对话框
     * @param info
     */
    private void alert(String title, String info) {
        if (alert != null) {
            alert.dismiss();
        }
        alert = new AlertDialog.Builder(res).setIcon(android.R.drawable.ic_dialog_alert).setTitle(title).setMessage(info).show();
    }
    /**Hander
     * 保证线程安全
     */
    public Handler alertHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            alert(msg.getData().getString("title"), msg.getData().getString("msg"));
            super.handleMessage(msg);
        }
    };
}
