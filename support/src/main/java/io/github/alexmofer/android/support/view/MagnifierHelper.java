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
package io.github.alexmofer.android.support.view;

import android.os.Build;
import android.view.View;
import android.widget.Magnifier;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 放大镜辅助
 * Created by Alex on 2026/8/4.
 */
public final class MagnifierHelper {
    @Nullable
    private final Object mMagnifier;

    public MagnifierHelper(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                mMagnifier = new Magnifier.Builder(view).build();
            } else {
                //noinspection deprecation
                mMagnifier = new Magnifier(view);
            }
        } else {
            mMagnifier = null;
        }
    }

    /**
     * Shows the magnifier on the screen at a position that is independent from its content
     * position. The first two arguments represent the coordinates of the center of the
     * content source going to be magnified and copied to the magnifier. The last two arguments
     * represent the coordinates of the center of the magnifier itself. All four coordinates
     * are relative to the top left corner of the magnified view. If you consider using this
     * method such that the offset between the source center and the magnifier center coordinates
     * remains constant, you should consider using method {@link Magnifier#show(float, float)} instead.
     *
     * @param sourceCenterX    horizontal coordinate of the source center relative to the view
     * @param sourceCenterY    vertical coordinate of the source center, relative to the view
     * @param magnifierCenterX horizontal coordinate of the magnifier center, relative to the view
     * @param magnifierCenterY vertical coordinate of the magnifier center, relative to the view
     */
    public void show(@FloatRange(from = 0) float sourceCenterX,
                      @FloatRange(from = 0) float sourceCenterY,
                      float magnifierCenterX, float magnifierCenterY) {
        if (mMagnifier == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ((Magnifier) mMagnifier).show(sourceCenterX, sourceCenterY, magnifierCenterX, magnifierCenterY);
            return;
        }
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.P) {
            ((Magnifier) mMagnifier).show(sourceCenterX, sourceCenterY);
        }
    }

    /**
     * Dismisses the magnifier from the screen. Calling this on a dismissed magnifier is a no-op.
     */
    public void dismiss() {
        if (mMagnifier == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ((Magnifier) mMagnifier).dismiss();
        }
    }
}
