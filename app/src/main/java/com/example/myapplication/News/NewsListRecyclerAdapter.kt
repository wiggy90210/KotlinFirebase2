package com.example.myapplication.News

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail
import com.bumptech.glide.Glide
import com.example.myapplication.R

class NewsListRecyclerAdapter(private val mNewsMap: HashMap<String, NewsItem>,
                              private val onNewsInteractionListener: NewsListFragment.OnNewsInteractionListener
) : RecyclerView.Adapter<NewsListRecyclerAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mItem: NewsItem? = null

       val name  = itemView.findViewById<TextView>(R.id.name)!!
       val title = itemView.findViewById<TextView>(R.id.quizTitle)!!
       val time = itemView.findViewById<TextView>(R.id.time)!!
       val comment = itemView.findViewById<TextView>(R.id.comment)!!
       val pointsText = itemView.findViewById<TextView>(R.id.pointsText)!!
       val respects = itemView.findViewById<TextView>(R.id.respects)!!
       val likesImage = itemView.findViewById<CheckBox>(R.id.likesImage)!!
       val profileImage = itemView.findViewById<BootstrapCircleThumbnail>(R.id.circleImageProfile)!!
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_newsitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sortedList = mNewsMap.toList().sortedWith(Comparator { o1, o2 -> if (o1.second.timeMillis < o2.second.timeMillis) 1 else -1 })
        val second = sortedList[position].second

        holder.mItem = second

        holder.name.text = second.user
        holder.title.text = second.quiz
        holder.time.text = second.timeMillis.div(1000L*60L).toString() // w minutach
        holder.comment.text = second.comment
        holder.pointsText.text = second.points.toString()
        holder.respects.text = countRespects(second)
        holder.likesImage.isChecked = !mNewsMap.containsKey(second.uid)
        Glide.with(holder.itemView.context)
            .load(second.image)
            .into(holder.profileImage)

    }

    private fun countRespects(second: NewsItem): String {
        return second.respects.values.count {it == 1}.plus(1).toString()   // dodatkowy like od deva
    }

    private fun getElapsedTimeMinutes(timeMillis: Long): String {
        val elapsedTimeSec = (System.currentTimeMillis() - timeMillis) / 1000
        val format = String.format("%%0%dd", 2)
        return when {
            (elapsedTimeSec / 3600 > 24) -> {
                val days = elapsedTimeSec / (60*60*24)
                String.format(format, days) + "d"
            }
            (elapsedTimeSec / 60 > 60) -> {
                val hours = elapsedTimeSec / (60*60)
                String.format(format, hours) + "h"
            }
            else -> {
                String.format(format, elapsedTimeSec / 60) + "m"
            }
        }
    }

    override fun getItemCount(): Int {
        return mNewsMap.size
    }

}
