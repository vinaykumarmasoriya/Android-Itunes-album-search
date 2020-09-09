package com.android.albumsearch.data.datasource

import com.android.albumsearch.model.Album
import com.android.albumsearch.model.SearchResult

interface AlbumLocalDataSource : AlbumDataSource{
    fun cacheAlbumQuery(query: String, result: SearchResult<Album>)
}