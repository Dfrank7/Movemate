package com.test.movemate.domain

import kotlin.random.Random

data class ShipmentItem (
    val shipments: List<ShipmentData> = emptyList()
)

data class ShipmentData(
    val id: Long = Random.nextLong(),
    val title: String,
    val description: String,
    val date: String,
    val amount: String,
    val status: ShipmentStatus
)

enum class ShipmentStatus(val data: String) {
    Loading("loading"),
    InProgress("in-progress"),
    Pending("pending"),
    Completed("completed"),
    Canceled("canceled");
}
