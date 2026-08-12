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

import android.view.View;

import androidx.annotation.NonNull;

/**
 * 重新绘制辅助
 * Created by Alex on 2026/7/29.
 */
public final class RedrawHelper {
    private final Runnable mRunnable = this::run;
    private final View mView;
    private final Callback mCallback;
    private boolean mAutoInvalidate = false;

    public RedrawHelper(@NonNull View view,
                        @NonNull Callback callback) {
        mView = view;
        mCallback = callback;
    }

    public void redraw() {
        if (mAutoInvalidate) {
            return;
        }
        mAutoInvalidate = true;
        mView.invalidate();
        mView.postOnAnimation(mRunnable);
    }

    private void run() {
        if (mCallback.isNeedRedraw()) {
            mAutoInvalidate = true;
            mView.invalidate();
            mView.postOnAnimation(mRunnable);
        } else {
            mAutoInvalidate = false;
        }
    }

    public interface Callback {

        boolean isNeedRedraw();
    }
}
