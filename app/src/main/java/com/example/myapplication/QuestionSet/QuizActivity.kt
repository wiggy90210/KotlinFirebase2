package com.example.myapplication.QuestionSet

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.QuizChooser.QuizItem
import com.example.myapplication.R
import com.kofigyan.stateprogressbar.StateProgressBar
import kotlinx.android.synthetic.main.quiz_activity.*
import java.util.*
import kotlin.collections.ArrayList


/**
 * Aktywność quizu, iteracja po pytaniach w obębie jednej aktywności
 */

class QuizActivity : AppCompatActivity() {


    private val questionList by lazy {
        intent.extras.get(QUIZ_SET) as ArrayList<QuestionItem>
    }
    private val quizTitle by lazy {
        intent.extras.get(TITLE) as String
    }
    private val quiz by lazy {      // "by lazy" - w momencie odwołania sę do wartości "quiz" wykona się blok kodu, który ją zainicjalizuje
        intent.extras.get(QUIZ) as QuizItem
    }
    private val quizIterator by  lazy { questionList.iterator() }

    private lateinit var currentQuestionItem: QuestionItem
    private var currentPositive = 0
    private var currentNumber: Int = 0
    get() = if (field < 5) field else 4     // zwracam field (current numbe) lub 4, bo mam max 5 elementów

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.quiz_activity)
        setLayout()
    }

    private fun setLayout() {
        quizLogo.setImageResource(quiz.lang.image)
        levelImageView.setImageResource(quiz.level.image)
        nextQuestion()
    }

    private fun nextQuestion() {
        if (quizIterator.hasNext()) {
            currentQuestionItem = quizIterator.next()
            currentPositive = Random().nextInt(3) + 1
            progress.setCurrentStateNumber(StateProgressBar.StateNumber.values()[currentNumber])
            setUpButtons()
        } else {
            returnResultFromQuiz()
        }
    }
}