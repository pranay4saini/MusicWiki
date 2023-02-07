package com.pranay.musicwiki.model

data class ArtistDetailsApiResponse(var artist:ArtistDetailsListResponse)

class ArtistDetailsListResponse (

    var name :String,
    var stats:StatsData,
    var tags :TagsDataResponse,
    var bio:SummaryData

)
class SummaryData(
    var summary :String
)

class StatsData(
    var listeners :String,
    var playcount :String
)

