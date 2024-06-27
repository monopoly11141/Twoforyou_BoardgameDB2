package com.example.twoforyou_boardgamedb.data.repository.filter

import com.example.twoforyou_boardgamedb.data.db.local.BoardgameDao
import com.example.twoforyou_boardgamedb.domain.filter.FilterRepository

class FilterRepositoryImpl(
    private val boardgameDao: BoardgameDao
): FilterRepository {
}