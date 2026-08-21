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

import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.dynamicanimation.animation.FloatValueHolder;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

/**
 * 透明度呼吸灯效果
 * Created by Alex on 2026/7/24.
 */
public final class AlphaBreathingEffect {
    private static final long DURATION = 2400;
    private final long mDuration;
    private final float mAlpha;
    private final FloatValueHolder mAlphaHolder;
    private final SpringAnimation mStopAnimation;
    private boolean mStarted;
    private float mStartOffset;
    private long mStartedTime;

    public AlphaBreathingEffect(long duration, float alpha) {
        mDuration = duration;
        mAlpha = alpha;
        mAlphaHolder = new FloatValueHolder(alpha);
        mStopAnimation = newAnimation(mAlphaHolder);
    }

    public AlphaBreathingEffect(float alpha) {
        this(DURATION, alpha);
    }

    public AlphaBreathingEffect() {
        this(1f);
    }

    @NonNull
    private static SpringAnimation newAnimation(@NonNull FloatValueHolder holder) {
        final SpringAnimation animation = new SpringAnimation(holder);
        animation.setSpring(new SpringForce()
                .setStiffness(SpringForce.STIFFNESS_LOW)
                .setDampingRatio(SpringForce.DAMPING_RATIO_NO_BOUNCY));
        animation.setMinValue(0f);
        animation.setMaxValue(1f);
        animation.setMinimumVisibleChange(0.001f);
        animation.setStartValue(holder.getValue());
        animation.animateToFinalPosition(holder.getValue());
        return animation;
    }

    /**
     * 开始
     */
    public void start() {
        if (mStarted) {
            return;
        }
        mStarted = true;
        final float alpha = mAlphaHolder.getValue();
        if (mStopAnimation.isRunning()) {
            mStopAnimation.skipToEnd();
        }
        mStartOffset = (float) Math.asin(alpha);
        mStartedTime = AnimationUtils.currentAnimationTimeMillis();
    }

    /**
     * 是否已开始
     *
     * @return true 已开始
     */
    public boolean isStarted() {
        return mStarted;
    }

    /**
     * 停止
     */
    public void stop() {
        if (mStarted) {
            final float alpha = getAlpha();
            mAlphaHolder.setValue(alpha);
            mStarted = false;
            mStopAnimation.animateToFinalPosition(mAlpha);
        }
    }

    /**
     * 是否正在运行
     *
     * @return true 正在运行
     */
    public boolean isRunning() {
        return mStarted || mStopAnimation.isRunning();
    }

    /**
     * 获取透明度
     *
     * @return 透明度
     */
    public float getAlpha() {
        if (mStarted) {
            final long time = AnimationUtils.currentAnimationTimeMillis();
            final long offset = time - mStartedTime;
            final long duration = offset % mDuration;
            final double radians = (2.0 * Math.PI * duration / mDuration) - (Math.PI / 2.0);
            final float progress = (float) (1.0 + Math.sin(mStartOffset + radians)) * 0.5f;
            return Math.min(1, Math.max(0, progress));
        }
        return mAlphaHolder.getValue();
    }
}
