package com.joshua.pageturndemo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.joshua.pageturndemo.databinding.FragmentSecondBinding
import com.joshua.pageturndemo.flippage.BookFlipPageTransformer2

class SecondFragment: Fragment(R.layout.fragment_second) {

    private val viewModel:SecondViewModel by viewModels()

    lateinit var binding: FragmentSecondBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSecondBinding.bind(view)

        viewModel.pageFragments.observe(viewLifecycleOwner){
            it?.let {
                val mPagerAdapter = BookPagerAdapter(
                    it,
                    requireActivity().supportFragmentManager,
                    lifecycle
                )
                binding.pager.let { pager->
                    pager.adapter = mPagerAdapter
                    pager.clipToPadding = true
                    pager.setPageTransformer(BookFlipPageTransformer2())
                }
            }
        }

        viewModel.setUpData()
    }

}

class BookPagerAdapter(
    private val pageFragmentsCreators: MutableMap<Int, Fragment>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle)
{
    override fun getItemCount(): Int {
        return pageFragmentsCreators.size
    }

    override fun createFragment(position: Int): Fragment {
        return pageFragmentsCreators[position] ?: throw IndexOutOfBoundsException()
    }
}