package com.test.movemate.di

import com.test.movemate.data.HomeRepository
import com.test.movemate.data.IHomeRepository
import com.test.movemate.data.ISearchRepository
import com.test.movemate.data.IShipmentRepository
import com.test.movemate.data.SearchRepository
import com.test.movemate.data.ShipmentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides @Singleton
    fun provideHomeRepository(): IHomeRepository = HomeRepository()

    @Provides @Singleton
    fun provideSearchRepository(): ISearchRepository = SearchRepository()

    @Provides
    @Singleton
    fun provideShipmentRepository(): IShipmentRepository = ShipmentRepository()
}