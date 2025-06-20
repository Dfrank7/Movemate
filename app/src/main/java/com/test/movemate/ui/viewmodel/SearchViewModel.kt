package com.test.movemate.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.movemate.data.ISearchRepository
import com.test.movemate.domain.SearchItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: ISearchRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val allItems: StateFlow<List<SearchItem>> =
        repo.getSearchResults()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val results: StateFlow<List<SearchItem>> = combine(allItems, query) { list, q ->
        if (q.isBlank()) list
        else list.filter {
            it.name.contains(q,   ignoreCase = true) ||
                    it.trackingNumber.contains(q,   ignoreCase = true)
        }
    }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun onQueryChanged(new: String) {
        viewModelScope.launch { _query.value = new }
    }
}