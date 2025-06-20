package com.test.movemate.data

import com.test.movemate.domain.SearchItem
import kotlinx.coroutines.flow.Flow

interface ISearchRepository {
    fun getSearchResults(): Flow<List<SearchItem>>
}