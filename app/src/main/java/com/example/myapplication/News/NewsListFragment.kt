package com.example.myapplication.News

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Profile.UserItem
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_newsitem_list.*

/**
 * Fragment "Nowinki" (Recycler)
 */

class NewsListFragment : Fragment() {

    private lateinit var onNewsInteractionListener: OnNewsInteractionListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_newsitem_list, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnNewsInteractionListener) {
            onNewsInteractionListener = context
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loader_news.visibility = View.VISIBLE
        // ładowanie danych
        feed_item_list.scheduleLayoutAnimation()
        loader_news.visibility = View.GONE
        setUpRecycler()

    }

    private fun setUpRecycler() {
        feed_item_list.layoutManager = LinearLayoutManager(context)
        feed_item_list.adapter = NewsListRecyclerAdapter(mNewsMap, onNewsInteractionListener)
    }

    interface OnNewsInteractionListener {
        fun onUserSelected(user: UserItem, image: View)
        fun onLikeSelected(feedId: String, diff: Int)
    }
}