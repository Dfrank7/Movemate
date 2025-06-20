package com.test.movemate.domain

import androidx.annotation.DrawableRes

data class HomeData (
    val locationLabel: String          = "",
    val trackingInfo: TrackingInfo     = TrackingInfo("", "","","", "","", "", "", ""),
    val vehicles: List<VehicleOption>  = emptyList()
)

data class VehicleOption(
    val title: String,
    val subtitle: String,
    @DrawableRes val image: Int,
    val id: Int
)

