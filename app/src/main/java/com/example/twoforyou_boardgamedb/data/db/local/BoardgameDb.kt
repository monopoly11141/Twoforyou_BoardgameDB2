package com.example.twoforyou_boardgamedb.data.db.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.twoforyou_boardgamedb.data.db.helper.LinkValueListConverter
import com.example.twoforyou_boardgamedb.data.model.BoardgameItem

@Database(
    entities = [BoardgameItem::class],
    version = 1,
    autoMigrations = []
)
@TypeConverters(
    LinkValueListConverter::class
)
abstract class BoardgameDb : RoomDatabase() {
    abstract val boardgameDao: BoardgameDao
}