package com.example.myapplication

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.beardedhen.androidbootstrap.TypefaceProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

/* singleton */
class QuizzApp : Application() {

    override fun onCreate() {
        super.onCreate()
        /* dzięki temu mogę korzystać z deafultowych ikon (wektorowych) - przynajmniej tak gość na kursie mówił
         * a z tego, co widziałem, to apka się wyjebie jak ja beemką na autostradzie */
        TypefaceProvider.registerDefaultIconSets()

        res = resources;
        ctx = applicationContext
        fData = FirebaseDatabase.getInstance()
        fAuth  = FirebaseAuth.getInstance()
        /*przechowuję ostatnio zalogowanego użytkownika w celu pominięcia etapu logowania przy kolejnym uruchomieniu apki
         *- to nie jest apka bankowa, więc nie trzeba aż tak ostrożnie obchodzić się z userem ;) */
        fUser = fAuth.currentUser
    }

    companion object {
        /*tu trzymam kontekst aplikacji, bo jest cykl życia jest spójny z cyklem życia apki
         * lateinit, boinicjalizacja jest dopiero w onCreate();
         */
        lateinit var ctx: Context
        lateinit var res: Resources

        lateinit var fData: FirebaseDatabase
        lateinit var fAuth: FirebaseAuth
        var fUser: FirebaseUser? = null
    }
}