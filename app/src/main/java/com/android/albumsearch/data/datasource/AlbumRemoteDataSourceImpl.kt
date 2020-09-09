package com.android.albumsearch.data.datasource

import com.android.albumsearch.data.network.AlbumSearchService
import com.android.albumsearch.model.Album
import com.android.albumsearch.model.Outcome
import com.android.albumsearch.model.SearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumRemoteDataSourceImpl(private val api: AlbumSearchService) : AlbumRemoteDataSource {
    private var currentSearchAlbumsCall: Call<SearchResult<Album>>? = null

    override fun getAlbums(query: String, callback: (Outcome<SearchResult<Album>>) -> Unit) {
        currentSearchAlbumsCall?.cancel()
        currentSearchAlbumsCall = api.searchAlbums(query)
        currentSearchAlbumsCall?.enqueue(object : Callback<SearchResult<Album>> {
            override fun onFailure(call: Call<SearchResult<Album>>, t: Throwable?) {
                if (!call.isCanceled) {
                    currentSearchAlbumsCall = null
                    Outcome.Error(
                        message = t?.message ?: "Server request failed due to an unknown exception",
                        cause = t
                    )
                }
            }

            override fun onResponse(
                call: Call<SearchResult<Album>>,
                response: Response<SearchResult<Album>>
            ) {
                currentSearchAlbumsCall = null
                if (response.isSuccessful) {
                    response.body()?.let {
                        callback(Outcome.Success(value = it))
                    } ?: callback(Outcome.Error(message = "Response body is empty", cause = null))
                } else {
                    callback(Outcome.Error(message = response.message(), cause = null))
                }
            }
        })
    }
}