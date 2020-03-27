package com.example.myapplication.QuizChooser

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_quizitem_list.*


class QuizChooserFragment : Fragment() {

    private lateinit var onStartQuizListener: OnStartQuizListener
    private val quizMap: HashMap<String, QuizItem> = HashMap()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnStartQuizListener) {
            onStartQuizListener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_quizitem_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView()
        setCommunication()
    }

    private fun setCommunication() {
        quizMap.apply {
            put("23", QuizItem())
            put("27", QuizItem(lang = LangEnum.ANDROID))
            put("252", QuizItem())
            put("2", QuizItem(level = LevelEnum.AVERAGE))
        }
    }

    private fun setUpRecyclerView() {
        quest_item_list.layoutManager = GridLayoutManager(context, COLUM_COUNT)
        quest_item_list.adapter = QuizzChooserRecyclerViewAdapter(quizMap, onStartQuizListener)
    }

    interface OnStartQuizListener {
        fun onStartQuizSelected(quiz: QuizItem, quizName: String)
    }



    companion object {
       private const val COLUM_COUNT = 3    // const val musi być przypisane w trakcie kompilacji, nigdy w trakcie działania aplikacji
    }
}