package com.android.albumsearch.model

import com.squareup.moshi.Json

data class SearchResult<T>(
    @field:Json(name = "resultCount") val resultCount: Int,
    @field:Json(name = "results") val results: List<T>
)