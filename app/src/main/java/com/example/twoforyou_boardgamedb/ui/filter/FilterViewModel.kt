package com.example.twoforyou_boardgamedb.ui.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twoforyou_boardgamedb.domain.filter.FilterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val repository: FilterRepository
) : ViewModel() {
    private val _state = MutableStateFlow(FilterUiState())
    val state = _state.asStateFlow()

    fun updateBoardgameList(idList: List<Int>) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    boardgameList = repository.getBoardgameListByIdList(idList)
                        .stateIn(viewModelScope).value
                )
            }
        }
    }

}