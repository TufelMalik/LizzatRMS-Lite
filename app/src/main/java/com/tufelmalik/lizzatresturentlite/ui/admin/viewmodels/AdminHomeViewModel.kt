package com.tufelmalik.lizzatresturentlite.ui.admin.viewmodels

import androidx.lifecycle.ViewModel
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.tufelmalik.lizzatresturentlite.R

class AdminHomeViewModel : ViewModel() {
    private val imageList = listOf(
        SlideModel(R.drawable.banner1, ScaleTypes.FIT),
        SlideModel(R.drawable.banner4 , ScaleTypes.FIT),
        SlideModel(R.drawable.banner2, ScaleTypes.FIT),
        SlideModel(R.drawable.banner5 , ScaleTypes.FIT),
        SlideModel(R.drawable.banner3 , ScaleTypes.FIT),
        SlideModel(R.drawable.banner6 , ScaleTypes.FIT)
    )

    fun setHomeScreenBanner(imageSlider: ImageSlider) {
        imageSlider.setImageList(imageList, ScaleTypes.FIT)
    }


}