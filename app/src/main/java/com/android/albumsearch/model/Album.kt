package com.android.albumsearch.model

import com.squareup.moshi.Json
import java.util.*

data class Album(
    @field:Json(name = "artistName") val artistName: String,
    @field:Json(name = "collectionName") val collectionName: String,
    @field:Json(name = "releaseDate") val releaseDate: Date,
    @field:Json(name = "artworkUrl100") val artworkUrl: String,
    @field:Json(name = "trackId") val trackId: Long,
    @field:Json(name = "collectionPrice") val collectionPrice: Float,
    @field:Json(name = "trackName") val trackName: String

    )