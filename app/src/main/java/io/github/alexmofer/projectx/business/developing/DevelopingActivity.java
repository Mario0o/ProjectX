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
package io.github.alexmofer.projectx.business.developing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.github.alexmofer.android.support.app.ApplicationData;
import io.github.alexmofer.android.support.app.ApplicationHolder;
import io.github.alexmofer.android.support.other.StringResourceException;
import io.github.alexmofer.projectx.databinding.ActivityDevelopingBinding;

/**
 * 正在开发
 */
public class DevelopingActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, DevelopingActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ActivityDevelopingBinding.inflate(getLayoutInflater()).getRoot());
        TestApplicationData.getInstance().toast(this);
        InnerApplicationData.getInstance().toast(this);

        ListenableFutureUtils.submit(() -> {
            System.out.println("lalalalalal-------------------------------------running:" + Thread.currentThread().getName());
            Thread.sleep(3000);
            return "你好你好";
        }, result -> {
            System.out.println("lalalalalal-------------------------------------success:" + Thread.currentThread().getName());
            System.out.println("lalalalalal-------------------------------------success:" + result);
        }, t -> {
            System.out.println("lalalalalal-------------------------------------failure:" + Thread.currentThread().getName());
            StringResourceException.getMessage(t).showToast(this);
        });
    }

    private static class InnerApplicationData extends ApplicationData {

        private final String mTest = "内部类";

        private InnerApplicationData() {
            //no instance
        }

        public static InnerApplicationData getInstance() {
            return ApplicationHolder.getApplicationData(
                    InnerApplicationData.class, InnerApplicationData::new);
        }

        public void toast(Context context) {
            Toast.makeText(context, mTest, Toast.LENGTH_SHORT).show();
        }
    }
}
