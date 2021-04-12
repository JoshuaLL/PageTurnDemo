package com.joshua.pageturndemo

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.joshua.pageturndemo.databinding.FragmentFirstBinding
import com.joshua.pageturndemo.readwidget.PageTurnView
import com.joshua.pageturndemo.readwidget.TextPageAdapter

class FirstFragment : Fragment(R.layout.fragment_first) {

    lateinit var binding:FragmentFirstBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFirstBinding.bind(view)

        val adapter = TextPageAdapter(requireContext())
        adapter.setText("ARKK Strategy Description This actively managed equity strategy seeks long-term capital growth by investing in the US listed securities, including ADRs, of companies focused on disruptive innovation. ARK defines ‘‘disruptive innovation’’ as the introduction of a technologically enabled product or service that potentially changes the way the world works. Companies in ARK’s cornerstone strategy aim to capture the substantial benefits of new products or services associated with scientific research in DNA technologies, energy storage, the increased use of autonomous technology, next generation internet services, and technologies that make financial services more efficient."
                        +"ARKW Strategy Description This actively managed equity strategy seeks long-term capital growth by investing in the US listed securities, including ADRs, of companies focused on next generation internet. Companies within this strategy aim to capture the substantial benefits of new products and services associated with scientific research and technological break-throughs in internet-based products and services, new payment methods, blockchain technology, big data, the internet of things, mobile, social and streaming media."
                        + "ARKG Strategy Description This actively managed equity strategy seeks long-term capital growth by investing in the US listed securities, including ADRs, of companies focused on the genomics revolution. Companies within this strategy aim to capture the substantial benefits of new products and services associated with technological and scientific developments in DNA sequencing, gene editing, targeted therapeutics, bioinformatics, and agricultural biology."
                         + "ARKQ Strategy Description This actively managed equity strategy seeks long-term capital growth by investing in the US listed securities, including ADRs, of companies focused on autonomous technology and robotics. Companies within this strategy aim to capture the substantial benefits of new products and services associated with scientific research and technological break-throughs in energy storage, transportation, automation and manufacturing, and materials, among other industries.")
        binding.pageTurnView.setPageTurnAdapter(adapter)
    }
}