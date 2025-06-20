package com.test.movemate.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.movemate.data.IShipmentRepository
import com.test.movemate.domain.ShipmentItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ShipmentViewModel @Inject constructor(
    private val repo: IShipmentRepository
) : ViewModel() {

    val uiState: StateFlow<ShipmentItem> = repo
        .getShipments()
        .map { ShipmentItem(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ShipmentItem()
        )
}