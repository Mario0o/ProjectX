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
package io.github.alexmofer.android.support.widget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A simple implementation of ListUpdater.
 * Created by Alex on 2026/8/18.
 */
public final class SimpleListUpdater implements ListUpdater {

    private final Runnable mCallback;

    public SimpleListUpdater(@NonNull Runnable callback) {
        mCallback = callback;
    }

    @Override
    public void notifyDataSetChanged() {
        mCallback.run();
    }

    @Override
    public void notifyItemChanged(int position) {
        notifyDataSetChanged();
    }

    @Override
    public void notifyItemChanged(int position, @Nullable Object payload) {
        notifyDataSetChanged();
    }

    @Override
    public void notifyItemRangeChanged(int positionStart, int itemCount) {
        notifyDataSetChanged();
    }

    @Override
    public void notifyItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
        notifyDataSetChanged();
    }

    @Override
    public void notifyItemInserted(int position) {
        notifyDataSetChanged();
    }

    @Override
    public void notifyItemMoved(int fromPosition, int toPosition) {
        notifyDataSetChanged();
    }

    @Override
    public void notifyItemRangeInserted(int positionStart, int itemCount) {
        notifyDataSetChanged();
    }

    @Override
    public void notifyItemRemoved(int position) {
        notifyDataSetChanged();
    }

    @Override
    public void notifyItemRangeRemoved(int positionStart, int itemCount) {
        notifyDataSetChanged();
    }
}
