package com.android.albumsearch.data.network

import com.android.albumsearch.model.Album
import com.android.albumsearch.model.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumSearchService {
    @GET("/search?term=all")
    fun searchAlbums(@Query("term") term: String): Call<SearchResult<Album>>
}