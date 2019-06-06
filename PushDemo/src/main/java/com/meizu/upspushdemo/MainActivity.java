/*
 * MIT License
 *
 * Copyright (c) [2017] [Meizu.inc]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.meizu.upspushdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.meizu.upspushsdklib.Company;
import com.meizu.upspushsdklib.UpsPushManager;
import com.meizu.upspushsdklib.util.UpsLogger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static List<String> logList = new CopyOnWriteArrayList<String>();
    private TextView mLogView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UpsDemoApplication.setMainActivity(this);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewDataBinding.setVariable(BR.handler, this);

        mLogView = (TextView) findViewById(R.id.log);
        Intent intent = getIntent();
        UpsLogger.e(this, "inent " + intent.getStringExtra("oppo"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_enable_direct_mode:
                Log.e(TAG, "onClick: 启用直连模式");
                UpsPushManager.enableDirectMode(this, true);
                break;
            case R.id.btn_disable_direct_mode://选择长连接通知栏类型，用于测试用
                Log.e(TAG, "onClick: 禁用直连模式");
                UpsPushManager.enableDirectMode(this, false);
                break;
            case R.id.btn_choose_company_channel:
                Log.e(TAG, "onClick: 选择通道");
                showChooseChannelDiglog();
                break;
            case R.id.btn_register:
                Log.e(TAG, "onClick: 订阅");
                UpsPushManager.register(this, BuildConfig.MEIZU_UPS_APP_ID, BuildConfig.MEIZU_UPS_APP_KEY);
                UpsLogger.e(this, "MANUFACTURER:" + Build.MANUFACTURER + " model:" + Build.MODEL + " brand:" + Build.BOARD);
                break;
            case R.id.btn_unregister:
                Log.e(TAG, "onClick: 取消订阅");
                UpsPushManager.unRegister(this);
                break;
            case R.id.btn_set_alias:
                Log.e(TAG, "onClick: 别名订阅");
                UpsPushManager.setAlias(this, "ups");
                break;
            case R.id.btn_unset_alias:
                Log.e(TAG, "onClick: 取消别名订阅");
                UpsPushManager.unSetAlias(this, "ups");
                break;
            case R.id.btn_server_push:
                Log.e(TAG, "onClick: 服务推送");
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshLogInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UpsDemoApplication.setMainActivity(null);
    }


    public void refreshLogInfo() {
        StringBuilder AllLog = new StringBuilder();
        for (String log : logList) {
            AllLog.append(log).append("\n\n");
        }
        mLogView.setText(AllLog.toString());
    }


    private void showChooseChannelDiglog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.choose_channel_tile)
                .setItems(R.array.channel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String channelName = "";
                        switch (which) {
                            case 0:
                                channelName = "自动选择";
                                UpsPushManager.setCurrentChannelType(MainActivity.this, which);
                                break;
                            case 1:
                                channelName = "魅族";
                                UpsPushManager.setCurrentChannelType(MainActivity.this, which);
                                break;
                            case 2:
                                channelName = "小米";
                                UpsPushManager.setCurrentChannelType(MainActivity.this, which);
                                break;
                            case 3:
                                channelName = "华为";
                                UpsPushManager.setCurrentChannelType(MainActivity.this, which);
                                break;
                            case 4:
                                channelName = "魅族全平台";
                                UpsPushManager.setCurrentChannelType(MainActivity.this, 40);
                                break;
                        }

                        UpsLogger.e(this, "channel which " + which + " company " + Company.fromValue(which));
                        UpsDemoApplication.sendMessage("使用 " + channelName + " 推送通道");
                    }
                });
        builder.create().show();
    }
}
