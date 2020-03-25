package com.example.myapplication.Profile

import java.io.Serializable

/**
 * Model danych użytkownika
 */
data class UserItem(
    // nazwa użytkownika
        var name: String = "",
    // url obrazka/zdjęcia użytkownika
        var imgUrl: String = "",
    // user ID
        var uid: String = "") : Serializable {
}