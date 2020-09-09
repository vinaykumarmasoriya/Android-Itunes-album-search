package com.android.albumsearch.repository

import com.android.albumsearch.data.datasource.AlbumLocalDataSource
import com.android.albumsearch.data.datasource.AlbumRemoteDataSource
import com.android.albumsearch.model.Album
import com.android.albumsearch.model.Outcome
import com.android.albumsearch.model.SearchResult
import org.koin.core.KoinComponent

class AlbumRepository(
    private val remoteDataSource: AlbumRemoteDataSource,
    private val localDataSource: AlbumLocalDataSource
) :
    KoinComponent {

    fun getAlbums(query: String, callback: (Outcome<SearchResult<Album>>) -> Unit) {
        localDataSource.getAlbums(query) { cacheOutcome ->
            when (cacheOutcome) {
                is Outcome.Success -> {
                    callback(cacheOutcome)
                }
                is Outcome.Error -> {
                    remoteDataSource.getAlbums(query) { remoteOutcome ->
                        when (remoteOutcome) {
                            is Outcome.Success ->
                                localDataSource.cacheAlbumQuery(query, remoteOutcome.value)
                        }
                        callback(remoteOutcome)
                    }
                }
            }
        }
    }
}