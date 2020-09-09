package com.android.albumsearch.data.datasource

import com.android.albumsearch.model.Album
import com.android.albumsearch.model.Outcome
import com.android.albumsearch.model.SearchResult

interface AlbumDataSource {
    fun getAlbums(query: String, callback: (Outcome<SearchResult<Album>>) -> Unit)
}