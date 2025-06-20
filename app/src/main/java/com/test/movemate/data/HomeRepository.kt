package com.test.movemate.data

import com.test.movemate.R
import com.test.movemate.domain.HomeData
import com.test.movemate.domain.TrackingInfo
import com.test.movemate.domain.VehicleOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class HomeRepository: IHomeRepository {
    override fun observeHomeState(): Flow<HomeData> = flowOf(
        HomeData(
            locationLabel = "Wertheimer, Illinois",
            trackingInfo  = TrackingInfo(
                number  = "NEJ20089934122231",
                sender = "Sender",
                senderName = "Atlanta, 5243",
                time = "Time",
                timeWindow = "2–3 days",
                receiver = "Receiver",
                receiverName   = "Chicago, 6342",
                status = "Status",
                statusName = "Waiting to collect"
            ),
            vehicles = listOf(
                VehicleOption("Ocean freight", "International", R.drawable.ocean_freight, 1),
                VehicleOption("Cargo freight", "Reliable", R.drawable.cargo_freight, 2),
                VehicleOption("Air freight",   "Fast", R.drawable.air_freight, 3)
            )
        )
    )
}