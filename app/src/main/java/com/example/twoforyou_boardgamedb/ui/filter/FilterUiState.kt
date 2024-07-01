package com.example.twoforyou_boardgamedb.ui.filter

import com.example.twoforyou_boardgamedb.data.model.BoardgameItem

data class FilterUiState(
    val boardgameList: List<BoardgameItem> = emptyList<BoardgameItem>()
)