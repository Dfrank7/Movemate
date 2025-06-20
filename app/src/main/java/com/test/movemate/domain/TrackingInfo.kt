package com.test.movemate.domain

data class TrackingInfo(
    val number: String,
    val sender: String,
    val senderName: String,
    val time: String,
    val timeWindow: String,
    val receiver: String,
    val receiverName: String,
    val status: String,
    val statusName: String
)