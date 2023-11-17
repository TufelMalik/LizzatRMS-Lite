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
import com.tufelmalik.lizzatresturentlite.ui.viewodel.AdminHomeViewModel

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
        setHomeRestaurantsRV()
        setHomeRole()
        setHomeChat()
    }

    private fun setHomeRestaurantsRV() {
        val itemList = listOf(
            AdminHomeItem(R.drawable.ic_staff, "View Staff"),
            AdminHomeItem(R.drawable.ic_food_menu, "Food List"),
            AdminHomeItem(R.drawable.ic_total_sold, "Total Sell")
        )
        val adapter = AdminHomeAdapter(itemList, requireContext(),"res")
        binding.adminHomeRestaurantRecycler.layoutManager = GridLayoutManager(requireContext(),3)
        binding.adminHomeRestaurantRecycler.adapter = adapter
    }
    private fun setHomeRole() {
        val itemList = listOf(
            AdminHomeItem(R.drawable.ic_waiter, "a Waiter"),
            AdminHomeItem(R.drawable.ic_cook, "a Cook"),
            AdminHomeItem(R.drawable.ic_cashier, "a Cashier")
        )

        val adapter = AdminHomeAdapter(itemList, requireContext(),"role")
        binding.adminHomeRoleRecycler.layoutManager = GridLayoutManager(requireContext(),3)
        binding.adminHomeRoleRecycler.adapter = adapter
    }
    private fun setHomeChat() {
        val itemList = listOf(
            AdminHomeItem(R.drawable.ic_cook, getString(R.string.cook)),
            AdminHomeItem(R.drawable.ic_waiter, getString(R.string.waiter)),
            AdminHomeItem(R.drawable.ic_cashier, getString(R.string.cashier))
        )

        val adapter = AdminHomeAdapter(itemList, requireContext(),"chat")
        binding.adminHomeChatStaffRecycler.layoutManager = GridLayoutManager(requireContext(),3)
        binding.adminHomeChatStaffRecycler.adapter = adapter
    }

    private fun setBanner() {
        imageSlider = binding.imageSlider
        viewModel.setHomeScreenBanner(imageSlider)
    }

}