package com.pranay.musicwiki.model

data class AlbumDetailsApiResponse(var album:AlbumDetailsListResponse)


class AlbumDetailsListResponse (

    var name :String,
    var artist:String,
    var tags :TagsDataResponse
)
