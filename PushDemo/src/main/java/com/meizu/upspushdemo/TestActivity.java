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
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.core.app.ShareCompat;

import com.socks.library.KLog;

public class TestActivity extends Activity {

    TextView tvExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tvExtra = (TextView) findViewById(R.id.tv_extra);
        Intent intent = getIntent();
        String value = intent.getStringExtra("key");
        tvExtra.setText("key->" + value);

        Uri uri = intent.getData();
        if (uri != null) {
            String scheme = uri.getScheme();
            String host = uri.getHost();
            int port = uri.getPort();
            String path = uri.getPath();
            String query = uri.getQuery();
//            String key = uri.getQueryParameter("key");

            KLog.e("scheme: " + scheme);
            KLog.e("host: " + host);
            KLog.e("port: " + port);
            KLog.e("path: " + path);
            KLog.e("queryString: " + query);
//           KLog.e( "queryParameter: " + key);

            tvExtra.setText("scheme: " + scheme + "\n" +
                    "host: " + host + "\n" +
                    "path: " + path + "\n" +
                    "queryString: " + query
            );
        }
    }

}
