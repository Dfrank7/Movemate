package com.test.movemate.data

import com.test.movemate.domain.SearchItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SearchRepository : ISearchRepository {
    override fun getSearchResults(): Flow<List<SearchItem>> = flowOf(
        listOf(
            SearchItem(
                name           = "Summer linen jacket",
                trackingNumber = "#NEJ20089934122231",
                from           = "Barcelona",
                to             = "Paris"
            ),
            SearchItem(
                name           = "Tapered-fit jeans AW",
                trackingNumber = "#NEJ35870264978659",
                from           = "Colombia",
                to             = "Paris"
            ),
            SearchItem(
                name           = "Macbook pro M2",
                trackingNumber = "#NE43857340857904",
                from           = "Paris",
                to             = "Morocco"
            ),
            SearchItem(
                name           = "Slim fit jeans",
                trackingNumber = "#NEJ35870264978659",
                from           = "Colombia",
                to             = "Paris"
            ),
            SearchItem(
                name           = "Office setup desk",
                trackingNumber = "#NEJ23481570754963",
                from           = "France",
                to             = "German"
            )
        )
    )
}