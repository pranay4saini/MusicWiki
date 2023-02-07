package com.pranay.musicwiki

import com.pranay.musicwiki.api.MusicServices
import com.pranay.musicwiki.api.RetrofitHelper
import com.pranay.musicwiki.model.AlbumApiResponse
import com.pranay.musicwiki.model.AlbumDetailsApiResponse
import com.pranay.musicwiki.model.ArtistApiResponse
import com.pranay.musicwiki.model.ArtistDetailsApiResponse
import com.pranay.musicwiki.model.ArtistTopAlbumsApiResponse
import com.pranay.musicwiki.model.ArtistTopTracksApiResponse
import com.pranay.musicwiki.model.TagDetailsApiResponse
import com.pranay.musicwiki.model.TagsApiResponse
import com.pranay.musicwiki.model.TrackApiResponse
import com.pranay.musicwiki.util.Constants.Companion.API_KEY
import com.pranay.musicwiki.util.Constants.Companion.FORMAT
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit

class Repository(private val retrofitService: MusicServices) {

    val  getTagMethod ="chart.gettoptags"
    val getDetailsMethod = "tag.getinfo"
    val getAlbum = "tag.gettopalbums"
    var getArtist = "tag.gettopartists"
    var getTracks= "tag.gettoptracks"
    var getAlbumInfo= "album.getinfo"
    var getArtistInfo= "artist.getinfo"
    val getArtistTopAlbum="artist.gettopalbums"
    val getArtistToptrack= "artist.gettoptracks"


    suspend fun getTagsApi(): Response<TagsApiResponse> {
        return retrofitService.getTagsApi(apikey = API_KEY,method =getTagMethod,format = FORMAT )
    }

    suspend fun getTagsDetailsApi(tag:String): Response<TagDetailsApiResponse> {
        return retrofitService.getTagsDetailsApi(apikey = API_KEY,method =getDetailsMethod,format = FORMAT, tag= tag )
    }
    suspend fun getAlbumApi(tag:String): Response<AlbumApiResponse> {
        return retrofitService.getAlbumApi(apikey = API_KEY,method = getAlbum,format = FORMAT, tag= tag )
    }

    suspend fun getArtistApi(tag:String): Response<ArtistApiResponse> {
        return retrofitService.getArtistApi(apikey = API_KEY,method = getArtist,format = FORMAT, tag= tag )
    }

    suspend fun getTracksApi(tag:String): Response<TrackApiResponse> {
        return retrofitService.getTracksApi(apikey = API_KEY,method = getTracks,format = FORMAT, tag= tag )
    }

    suspend fun getAlbumDetailsApi(artist:String,album:String): Response<AlbumDetailsApiResponse> {
        return retrofitService.getAlbumDetailsApi(apikey = API_KEY,method = getAlbumInfo,format = FORMAT, artist= artist,  album = album )
    }

    suspend fun getArtistDetailsApi(artist:String): Response<ArtistDetailsApiResponse> {
        return retrofitService.getArtistDetailsApi(apikey = API_KEY,method = getArtistInfo,format = FORMAT, artist= artist )
    }

    suspend fun getArtistTopAlbums(artist:String): Response<ArtistTopAlbumsApiResponse> {
        return retrofitService.getArtistTopAlbums(apikey = API_KEY,method = getArtistTopAlbum,format = FORMAT, artist= artist )
    }

    suspend fun getArtistTopTracks(artist:String): Response<ArtistTopTracksApiResponse> {
        return retrofitService.getArtistTopTracks(apikey = API_KEY,method = getArtistToptrack,format = FORMAT, artist= artist )
    }


}