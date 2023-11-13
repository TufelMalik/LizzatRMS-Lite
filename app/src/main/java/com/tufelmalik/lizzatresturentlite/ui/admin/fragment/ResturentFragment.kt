package com.tufelmalik.lizzatresturentlite.ui.admin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tufelmalik.lizzatresturentlite.databinding.FragmentResturentBinding

class ResturentFragment : Fragment() {
    private val binding : FragmentResturentBinding by lazy {
        FragmentResturentBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


       return binding.root
    }


}