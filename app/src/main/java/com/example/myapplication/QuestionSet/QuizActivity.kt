package com.example.myapplication.QuestionSet

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand
import com.example.myapplication.MainActivity.Companion.QUIZ
import com.example.myapplication.MainActivity.Companion.QUIZ_SET
import com.example.myapplication.MainActivity.Companion.TITLE
import com.example.myapplication.QuizChooser.QuizItem
import com.example.myapplication.QuizzApp
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
        intent.extras?.get(QUIZ_SET) as ArrayList<QuestionItem>        // "!!." -> non-null, ?. -> nullable
    }
    private val quizTitle by lazy {
        intent.extras?.get(TITLE) as String
    }
    private val quiz by lazy {      // "by lazy" - w momencie odwołania sę do wartości "quiz" wykona się blok kodu, który ją zainicjalizuje
        intent.extras?.get(QUIZ) as QuizItem
    }

    val successArray: BooleanArray by lazy {BooleanArray(questionList.size)}
    private val quizIterator by  lazy { questionList.iterator() }

    private lateinit var currentQuestionItem: QuestionItem
    private var currentPositive = 0
    private var currentNumber: Int = 0
    get() = if (field < 5) field else 4     // zwracam field (current numbe) lub 4, bo mam max 5 elementów

    /**
     * Odlicza czas przeznaczony na odpowiedź
     */
    lateinit var countDown: CountDownTimer// = getCountDownTimer()
    /**
     * Czas na przygotowanie kolejnego pytania
     * Jest to czas dla użytkownika na przeczytanie poprawnej odpowiedzi
     * w przypadku kiedy wybierze niepoprawną
     */
    lateinit var prepareNext: CountDownTimer //= getPrepareNextTimer()
    private var countDownRemain: Long = TIMER_VALUE
    private var prepareNextRemain: Long = PREPARE_VALUE


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            questionText.setSexyText(currentQuestionItem.question)
            setUpButtons()
            countDown = getCountDownTimer()
            setButtonsClickable(true)
            countDown.start()
        } else {
            returnResultFromQuiz()
        }
    }

    private fun returnResultFromQuiz() {
        val intent = Intent().apply {
            putExtra(QUIZ_NAME, quizTitle)
            putExtra(SUCCESS_SUMMARY, "Poprawne ${successArray.count({it})}/5")     //count() liczy wystąpienia "true", it - odwołani do obiektu na którym wywołuję metodę
            putExtra(POINTS, successArray.count({it}) * (quiz.level.ordinal + 1) * 39)  // wyliczam punkty za quiz "level" liczony od 0, więc dodaję 1 i mnożę razy 39, ot tak sobie (liczba pierwsza)
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun setUpButtons() {
        ans_a.setOnClickListener ( onChoiceListener(false) )
        ans_b.setOnClickListener ( onChoiceListener(false) )
        ans_c.setOnClickListener ( onChoiceListener(false) )
        when (currentPositive) {
            1->{
                ans_a.setSexyText(currentQuestionItem.positive)
                ans_a.setOnClickListener ( onChoiceListener(true) )
                ans_b.setSexyText(getNegativeAnswers()[0])
                ans_c.setSexyText(getNegativeAnswers()[1])
            }
            2->{
                ans_a.setSexyText(getNegativeAnswers()[0])
                ans_b.setSexyText(currentQuestionItem.positive)
                ans_b.setOnClickListener ( onChoiceListener(true) )
                ans_c.setSexyText(getNegativeAnswers()[1])
            }
            3->{
                ans_a.setSexyText(getNegativeAnswers()[0])
                ans_b.setSexyText(getNegativeAnswers()[1])
                ans_c.setSexyText(currentQuestionItem.positive)
                ans_c.setOnClickListener ( onChoiceListener(true) )
            }
        }
    }

    private fun onChoiceListener(isPositive: Boolean) : View.OnClickListener {
        return View.OnClickListener {
            successArray[currentNumber] = isPositive // jeśli odpowie poprawnie, to tutaj wpisuję true
            countDown.cancel()
            if (!isPositive) {
                setButtonColorBrand(DefaultBootstrapBrand.DANGER)   // ustawiam wszystkim 3 widokom kolor czerwony
            }
            when (currentPositive) {
                1 -> ans_a.bootstrapBrand = DefaultBootstrapBrand.SUCCESS
                2 -> ans_b.bootstrapBrand = DefaultBootstrapBrand.SUCCESS
                3 -> ans_c.bootstrapBrand = DefaultBootstrapBrand.SUCCESS
            }

            setButtonsClickable(false)

            resetNextTimer()
            prepareNext.start()
        }
    }

    private fun setButtonsClickable(clickable: Boolean) {
        ans_a.isClickable = clickable
        ans_b.isClickable = clickable
        ans_c.isClickable = clickable
    }

    private fun setButtonColorBrand(brand: DefaultBootstrapBrand) {
        ans_a.bootstrapBrand = brand
        ans_b.bootstrapBrand = brand
        ans_c.bootstrapBrand = brand
    }

    private fun TextView.setSexyText(text: String) {
        if (currentNumber > 0) {
            val anim = AlphaAnimation(1.0f, 0.0f)
            anim.apply {
                duration = 200
                repeatCount = 1
                repeatMode = Animation.REVERSE
                setAnimationListener(object : Animation.AnimationListener{
                    override fun onAnimationRepeat(animation: Animation?) {
                        this@setSexyText.text = text
                    }
                    override fun onAnimationEnd(animation: Animation?) {}
                    override fun onAnimationStart(animation: Animation?) {}
                })
            }
            this.startAnimation(anim)
        } else {
            this.text = text
        }
    }

    private fun getCountDownTimer(): CountDownTimer {
        return object : CountDownTimer(countDownRemain, 100) {
            override fun onFinish() {
                resetNextTimer()    // resetuję timer odpowiadający za czas na przygotowanie kolejnego pytania
                successArray[currentNumber] = false
                setButtonColorBrand(DefaultBootstrapBrand.WARNING)
                setButtonsClickable(false)
                prepareNext.start()
                Toast.makeText(this@QuizActivity, QuizzApp.res.getString(R.string.time_is_up), Toast.LENGTH_SHORT).show()
            }

            override fun onTick(remain: Long) {
                countDownRemain = remain
                timeLeftProgress.progressValue = remain / 1000f
            }

        }
    }

    private fun resetNextTimer() {
        countDownRemain = TIMER_VALUE
        prepareNextRemain = PREPARE_VALUE
        prepareNext = getPrepareNextTimer()
    }

    private fun getPrepareNextTimer(): CountDownTimer {
        return object : CountDownTimer(prepareNextRemain, 10) {
            override fun onFinish() {
                resetCountDownTimer()    // resetuję pierwszy timer
                setButtonsClickable(true)
                setButtonColorBrand(DefaultBootstrapBrand.SECONDARY)
                currentNumber++
                nextQuestion()
            }

            override fun onTick(remain: Long) {
                prepareNextRemain = remain
                timeLeftProgress.progressValue = 40 - remain.toFloat() / 50
            }

        }
    }

    private fun resetCountDownTimer() {
        countDownRemain = TIMER_VALUE
        prepareNextRemain = PREPARE_VALUE
        countDown = getCountDownTimer()
    }

    private fun getNegativeAnswers() : Array<String> {
        when(Random().nextInt(2) + 1) {
            1->{
                return arrayOf(currentQuestionItem.false1, currentQuestionItem.false2)
            }
            2->{
                return arrayOf(currentQuestionItem.false2, currentQuestionItem.false1)
            }
        }
        throw IndexOutOfBoundsException("getNegativeAnswers() wrong parameter")
    }

    override fun onPause() {
        super.onPause()
        countDown.cancel()
        prepareNext.cancel()
    }

    override fun onResume() {
        super.onResume()
        /*if (!countDownRemain.equals(TIMER_VALUE)) {
            countDown = getCountDownTimer()
            countDown.start()
        }*/
        /*if(!prepareNextRemain.equals(PREPARE_VALUE)) {
            prepareNext = getPrepareNextTimer()
            prepareNext.start()
        }*/
    }

    companion object {
        private const val TIMER_VALUE = 40000L
        private const val PREPARE_VALUE = 2000L

        const val QUIZ_NAME = "quiz_name"
        const val SUCCESS_SUMMARY = "success_summary"
        const val POINTS = "points"
    }
}