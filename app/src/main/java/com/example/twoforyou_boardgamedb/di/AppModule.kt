package com.example.twoforyou_boardgamedb.di

import com.example.twoforyou_boardgamedb.data.db.local.BoardgameDao
import com.example.twoforyou_boardgamedb.data.repository.detail.DetailRepositoryImpl
import com.example.twoforyou_boardgamedb.data.repository.display.DisplayRepositoryImpl
import com.example.twoforyou_boardgamedb.data.repository.filter.FilterRepositoryImpl
import com.example.twoforyou_boardgamedb.domain.detail.DetailRepository
import com.example.twoforyou_boardgamedb.domain.display.DisplayRepository
import com.example.twoforyou_boardgamedb.domain.filter.FilterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDisplayBoardgameRepository(
        retrofit: Retrofit,
        boardgameDao: BoardgameDao
    ): DisplayRepository {
        return DisplayRepositoryImpl(retrofit, boardgameDao)
    }

    @Provides
    @Singleton
    fun providesDetailRepository(boardgameDao: BoardgameDao): DetailRepository {
        return DetailRepositoryImpl(boardgameDao)
    }

    @Provides
    @Singleton
    fun providesFilteredRepository(boardgameDao: BoardgameDao): FilterRepository {
        return FilterRepositoryImpl(boardgameDao)
    }

}