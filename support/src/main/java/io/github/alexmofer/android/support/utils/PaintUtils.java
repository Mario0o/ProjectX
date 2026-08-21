/*
 * Copyright (C) 2026 AlexMofer
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
package io.github.alexmofer.android.support.utils;

import android.graphics.Paint;

import androidx.annotation.NonNull;

/**
 * 画笔工具
 * Created by Alex on 2026/8/21.
 */
public final class PaintUtils {

    private PaintUtils() {
        //no instance
    }

    /**
     * 叠加透明度
     *
     * @param paint 画笔
     * @param alpha 透明度
     */
    public static void addAlpha(@NonNull Paint paint, float alpha) {
        paint.setAlpha(Math.min(255, Math.max(0, Math.round(paint.getAlpha() * alpha))));
    }
}
