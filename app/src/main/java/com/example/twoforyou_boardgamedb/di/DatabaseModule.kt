package com.example.twoforyou_boardgamedb.di

import android.content.Context
import androidx.room.Room
import com.example.twoforyou_boardgamedb.data.db.local.BoardgameDao
import com.example.twoforyou_boardgamedb.data.db.local.BoardgameDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesBoardgameDao(boardgameDb: BoardgameDb): BoardgameDao = boardgameDb.boardgameDao

    @Provides
    @Singleton
    fun providesBoardgameDb(@ApplicationContext context: Context): BoardgameDb =
        Room.databaseBuilder(context, BoardgameDb::class.java, "boardgame_database")
            .fallbackToDestructiveMigration()
            .build()

}