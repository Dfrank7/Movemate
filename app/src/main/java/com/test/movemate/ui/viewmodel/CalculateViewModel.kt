package com.test.movemate.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.movemate.domain.Calculate
import com.test.movemate.domain.CategoryTag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculateViewModel @Inject constructor(): ViewModel() {

    private val _state = MutableStateFlow(Calculate())
    val uiState: StateFlow<Calculate> = _state.asStateFlow()

    fun onSenderLocationChange(v: String) = _state.update { it.copy(senderLocation = v) }
    fun onReceiverLocationChange(v: String) = _state.update { it.copy(receiverLocation = v) }
    fun onWeightChange(v: String) = _state.update { it.copy(weight = v) }

    fun onPackagingOptionChange(option: String) =
        _state.update { it.copy(packagingOption = option) }

    fun onToggleCategory(tag: CategoryTag) {
        _state.update {
            val newSet = if (it.selectedItems.contains(tag))
                it.selectedItems - tag
            else
                it.selectedItems + tag
            it.copy(selectedItems = newSet)
        }
    }

    fun calculate() {
        viewModelScope.launch {
            val w = _state.value.weight.toIntOrNull() ?: 0
            val total = w * 100 + _state.value.selectedItems.size * 50
            _state.update {
                it.copy(
                    estimatedTotal = total,
                    showTotalEstimateDialog = true
                )
            }
        }
    }

    fun dismissDialog() =
        _state.update { it.copy(showTotalEstimateDialog = false) }
}