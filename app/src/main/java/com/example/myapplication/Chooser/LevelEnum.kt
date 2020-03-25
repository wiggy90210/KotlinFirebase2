package com.example.myapplication.Chooser

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.myapplication.QuizzApp
import com.example.myapplication.R

/**
 * typ wliczeniowy dla poziomów trudności, raczej taki już zostanie
 */
enum class LevelEnum(
    // nazwa...
        @StringRes val label: Int,
    // ikona...
        @DrawableRes val image: Int) {

    EASY(R.string.level_easy, R.drawable.ic_level_easy),
    AVERAGE(R.string.level_average, R.drawable.ic_level_average),
    HARD(R.string.level_hard, R.drawable.ic_level_hard);

    fun getString() =
        QuizzApp.res.getString(this.label)
}