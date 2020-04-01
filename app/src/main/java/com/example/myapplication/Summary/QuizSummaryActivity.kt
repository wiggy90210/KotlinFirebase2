package com.example.myapplication.Summary

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.MainActivity.Companion.USER_NAME
import com.example.myapplication.MainActivity.Companion.USER_URL
import com.example.myapplication.News.NewsItem
import com.example.myapplication.QuestionSet.QuizActivity.Companion.POINTS
import com.example.myapplication.QuestionSet.QuizActivity.Companion.QUIZ_NAME
import com.example.myapplication.QuestionSet.QuizActivity.Companion.SUCCESS_SUMMARY
import com.example.myapplication.QuizzApp
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_newsitem.*
import kotlinx.android.synthetic.main.fragment_quizitem.quizTitle
import kotlinx.android.synthetic.main.result_activity.*

/**
 * Aktywność podsumowania quizu, wraz z ewentualnym logowaniem
 */

class QuizSummaryActivity : AppCompatActivity() {
    //region intent extras
    private val quiz_name by lazy { intent.extras!!.get(QUIZ_NAME) as String }
    private val success_summary by lazy { intent.extras!!.get(SUCCESS_SUMMARY) as String }
    private val iPoints by lazy { intent.extras!!.get(POINTS) as Int }
    private val user_name by lazy { intent.extras?.get(USER_NAME) as? String }
    private val user_url by lazy { intent.extras?.get(USER_URL) as? String }
    //endregion

    //region window init
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result_activity)
        setLayout()
    }

    private fun setLayout() {
        title_caption.text = success_summary
        quizTitle.text = quiz_name
        pointsText.text = iPoints.toString()
        respects.text = 1.toString()
        time.text = "00m"

        setUsername()
        setUserImage()
        setAddComment()

        likesImage.isEnabled = false

        setUpOkButton()
        setUpCloseButton()
    }



    private fun setUserImage() {
        if (!user_url.isNullOrEmpty()) {
            Glide.with(this)
                .load(user_url)
                .into(circleImageProfile)
        }
    }

    private fun setUsername() {
        if (!user_name.isNullOrEmpty()) {
            name.text = user_name
        }
    }
    //endregion

    //region comment
    private fun setAddComment() {
        add_comment.visibility = View.VISIBLE
        comment.visibility = View.GONE
        add_comment.setOnClickListener { v -> showEditComment() }
    }

    private fun showEditComment() {
        add_comment.visibility = View.GONE
        edit_comment.visibility = View.VISIBLE
    }
    //endregion

    //region public
    private fun setUpCloseButton() {
        close_btn.setOnClickListener { v -> setResult(Activity.RESULT_CANCELED) }
    }

    private fun setUpOkButton() {
        // TODO przypadkowe logowanie
        if (FirebaseAuth.getInstance().currentUser != null) {
            ok.setOnClickListener { v -> goToPublish() }
        } else {
            ok.text = QuizzApp.res.getString(R.string.not_logged_news)
            ok.setOnClickListener { v -> logIn() }
        }
    }

    private fun logIn() {
        goToPublish()
    }

    private fun goToPublish() {
        val intent = Intent().apply {
            putExtra(NEW_FEED, NewsItem().apply {
                comment = edit_comment.text.toString()
                points = iPoints
                quiz = quiz_name
                timeMillis = System.currentTimeMillis()
            })
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
    //endregion

    companion object {
        const val NEW_FEED = "newFeed"
    }
}