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
package io.github.alexmofer.projectx;

import android.content.Context;

import io.github.alexmofer.android.support.app.ApplicationHolder;
import io.github.alexmofer.projectx.features.AppSignatureUtil;

/**
 * 应用 Application
 * Created by Alex on 2018/7/23.
 */
public class Application extends android.app.Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        ApplicationHolder.create(this);
        System.out.println("lalala----------------------------------md5:" + AppSignatureUtil.getAppSignature(this, "com.fawnflow.app", "MD5"));
        System.out.println("lalala---------------------------------sha1:" + AppSignatureUtil.getAppSignature(this, "com.fawnflow.app", "SHA-1"));
        System.out.println("lalala-------------------------------sha256:" + AppSignatureUtil.getAppSignature(this, "com.fawnflow.app", "SHA-256"));
    }
}
