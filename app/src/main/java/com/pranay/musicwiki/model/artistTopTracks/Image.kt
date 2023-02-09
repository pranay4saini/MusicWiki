package com.pranay.musicwiki.model.artistTopTracks

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("#text")val url: String,
    val size: String
)
