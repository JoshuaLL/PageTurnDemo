package com.joshua.pageturndemo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.joshua.pageturndemo.databinding.FragmentSecondBinding
import com.joshua.pageturndemo.flippage.BookFlipPageTransformer

class SecondFragment: Fragment(R.layout.fragment_second) {

    lateinit var binding: FragmentSecondBinding
    private var mPagerAdapter: PagerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSecondBinding.bind(view)
        binding.pager.let { pager->
            mPagerAdapter = BookPagerAdapter(requireActivity().supportFragmentManager)
            pager.adapter = mPagerAdapter
            pager.clipToPadding = true
            pager.setPageTransformer(true, BookFlipPageTransformer())
        }

    }

    class BookPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
    {
        var fragments = arrayListOf<BookPageIntroFragment>()
        init {
            val titles =
                arrayOf("All About Reading", "Find Your Love", "Pick Your Books", "Enjoy Your Time")
            val subtitles = arrayOf(
                "Everyone love reading books",
                "All books in your library",
                "Books are your best friends",
                "All set and get started now"
            )
            val imageIds = intArrayOf(
                R.drawable.all_about_reading,
                R.drawable.find_your_love,
                R.drawable.pick_your_books,
                R.drawable.enjoy_your_time
            )
            titles.forEachIndexed { index, s ->
                var frag = BookPageIntroFragment.newInstance(titles[index], subtitles[index], imageIds[index])
                fragments.add(frag)
            }

            for (i in 0..3) {

            }

        }
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }
    }
}