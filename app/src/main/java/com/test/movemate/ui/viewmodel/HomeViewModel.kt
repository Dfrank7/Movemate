package com.test.movemate.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.movemate.data.IHomeRepository
import com.test.movemate.data.IShipmentRepository
import com.test.movemate.domain.HomeData
import com.test.movemate.domain.TrackingInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repo: IHomeRepository
) : ViewModel() {
    val homeData: StateFlow<HomeData> = repo
        .observeHomeState()
        .stateIn(viewModelScope, SharingStarted.Lazily,
            HomeData()
        )
}