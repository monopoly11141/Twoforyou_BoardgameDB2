package com.example.twoforyou_boardgamedb.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twoforyou_boardgamedb.domain.detail.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DetailRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DetailUiState())
    val state = _state.asStateFlow()

    fun updateDetailBoardgameItemById(id: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    detailBoardgameItem = repository.getBoardgameItemByRoomKey(id)
                )
            }
        }
    }

}