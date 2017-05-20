package com.quiz.android.animalsquiz.model;

import android.support.annotation.StringRes;

import com.quiz.android.animalsquiz.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.quiz.android.animalsquiz.model.Animal.BEE;
import static com.quiz.android.animalsquiz.model.Animal.COW;
import static com.quiz.android.animalsquiz.model.Animal.FOX;
import static com.quiz.android.animalsquiz.model.Animal.GOAT;
import static com.quiz.android.animalsquiz.model.Animal.HEDGEHOG;
import static com.quiz.android.animalsquiz.model.Animal.HORSE;
import static com.quiz.android.animalsquiz.model.Animal.JAGUAR;
import static com.quiz.android.animalsquiz.model.Animal.PORCUPINE;
import static com.quiz.android.animalsquiz.model.Animal.SEASHELL;
import static com.quiz.android.animalsquiz.model.Animal.WHALE;
import static com.quiz.android.animalsquiz.model.Animal.WOLF;
import static com.quiz.android.animalsquiz.model.Animal.WORM;

public enum Question {
    PREDATORS(R.string.highly_developed_predators, pack(WOLF, FOX, JAGUAR)),
    HERBIVORES(R.string.highly_developed_herbivores, pack(GOAT, COW, HORSE)),
    HORNED(R.string.horned, pack(GOAT, COW)),
    PRICKLY(R.string.prickly, pack(HEDGEHOG, PORCUPINE)),
    SEA(R.string.sea, pack(SEASHELL, WHALE)),
    INSECTS(R.string.insects, pack(BEE, WORM)),
    FELINE(R.string.feline, pack(JAGUAR)),
    FLYING(R.string.flying, pack(BEE)),
    JEWELRY(R.string.jewelry, pack(SEASHELL)),
    RIDING(R.string.riding, pack(HORSE)),
    THE_BIGGEST(R.string.the_biggest, pack(WHALE)),
    THE_SMALLES(R.string.the_smallest, pack(BEE));

    @StringRes
    public final int textResId;
    public final List<Animal> answers;

    Question(@StringRes int textResId, List<Animal> answers) {
        this.textResId = textResId;
        this.answers = answers;
    }

    private static List<Animal> pack(Animal... inputAnimals) {
        List<Animal> animals = new ArrayList<>();
        Collections.addAll(animals, inputAnimals);
        return animals;
    }
}
