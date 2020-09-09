package com.android.albumsearch.di

import com.android.albumsearch.data.datasource.AlbumLocalDataSourceImpl
import com.android.albumsearch.data.datasource.AlbumRemoteDataSourceImpl
import com.android.albumsearch.data.network.AlbumSearchService
import com.android.albumsearch.repository.AlbumRepository
import com.android.albumsearch.ui.AlbumSearchViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

val networkModule: Module = module {

    single {
        Moshi.Builder().add(Date::class.java, Rfc3339DateJsonAdapter()).build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }
}

val albumSearchModule: Module = module {
    single { (get() as Retrofit).create(AlbumSearchService::class.java) }
    single { AlbumRemoteDataSourceImpl(get()) }
    single { AlbumLocalDataSourceImpl() }
    single {
        AlbumRepository(
            get() as AlbumRemoteDataSourceImpl,
            get() as AlbumLocalDataSourceImpl
        )
    }
    viewModel { AlbumSearchViewModel(get()) }
}

