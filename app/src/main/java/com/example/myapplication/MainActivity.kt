package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.QuizChooser.QuizChooserFragment
import com.example.myapplication.QuizChooser.QuizItem
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*   // import layoutu #javassie
import java.lang.IllegalStateException

class MainActivity : AppCompatActivity(),
    QuizChooserFragment.OnStartQuizListener {

    override fun onStartQuizSelected(quiz: QuizItem, quizName: String) {
        Log.i("MAIN_ACTIVITY", "OnStartQuizSelected")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setViewPager()
    }

    //region setViewPager() - Ustawienie ViewPagera i bottomNavigation
    private fun setViewPager() {
        viewpager.adapter = getFragmentPagerAdapter()
        navigation.setOnNavigationItemSelectedListener(getBottomNavigationItemSelectedListener())
        viewpager.addOnPageChangeListener(getOnPageChangeListener())
        viewpager.offscreenPageLimit = 2
    }



    private fun getFragmentPagerAdapter() =
        object : FragmentPagerAdapter(supportFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getItem(position: Int) = when (position) {     // when zwraca wartość (w tym przypadku oczekujemy Fragmentu)
                FEED_ID -> Fragment()//NewsListFragment()
                CHOOSER_ID -> QuizChooserFragment()
                PROFILE_ID -> Fragment()//ProfileFragment()
                else -> {
                    Log.wtf("ValueOutOfBounds", "WTF!?")
                    throw IllegalStateException("Wskazana wartość $position wychodzi poza zakres ViewPager'a")
                }
            }

            override fun getCount() = 3

        }

    private fun getBottomNavigationItemSelectedListener() =
        BottomNavigationView.OnNavigationItemSelectedListener {item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    viewpager.currentItem = 0
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_dashboard -> {
                    viewpager.currentItem = 1
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {
                    viewpager.currentItem = 2
                    return@OnNavigationItemSelectedListener true
                }
                else -> false
            }
        }

    private fun getOnPageChangeListener() =
        object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                navigation.menu.getItem(position).isChecked = true
            }
        }
    //endregion

    //region Obsługa wyników z okien
    //endregion

    //region Obsługa interfejsów z fragmentów
    //endregion

    companion object {
        const val FEED_ID = 0
        const val CHOOSER_ID = 1
        const val PROFILE_ID = 2
    }
}
