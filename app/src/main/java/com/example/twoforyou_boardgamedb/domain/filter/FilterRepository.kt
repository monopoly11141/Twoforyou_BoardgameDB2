package com.example.twoforyou_boardgamedb.domain.filter

import com.example.twoforyou_boardgamedb.data.model.BoardgameItem
import kotlinx.coroutines.flow.Flow

interface FilterRepository {
    fun getBoardgameListByIdList(idList: List<Int>): Flow<List<BoardgameItem>>
}