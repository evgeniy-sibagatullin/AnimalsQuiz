package com.quiz.android.animalsquiz.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.quiz.android.animalsquiz.R;

public enum Animal {
    WOLF(R.string.wolf, R.drawable.wolf),
    FOX(R.string.fox, R.drawable.fox),
    JAGUAR(R.string.jaguar, R.drawable.jaguar),
    GOAT(R.string.goat, R.drawable.goat),
    COW(R.string.cow, R.drawable.cow),
    HORSE(R.string.horse, R.drawable.horse),
    HEDGEHOG(R.string.hedgehog, R.drawable.hedgehog),
    PORCUPINE(R.string.porcupine, R.drawable.porcupine),
    SEASHELL(R.string.seashell, R.drawable.seashell),
    WHALE(R.string.whale, R.drawable.whale),
    BEE(R.string.bee, R.drawable.bee),
    WORM(R.string.worm, R.drawable.worm);

    @StringRes
    public final int textResId;
    @DrawableRes
    public final int iconResId;

    Animal(@StringRes int textResId, @DrawableRes int iconResId) {
        this.textResId = textResId;
        this.iconResId = iconResId;
    }
}
