package com.example.twoforyou_boardgamedb.ui.display

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twoforyou_boardgamedb.data.model.BoardgameItem
import com.example.twoforyou_boardgamedb.domain.display.DisplayRepository
import com.example.twoforyou_boardgamedb.ui.display.util.DISPLAY_ORDER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplayViewModel @Inject constructor(
    private val repository: DisplayRepository
) : ViewModel() {
    private val _state = MutableStateFlow(DisplayUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    boardgameItemList = repository.getAllBoardgameItem().stateIn(viewModelScope).value,
                    displayBoardgameList = repository.getAllBoardgameItem().stateIn(viewModelScope).value,
                )
            }.run {
                for(boardgameItem in state.value.boardgameItemList) {
                    updateBoardgameItemFromApi(boardgameItem)
                }
            }
        }


    }

    fun insertBoardgameItemToDb(url: String): Boolean {
        var id = -1
        try {
            id = url.substringAfter("boardgame/").substringBefore("/").toInt()
        } catch (e: Exception) {
            return false
        }

        repository.getBoardgameItem(id, callback = {
            viewModelScope.launch {
                repository.insertItemToDb(boardgameItem = it)
                _state.update {
                    it.copy(
                        boardgameItemList = repository.getAllBoardgameItem()
                            .stateIn(viewModelScope).value
                    )
                }
            }
        })
        return true
    }

    fun updateBoardgameItemFromApi(boardgameItem: BoardgameItem) {
        val url = boardgameItem.boardgameUrl
        var id = -1
        try {
            id = url.substringAfter("boardgame/").substringBefore("/").toInt()
        } catch (e: Exception) {
            return
        }

        repository.getBoardgameItem(id, callback = {
            boardgameItem.ranking = it.ranking
            boardgameItem.bayesAverageValue = it.bayesAverageValue
            boardgameItem.averageValue = it.averageValue
            boardgameItem.numUsersRated = it.numUsersRated
            boardgameItem.minPlayTimeValue = it.minPlayTimeValue
            boardgameItem.maxPlayersValue = it.maxPlayersValue
            boardgameItem.linkValueList = it.linkValueList
            boardgameItem.averageWeight = it.averageWeight

            viewModelScope.launch {
                repository.updateBoardgameItem(boardgameItem = boardgameItem)
            }
        })
        viewModelScope.launch {
            _state.update {
                it.copy(
                    boardgameItemList = repository.getAllBoardgameItem()
                        .stateIn(viewModelScope).value
                )
            }
        }
    }

    fun deleteBoardgameItem(boardgameItem: BoardgameItem) {
        viewModelScope.launch {
            repository.deleteBoardgameItem(boardgameItem)
            _state.update {
                it.copy(
                    boardgameItemList = repository.getAllBoardgameItem()
                        .stateIn(viewModelScope).value
                )
            }
        }
    }

    fun editBoardgameItem(boardgameItem: BoardgameItem) {
        viewModelScope.launch {
            repository.updateBoardgameItem(boardgameItem)
            _state.update {
                it.copy(
                    boardgameItemList = repository.getAllBoardgameItem()
                        .stateIn(viewModelScope).value
                )
            }
        }
    }

    fun updateDisplayingBoardgameItemList(searchQuery: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    displayBoardgameList =
                    if (searchQuery.isBlank()) {
                        state.value.boardgameItemList
                    } else {
                        repository.getBoardgameFromKeyword(searchQuery)
                            .stateIn(viewModelScope).value
                    }

                )
            }
        }
    }

    fun updateDisplayOrder(displayOrder: DISPLAY_ORDER) {
        _state.update {
            it.copy(
                displayOrder = displayOrder
            )
        }
    }

    fun setBottomBarLabelText() {
        _state.update {
            it.copy(
                bottomBarLabelText = when (_state.value.displayOrder) {
                    DISPLAY_ORDER.ALPHABETICAL -> "전체"
                    DISPLAY_ORDER.FAVORITE -> "즐겨찾기"
                    DISPLAY_ORDER.NON_FAVORITE -> "즐겨찾기 제외"
                    DISPLAY_ORDER.RANKING -> "전체"
                    DISPLAY_ORDER.WEIGHT -> "전체"
                }
            )
        }
    }

    fun filterBoardgame(
        playerCountRange: ClosedFloatingPointRange<Float>,
        bayesAverageRange: ClosedFloatingPointRange<Float>,
        weightRange: ClosedFloatingPointRange<Float>
    ): List<Int> {

        val filteredBoardgameId = mutableListOf<Int>()

        state.value.boardgameItemList.filter {
            it.minPlayersValue >= playerCountRange.start &&
                    if (playerCountRange.endInclusive.toInt() == 10) {
                        true
                    } else {
                        it.maxPlayersValue <= playerCountRange.endInclusive
                    } &&
                    it.bayesAverageValue >= bayesAverageRange.start &&
                    it.bayesAverageValue <= bayesAverageRange.endInclusive &&
                    it.averageWeight >= weightRange.start &&
                    it.averageWeight <= weightRange.endInclusive
        }.forEach {
            filteredBoardgameId.add(it.id)
        }
        return filteredBoardgameId
    }

    fun setShowAddBoardgameDialog(showAddBoardgameDialog : Boolean) {
        _state.update {
            it.copy(
                showAddBoardgameDialog = showAddBoardgameDialog
            )
        }
    }

    fun updateDisplayBoardgame() {
        _state.update {
            it.copy(
                displayBoardgameList = when(_state.value.displayOrder) {
                    DISPLAY_ORDER.ALPHABETICAL -> state.value.boardgameItemList
                    DISPLAY_ORDER.FAVORITE -> state.value.boardgameItemList.filter { it.isFavorite }
                    DISPLAY_ORDER.NON_FAVORITE -> state.value.boardgameItemList.filter { !it.isFavorite }
                    DISPLAY_ORDER.RANKING -> state.value.boardgameItemList.sortedBy { it.ranking }
                    DISPLAY_ORDER.WEIGHT -> state.value.boardgameItemList.sortedBy { it.averageWeight }
                }
            )
        }
    }

}
