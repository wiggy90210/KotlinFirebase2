package com.example.myapplication.QuizChooser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R

class QuizzChooserRecyclerViewAdapter(private val quizMap: HashMap<String, QuizItem>,
                                      private val onStartQuizListener: QuizChooserFragment.OnStartQuizListener) : RecyclerView.Adapter<QuizzChooserRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val levelIV = mView.findViewById<View>(R.id.levelImageView) as ImageView       //w tym wypadku syntetyki są niewydajne, bo tworzą one dodatkowe klasy, które za każdym razem się instancjonują
        val langIV = mView.findViewById<View>(R.id.langImageView) as ImageView
        val quizTitleTV = mView.findViewById<View>(R.id.quizTitle) as TextView

        lateinit var mItem: QuizItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_quizitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sorted = quizMap.values.toList().sortedBy { quizItem -> (quizItem.level.ordinal + quizItem.lang.ordinal * 10) } // 0,1,2; 10,11,12; 20,21,22
        holder.mItem = sorted[position]

        holder.langIV.setImageResource(holder.mItem.lang.image)
        holder.levelIV.setImageResource(holder.mItem.level.image)
        holder.quizTitleTV.text  = getDoubleLineTitle(holder.mItem)

        holder.mView.setOnClickListener {
            onStartQuizListener.onStartQuizSelected(holder.mItem, getSingleLineTitle(holder.mItem))
        }


    }

    override fun getItemCount(): Int {
        return quizMap.size
    }

    private fun getSingleLineTitle(mItem: QuizItem) =
        "${mItem.lang.getString()} ${mItem.level.getString()}"

    private fun getDoubleLineTitle(mItem: QuizItem) =
        "${mItem.lang.getString()} \n ${mItem.level.getString()}"


}