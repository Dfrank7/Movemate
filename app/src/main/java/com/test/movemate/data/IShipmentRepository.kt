package com.test.movemate.data

import com.test.movemate.domain.ShipmentData
import com.test.movemate.domain.TrackingInfo
import kotlinx.coroutines.flow.Flow

interface IShipmentRepository {
    fun getShipments(count: Int = 12): Flow<List<ShipmentData>>
}