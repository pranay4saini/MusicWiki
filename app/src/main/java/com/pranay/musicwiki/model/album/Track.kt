package com.pranay.musicwiki.model.album

data class Track(
    val artist: Artist,
    val duration: String,
    val name: String,
    val url: String
)
