package com.example.myapplication.Chooser

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.myapplication.QuizzApp
import com.example.myapplication.R

/**
 * typ wliczeniowy dla języków programowania(kiedyś może innych dziedzin),
 * w przypadku dalszego rozwoju może podzielę na różne enumy, lub klasy
 */
enum class LangEnum(
    // nazwa języka
        @StringRes val label: Int,
    // ikona języka
        @DrawableRes val image: Int) {

    ANDROID(R.string.lang_android, R.drawable.ic_language_android),
    KOTLIN(R.string.lang_kotlin, R.drawable.ic_language_kotlin),
    JAVA(R.string.lang_java, R.drawable.ic_language_java);

    fun getString() =
        QuizzApp.res.getString(this.label)
}