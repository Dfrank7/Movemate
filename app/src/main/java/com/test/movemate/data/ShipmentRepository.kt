package com.test.movemate.data

import com.test.movemate.domain.ShipmentData
import com.test.movemate.domain.ShipmentStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlin.random.Random

class ShipmentRepository : IShipmentRepository {
    override fun getShipments(count: Int): Flow<List<ShipmentData>> = flowOf(
        List(count) {
            ShipmentData(
                title       = "Arriving today!",
                description = "Your delivery, #NEJ20089934122231 from Atlanta, is arriving today!",
                amount      = "$1400 USD",
                date        = "Sep 20, 2023",
                status      = ShipmentStatus.values()[Random.nextInt(ShipmentStatus.values().size)]
            )
        }
    )
}