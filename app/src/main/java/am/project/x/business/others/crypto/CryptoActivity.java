/*
 * Copyright (C) 2018 AlexMofer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package am.project.x.business.others.crypto;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import am.project.support.utils.InputMethodUtils;
import am.project.x.R;
import am.project.x.base.BaseActivity;

/**
 * 加密解密
 */
public class CryptoActivity extends BaseActivity implements View.OnClickListener {

    private EditText mVInput;
    private TextView mVOutput;
    private AlertDialog mDLoading;

    public static void start(Context context) {
        context.startActivity(new Intent(context, CryptoActivity.class));
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_crypto;
    }

    @Override
    protected void initializeActivity(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(R.id.crypto_toolbar);
        mVInput = findViewById(R.id.crypto_edt_input);
        mVOutput = findViewById(R.id.crypto_tv_output);

        findViewById(R.id.crypto_btn_message).setOnClickListener(this);
        findViewById(R.id.crypto_btn_des).setOnClickListener(this);
        findViewById(R.id.crypto_btn_aes).setOnClickListener(this);
        findViewById(R.id.crypto_btn_rsa).setOnClickListener(this);
    }

    // Listener
    @Override
    public void onClick(View v) {
        final String input = mVInput.getText().toString();
        if (TextUtils.isEmpty(input)) {
            if (!InputMethodUtils.isInputMethodOpen(this))
                InputMethodUtils.openInputMethod(mVInput);
            return;
        }
        InputMethodUtils.closeInputMethod(mVInput);
        if (mDLoading == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.crypto_dlg_message);
            builder.setCancelable(false);
            mDLoading = builder.create();
            mDLoading.setCanceledOnTouchOutside(false);
        }
        showDialog(mDLoading);
        switch (v.getId()) {
            case R.id.crypto_btn_message:
                break;
            case R.id.crypto_btn_des:
                break;
            case R.id.crypto_btn_aes:
                break;
            case R.id.crypto_btn_rsa:
                break;
        }
    }
}