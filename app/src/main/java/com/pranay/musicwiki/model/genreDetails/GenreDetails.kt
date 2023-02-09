package com.pranay.musicwiki.model.genreDetails

import com.google.gson.annotations.SerializedName

data class GenreDetails(
    @SerializedName("tag") val genreDetailsTag: GenreDetailsTags
)
