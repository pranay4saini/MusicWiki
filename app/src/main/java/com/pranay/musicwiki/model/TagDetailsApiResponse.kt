package com.pranay.musicwiki.model

data class TagDetailsApiResponse(
    val tag : TagDetailsData
)

    class TagDetailsData(
    val name :String,
    val wiki :TagDetailsWiki?
    )
    class TagDetailsWiki(
        val summary :String,
        val content :String
    )


