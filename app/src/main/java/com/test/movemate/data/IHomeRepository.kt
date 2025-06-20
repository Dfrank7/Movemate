package com.test.movemate.data

import com.test.movemate.domain.HomeData
import kotlinx.coroutines.flow.Flow

interface IHomeRepository {
    fun observeHomeState(): Flow<HomeData>
}