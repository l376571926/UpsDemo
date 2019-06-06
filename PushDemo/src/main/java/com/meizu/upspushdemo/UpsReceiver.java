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


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.meizu.upspushsdklib.CommandType;
import com.meizu.upspushsdklib.Company;
import com.meizu.upspushsdklib.UpsCommandMessage;
import com.meizu.upspushsdklib.UpsPushMessage;
import com.meizu.upspushsdklib.UpsPushMessageReceiver;
import com.socks.library.KLog;

import static com.meizu.upspushdemo.UpsDemoApplication.sendMessage;


public class UpsReceiver extends UpsPushMessageReceiver {
    @Override
    public void onThroughMessage(Context context, UpsPushMessage upsPushMessage) {
        sendMessage("onThroughMessage: " + upsPushMessage.getContent());
    }

    @Override
    public void onNotificationClicked(Context context, UpsPushMessage upsPushMessage) {
        sendMessage("onNotificationClicked: " + upsPushMessage);
    }

    @Override
    public void onNotificationArrived(Context context, UpsPushMessage upsPushMessage) {
        sendMessage("onNotificationArrived: " + upsPushMessage);
    }

    @Override
    public void onNotificationDeleted(Context context, UpsPushMessage upsPushMessage) {
        sendMessage("onNotificationDeleted: " + upsPushMessage);
    }

    @Override
    public void onUpsCommandResult(Context context, UpsCommandMessage upsCommandMessage) {
        KLog.e("UpsReceiver " + upsCommandMessage);
        CommandType commandType = upsCommandMessage.getCommandType();
        int code = upsCommandMessage.getCode();
        String commandResult = upsCommandMessage.getCommandResult();
        Company company = upsCommandMessage.getCompany();
        String message = upsCommandMessage.getMessage();
        Object extra = upsCommandMessage.getExtra();

        if (company == Company.HUAWEI) {
            if (commandType == CommandType.REGISTER) {
                Bundle bundle = (Bundle) extra;
                String belongId = bundle.getString("belongId");
                KLog.e("hw belongId " + belongId);
            } else if (commandType == CommandType.UNREGISTER) {
                KLog.e("hw unregister " + upsCommandMessage);
            }
        }
        if (commandType == CommandType.REGISTER) {
            KLog.e("推送注册成功token：" + commandResult);
        }

        sendMessage("onUpsCommandResult 如下:"
                + "\n CommandType-> " + commandType.name()
                + "\n Company-> " + company
                + "\n Code-> " + code
                + "\n CommandResult-> " + commandResult
                + (TextUtils.isEmpty(message) ? "" : "\n Message-> " + message));

        if (code == 0) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(context, UpsPermissionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }


}
