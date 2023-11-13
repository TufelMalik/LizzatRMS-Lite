package com.tufelmalik.lizzatresturentlite.ui.admin.fragment

import com.tufelmalik.lizzatresturentlite.ui.admin.adapters.AdminHomeAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.denzcoskun.imageslider.ImageSlider
import com.tufelmalik.lizzatresturentlite.R
import com.tufelmalik.lizzatresturentlite.data.AdminHomeItem
import com.tufelmalik.lizzatresturentlite.databinding.FragmentHomeBinding
import com.tufelmalik.lizzatresturentlite.ui.admin.viewmodels.AdminHomeViewModel

class HomeFragment : Fragment() {
    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    private val viewModel: AdminHomeViewModel by lazy {
        AdminHomeViewModel()
    }
    private lateinit var imageSlider: ImageSlider



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBanner()
        setHomeRecyclerView()
    }

    private fun setHomeRecyclerView() {
        val itemList = listOf(
            AdminHomeItem(R.drawable.ic_staff, "View Staff"),
            AdminHomeItem(R.drawable.ic_waiter, "Waiter"),
            AdminHomeItem(R.drawable.ic_cook, "Cook"),
            AdminHomeItem(R.drawable.ic_add_food, "Add Food"),
            AdminHomeItem(R.drawable.ic_edit_food, "Edit Food"),
            AdminHomeItem(R.drawable.ic_delete_food, "Delete Food"),

        )

        val adapter = AdminHomeAdapter(itemList, requireContext())
        binding.adminHomeRecyclerView.layoutManager = GridLayoutManager(requireContext(),3)
        binding.adminHomeRecyclerView.adapter = adapter
    }

    private fun setBanner() {
        imageSlider = binding.imageSlider
        viewModel.setHomeScreenBanner(imageSlider)

    }

}