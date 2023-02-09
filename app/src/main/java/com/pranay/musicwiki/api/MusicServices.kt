package com.pranay.musicwiki.api



import com.pranay.musicwiki.model.album.AlbumDetails
import com.pranay.musicwiki.model.artistTopAlbum.ArtistTopAlbums
import com.pranay.musicwiki.model.artistTopTracks.ArtistTopTracks
import com.pranay.musicwiki.model.artists.ArtistsDetails
import com.pranay.musicwiki.model.genre.Genre
import com.pranay.musicwiki.model.genreDetails.GenreDetails
import com.pranay.musicwiki.model.topAlbum.TopAlbums
import com.pranay.musicwiki.model.topArtist.TopArtists
import com.pranay.musicwiki.model.topGenres.TopGenres
import com.pranay.musicwiki.model.topTracks.TopTracks
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicServices {

    @GET("?method=tag.getTopTags")
    suspend fun getGenre(
        @Query(value = "format") format: String,
        @Query(value = "api_key") apiKey: String
    ): Response<Genre>

    @GET("?method=tag.getinfo")
    suspend fun getGenreDetails(
        @Query(value = "tag") tag: String,
        @Query(value = "api_key") apiKey: String,
        @Query(value = "format") format: String
    ): Response<GenreDetails>

    @GET("?method=tag.gettopalbums")
    suspend fun getTopAlbums(
        @Query(value = "tag") tag: String,
        @Query(value = "api_key") apiKey: String,
        @Query(value = "format") format: String
    ): Response<TopAlbums>

    @GET("?method=tag.gettopartists")
    suspend fun getTopArtists(
        @Query(value = "tag") tag: String,
        @Query(value = "api_key") apiKey: String,
        @Query(value = "format") format: String
    ): Response<TopArtists>

    @GET("?method=tag.gettoptracks")
    suspend fun getTopTracks(
        @Query(value = "tag") tag: String,
        @Query(value = "api_key") apiKey: String,
        @Query(value = "format") format: String
    ): Response<TopTracks>

    @GET("?method=album.getinfo")
    suspend fun getAlbumDetails(
        @Query(value = "artist", encoded = true) artist: String,
        @Query(value = "album", encoded = true) album: String,
        @Query(value = "api_key") apiKey: String,
        @Query(value = "format") format: String
    ): Response<AlbumDetails>

    @GET("?method=album.gettoptags")
    suspend fun getTopGenres(
        @Query(value = "artist", encoded = true) artist: String,
        @Query(value = "album", encoded = true) album: String,
        @Query(value = "api_key") apiKey: String,
        @Query(value = "format") format: String
    ): Response<TopGenres>

    @GET("?method=artist.getinfo")
    suspend fun getArtistDetails(
        @Query(value = "artist", encoded = true) artist: String,
        @Query(value = "api_key") apiKey: String,
        @Query(value = "format") format: String
    ): Response<ArtistsDetails>

    @GET("?method=artist.getinfo")
    suspend fun getArtistInfo(
        @Query(value = "mbid") mbid: String,
        @Query(value = "api_key") apiKey: String,
        @Query(value = "format") format: String
    ): Response<ArtistsDetails>

    @GET("?method=artist.getTopTags")
    suspend fun getArtistTags(
        @Query(value = "mbid") mbid: String,
        @Query(value = "api_key") apiKey: String,
        @Query(value = "format") format: String
    ): Response<TopGenres>

    @GET("?method=artist.getTopAlbums")
    suspend fun getArtistAlbums(
        @Query(value = "mbid") mbid: String,
        @Query(value = "api_key") apiKey: String,
        @Query(value = "format") format: String
    ): Response<ArtistTopAlbums>

    @GET("?method=artist.getTopTracks")
    suspend fun getArtistTracks(
        @Query(value = "mbid") mbid: String,
        @Query(value = "api_key") apiKey: String,
        @Query(value = "format") format: String
    ): Response<ArtistTopTracks>



}