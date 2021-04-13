package com.joshua.pageturndemo

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SecondViewModel:ViewModel() {

    private val titles =
        arrayOf("All About Reading", "Find Your Love", "Pick Your Books", "Enjoy Your Time")
    private val subtitles = arrayOf(
        "Everyone love reading books",
        "All books in your library",
        "Books are your best friends",
        "All set and get started now"
    )
    private val imageIds = intArrayOf(
        R.drawable.all_about_reading,
        R.drawable.find_your_love,
        R.drawable.pick_your_books,
        R.drawable.enjoy_your_time
    )
    private val _pageFragments = MutableLiveData<MutableMap<Int, Fragment>>()
    val pageFragments: LiveData<MutableMap<Int, Fragment>> = _pageFragments

    fun setUpData(){
        viewModelScope.launch {
            mutableMapOf<Int, Fragment>().let {
                titles.forEachIndexed { index, s ->
                    it[index] =
                        BookPageIntroFragment.newInstance(titles[index], subtitles[index], imageIds[index])
                }
                _pageFragments.value =it
            }

        }
    }
}