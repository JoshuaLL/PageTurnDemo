package com.joshua.pageturndemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.joshua.pageturndemo.databinding.FragmentBookPageLayoutBinding

class BookPageIntroFragment : Fragment(R.layout.fragment_book_page_layout) {

    lateinit var binding:FragmentBookPageLayoutBinding
    var title = ""
    var subtitle = ""
    var imageId = 0

    companion object {
        fun newInstance(title: String?, subtitle: String?, imageId: Int): BookPageIntroFragment {
            val fragment = BookPageIntroFragment()
            val args = Bundle()
            args.putString("title", title)
            args.putString("subtitle", subtitle)
            args.putInt("imageId", imageId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            imageId = getInt("imageId", R.drawable.ic_launcher_background)
            title = getString("title", "")
            subtitle = getString("subtitle", "")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBookPageLayoutBinding.bind(view)
        binding.let {
            it.textView2.text = title
            it.textView3.text = subtitle

            Glide.with(view).load(imageId).into(it.imageView)
            if (imageId == R.drawable.all_about_reading) binding.root.tag = 21 else binding.root.tag = 40
        }
    }
}