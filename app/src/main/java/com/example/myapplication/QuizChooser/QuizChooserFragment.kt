package com.example.myapplication.QuizChooser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_quizitem_list.*

class QuizChooserFragment : Fragment() {

    private val quizMap: HashMap<String, QuizItem> = HashMap()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)  // onCreateView() w klasie nadrzędnej zwraca "inflater.inflate(layout_id, container, false)"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView()
        setCommunication()
    }

    private fun setUpRecyclerView() {
        quest_item_list.layoutManager = GridLayoutManager(context, COLUM_COUNT)
        quest_item_list.adapter = QuizChooserRecyclerViewAdapter(quizMap, onStartQuizListener)
    }



    companion object {
       private const val COLUM_COUNT = 3
    }
}