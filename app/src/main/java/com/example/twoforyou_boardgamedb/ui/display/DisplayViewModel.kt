package com.example.twoforyou_boardgamedb.ui.display

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twoforyou_boardgamedb.data.db.local.BoardgameDb
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
                    boardgameItemList = repository.getAllBoardgameItem().stateIn(viewModelScope).value
                )
            }
            for (boardgameItem in _state.value.boardgameItemList) {
                updateBoardgameItemFromApi(boardgameItem)
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
                        .stateIn(viewModelScope).value,
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

    fun updateDisplayBoardgame(boardgameList: List<BoardgameItem>, searchQuery: String) {
        _state.update {
            it.copy(
                displayBoardgameList = when(_state.value.displayOrder) {
                    DISPLAY_ORDER.ALPHABETICAL -> boardgameList
                    DISPLAY_ORDER.FAVORITE -> boardgameList.filter { it.isFavorite }
                    DISPLAY_ORDER.NON_FAVORITE -> boardgameList.filter { !it.isFavorite }
                    DISPLAY_ORDER.RANKING -> boardgameList.sortedBy { it.ranking }
                    DISPLAY_ORDER.WEIGHT -> boardgameList.sortedBy { it.averageWeight }
                }
            )
        }
        _state.update {
            it.copy(
                displayBoardgameList = if(searchQuery.isBlank()) {
                    _state.value.displayBoardgameList
                }else {
                    _state.value.displayBoardgameList.filter {boardgame -> boardgame.englishName.contains(searchQuery, ignoreCase = true) || boardgame.koreanName.contains(searchQuery, ignoreCase = true)}
                }
            )
        }
    }

    fun setSearchQuery(searchQuery: String) {
        _state.update {
            it.copy(
                searchQuery = searchQuery
            )
        }
    }

}
