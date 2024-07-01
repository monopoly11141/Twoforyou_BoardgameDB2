package com.example.twoforyou_boardgamedb.data.repository.filter

import com.example.twoforyou_boardgamedb.data.db.local.BoardgameDao
import com.example.twoforyou_boardgamedb.data.model.BoardgameItem
import com.example.twoforyou_boardgamedb.domain.filter.FilterRepository
import kotlinx.coroutines.flow.Flow

class FilterRepositoryImpl(
    private val boardgameDao: BoardgameDao
): FilterRepository {
    override fun getBoardgameListByIdList(idList: List<Int>): Flow<List<BoardgameItem>> {
        return boardgameDao.getBoardgameListByIdList(idList)
    }
}