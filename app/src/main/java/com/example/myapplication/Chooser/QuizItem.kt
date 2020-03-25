package com.example.myapplication.Chooser

import java.io.Serializable


/**
 * model danych dla zestawy pytań
 */

data class QuizItem (
        var level: LevelEnum = LevelEnum.EASY,
        var lang: LangEnum = LangEnum.ANDROID,
    // zbiór pytań
        var questionSet: String = "") : Serializable