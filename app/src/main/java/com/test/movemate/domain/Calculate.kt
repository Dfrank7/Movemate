package com.test.movemate.domain

data class Calculate(
    val senderLocation: String = "",
    val receiverLocation: String = "",
    val weight: String = "",
    val packagingOptions: List<String> = listOf("Box", "Container", "Paper Bag"),
    val packagingOption: String = "Box",
    val categoryTags: List<CategoryTag> = listOf(
        CategoryTag("Documents"),
        CategoryTag("Glass"),
        CategoryTag("Liquid"),
        CategoryTag("Food"),
        CategoryTag("Electronic"),
        CategoryTag("Product"),
        CategoryTag("Others")
    ),
    val selectedItems: Set<CategoryTag> = emptySet(),
    val estimatedTotal: Int = 1460,
    val showTotalEstimateDialog: Boolean = false
)

data class CategoryTag(
    val label: String
)