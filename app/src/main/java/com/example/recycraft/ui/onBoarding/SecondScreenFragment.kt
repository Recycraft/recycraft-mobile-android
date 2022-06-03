package com.example.recycraft.ui.onBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.recycraft.R
import com.example.recycraft.R.layout.fragment_second_screen
import kotlinx.android.synthetic.main.fragment_second_screen.view.*

class SecondScreenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(fragment_second_screen, container, false)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        view.previous.setOnClickListener {
            viewPager?.currentItem = 0
        }
        view.next.setOnClickListener {
            viewPager?.currentItem = 2
        }
        return view
    }
}