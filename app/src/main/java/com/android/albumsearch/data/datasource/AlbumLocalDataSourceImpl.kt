package com.android.albumsearch.data.datasource

import com.android.albumsearch.model.Album
import com.android.albumsearch.model.Outcome
import com.android.albumsearch.model.SearchResult

class AlbumLocalDataSourceImpl : AlbumDataSource, AlbumLocalDataSource {

    private var cache: HashMap<String, SearchResult<Album>> = HashMap()

    override fun getAlbums(query: String, callback: (Outcome<SearchResult<Album>>) -> Unit) {
        cache[normalizeQuery(query)]?.let {
            callback(Outcome.Success(value = it))
        } ?: callback(Outcome.Error(message = "No data found", cause = null))
    }

    override fun cacheAlbumQuery(query: String, result: SearchResult<Album>) {
        cache[normalizeQuery(query)] = result
    }

    private fun normalizeQuery(query: String): String = query.toLowerCase().trim()
}