package com.pranay.musicwiki.api

import com.pranay.musicwiki.util.Constants.Companion.API_KEY

import com.pranay.musicwiki.util.Constants.Companion.FORMAT


class RetrofitHelper(private val apiService: MusicServices) {

    suspend fun getGenre() = apiService.getGenre(apiKey = API_KEY, format = FORMAT)
    suspend fun getGenreDetails(tag: String) =
        apiService.getGenreDetails(apiKey = API_KEY, format = FORMAT, tag = tag)

    suspend fun getTopAlbums(tag: String) =
        apiService.getTopAlbums(apiKey = API_KEY, format = FORMAT, tag = tag)

    suspend fun getTopArtists(tag: String) =
        apiService.getTopArtists(apiKey = API_KEY, format = FORMAT, tag = tag)

    suspend fun getTopTracks(tag: String) =
        apiService.getTopTracks(apiKey = API_KEY, format = FORMAT, tag = tag)

    suspend fun getAlbumDetails(artist: String, album: String) =
        apiService.getAlbumDetails(apiKey = API_KEY, format = FORMAT, artist = artist, album = album)

    suspend fun getTopGenres(artist: String, album: String) =
        apiService.getTopGenres(apiKey = API_KEY, format = FORMAT, artist = artist, album = album)

    suspend fun getArtistDetails(artist: String) =
        apiService.getArtistDetails(apiKey = API_KEY, format = FORMAT, artist = artist)

    suspend fun getArtistTags(mbid: String) =
        apiService.getArtistTags(apiKey = API_KEY, format = FORMAT, mbid = mbid)

    suspend fun getArtistAlbums(mbid: String) =
        apiService.getArtistAlbums(apiKey = API_KEY, format = FORMAT, mbid = mbid)

    suspend fun getArtistTracks(mbid: String) =
        apiService.getArtistTracks(apiKey = API_KEY, format = FORMAT, mbid = mbid)

    suspend fun getArtistInfo(mbid: String) =
        apiService.getArtistInfo(apiKey = API_KEY, format = FORMAT, mbid = mbid)


}