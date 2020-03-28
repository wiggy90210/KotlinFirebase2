package com.example.myapplication.QuestionSet

import java.io.Serializable

data class QuestionItem(
    // treść pytania
        var question: String = "question",
    // odpowiedź poprawna
        var positive: String = "positive",
    // odpowedź niepoprawna 1
        var false1: String = "false1",
    // odpowiedź niepoprawna 2    // TODO do przerobienia później, zamiast dwóch sztywnych niepoprawnych odpowiedzi wrzucić ArrayListę z dowolną ich ilością, później losować dwie liczby jako indeksy listy do wyświetlenia
        var false2: String = "false2") : Serializable
