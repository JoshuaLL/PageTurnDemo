package com.joshua.pageturndemo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.joshua.pageturndemo.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var binding:FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        binding.btnFirst.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFirstFragment())
        }

        binding.btnSecond.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSecondFragment())
        }
    }
}