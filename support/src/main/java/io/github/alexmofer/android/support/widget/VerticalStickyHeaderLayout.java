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

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingChild3;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import io.github.alexmofer.android.support.widget.builders.NestedScrollViewBuilder;
import io.github.alexmofer.android.support.widget.builders.ViewBuilder;

/**
 * 垂直吸顶布局
 * Created by Alex on 2026/7/21.
 */
@SuppressLint("ViewConstructor")
@SuppressWarnings("NullableProblems")
public final class VerticalStickyHeaderLayout<
        Header extends View & VerticalStickyHeaderLayout.StickyHeader,
        Content extends View & NestedScrollingChild3> extends ViewGroup
        implements NestedScrollingParent3 {
    private final NestedScrollingParentHelper mHelper;
    private final View mHeaderContainer;
    private final Header mHeader;
    private final Content mContent;
    private int mFoldableSpace;
    private int mFoldedSpace;
    private boolean mAutoDisableScroll = false;

    private VerticalStickyHeaderLayout(@NonNull Context context,
                                       @NonNull Header header,
                                       @NonNull Content content) {
        super(context);
        mHelper = new NestedScrollingParentHelper(this);
        if (header instanceof NestedScrollingChild) {
            mHeaderContainer = header;
        } else {
            mHeaderContainer = new NestedScrollViewBuilder(context)
                    .addView(new ViewBuilder(header)
                            .matchWidth()
                            .build())
                    .setVerticalScrollBarEnabled(false)
                    .build();
        }
        mHeader = header;
        mContent = content;
        addView(mHeaderContainer);
        addView(content);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        mHeaderContainer.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        mFoldableSpace = mHeader.getFoldableSpace();
        final int headerMinHeight = mHeaderContainer.getMeasuredHeight() - mFoldableSpace;
        final int contentHeight = height - headerMinHeight;
        mContent.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(contentHeight, MeasureSpec.EXACTLY));
        if (mAutoDisableScroll) {
            if (mContent instanceof AutoDisableScrollHelper
                    && ((AutoDisableScrollHelper) mContent).getVerticalScrollRange() < mFoldableSpace) {
                mFoldableSpace = 0;
                mContent.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(height - mHeaderContainer.getMeasuredHeight(),
                                MeasureSpec.EXACTLY));
            }
        }
        mFoldedSpace = Math.min(mFoldedSpace, mFoldableSpace);
        mHeader.setFoldedSpace(mFoldedSpace);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int top = -mFoldedSpace;
        final int headerHeight = mHeaderContainer.getMeasuredHeight();
        mHeaderContainer.layout(0, top, mHeaderContainer.getMeasuredWidth(), top + headerHeight);
        mContent.layout(0, top + headerHeight,
                mContent.getMeasuredWidth(),
                top + headerHeight + mContent.getMeasuredHeight());
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child,
                                       @NonNull View target,
                                       int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child,
                                       @NonNull View target, int axes, int type) {
        mHelper.onNestedScrollAccepted(child, target, axes, type);
    }

    @Override
    public void onNestedPreScroll(@NonNull View target,
                                  int dx, int dy, @NonNull int[] consumed, int type) {
        if (dy > 0 && mFoldableSpace > mFoldedSpace) {
            final int remainingSpace = mFoldableSpace - mFoldedSpace;
            final int dyConsumed = Math.min(dy, remainingSpace);
            mHeaderContainer.offsetTopAndBottom(-dyConsumed);
            mContent.offsetTopAndBottom(-dyConsumed);
            mFoldedSpace = mFoldedSpace + dyConsumed;
            mHeader.setFoldedSpace(mFoldedSpace);
            consumed[1] = dyConsumed;
        }
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed, int type) {
        onNestedScrollInternal(dyUnconsumed, null);
    }


    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed, int type,
                               @NonNull int[] consumed) {
        onNestedScrollInternal(dyUnconsumed, consumed);
    }

    private void onNestedScrollInternal(int dyUnconsumed, @Nullable int[] consumed) {
        if (dyUnconsumed < 0 && mFoldedSpace > 0) {
            final int remainingSpace = mFoldedSpace;
            final int dyConsumed = Math.max(dyUnconsumed, -remainingSpace);

            mHeaderContainer.offsetTopAndBottom(-dyConsumed);
            mContent.offsetTopAndBottom(-dyConsumed);
            mFoldedSpace = mFoldedSpace + dyConsumed;
            mHeader.setFoldedSpace(mFoldedSpace);

            if (consumed != null) {
                consumed[1] += dyConsumed;
            }
        }
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        mHelper.onStopNestedScroll(target, type);
    }

    @Override
    public int getNestedScrollAxes() {
        return mHelper.getNestedScrollAxes();
    }

    public void folded() {
        if (mFoldedSpace < mFoldableSpace) {
            final int offset = mFoldedSpace - mFoldableSpace;
            mFoldedSpace = mFoldableSpace;
            mHeaderContainer.offsetTopAndBottom(offset);
            mHeader.setFoldedSpace(mFoldedSpace);
            mContent.offsetTopAndBottom(offset);
        }
    }

    public void unfold() {
        if (mFoldedSpace > 0) {
            final int offset = mFoldedSpace;
            mFoldedSpace = 0;
            mHeaderContainer.offsetTopAndBottom(offset);
            mHeader.setFoldedSpace(mFoldedSpace);
            mContent.offsetTopAndBottom(offset);
        }
    }

    public void setAutoDisableScroll(boolean autoDisableScroll) {
        if (mAutoDisableScroll == autoDisableScroll) {
            return;
        }
        mAutoDisableScroll = autoDisableScroll;
        requestLayout();
    }

    public interface StickyHeader {
        /**
         * 获取可折叠空间
         *
         * @return 可折叠空间
         */
        int getFoldableSpace();

        /**
         * 设置已折叠空间
         *
         * @param foldedSpace 已折叠空间
         */
        void setFoldedSpace(int foldedSpace);
    }

    public interface AutoDisableScrollHelper {

        int getVerticalScrollRange();
    }

    public static class Builder extends ViewBuilder {
        private final VerticalStickyHeaderLayout<?, ?> mView;

        public Builder(@NonNull VerticalStickyHeaderLayout<?, ?> view) {
            super(view);
            mView = view;
        }

        public <Header extends View & StickyHeader, Content extends View & NestedScrollingChild3> Builder(@NonNull Context context,
                                                                                                                                     @NonNull Header header,
                                                                                                                                     @NonNull Content content) {
            this(new VerticalStickyHeaderLayout<>(context, header, content));
        }

        @NonNull
        @Override
        public VerticalStickyHeaderLayout<?, ?> build() {
            return mView;
        }

        @NonNull
        public Builder setFolded(@NonNull LifecycleOwner owner,
                                 @NonNull MutableLiveData<Boolean> folded) {
            folded.observe(owner, value -> {
                if (value == null) {
                    return;
                }
                folded.setValue(null);
                if (value) {
                    mView.folded();
                } else {
                    mView.unfold();
                }
            });
            return this;
        }

        @NonNull
        public Builder setAutoDisableScroll(boolean autoDisableScroll) {
            mView.setAutoDisableScroll(autoDisableScroll);
            return this;
        }
    }
}
