package com.example.myapplication.News

import java.io.Serializable

/**
 * Model danych dla elementu "newsa"
 */
data class NewsItem(

    // komentarz użytkownika pod quizem
        var comment: String = "",
    // punkty uzyskane za quiz
        var points: Int = 0,
    // nazwa quizu / LangEnum + LevelEnum
        var quiz: String = "",
    // zdjęcie / ikona użytkownika
        var image: String = "",
    // nazwa użytkownika
        var user: String = "",
    // czas ukończenia quizu
        var timeMillis: Long = 0,
    // user ID
        var uid: String = "",
    // mapa "like'ów" zawierająca nazwy użytkowników, który polubili dany quiz
        var respects: HashMap<String, Int> = hashMapOf()) : Serializable {
}