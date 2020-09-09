package com.android.albumsearch

import android.app.Application
import com.android.albumsearch.di.albumSearchModule
import com.android.albumsearch.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AlbumSearchApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AlbumSearchApplication)
            modules(listOf(albumSearchModule, networkModule))
        }
    }
}