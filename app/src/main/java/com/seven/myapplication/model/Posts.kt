package com.seven.myapplication.model

import androidx.room.ColumnInfo
import com.squareup.moshi.Json

data class Posts (

	@Json(name = "id") val id : String,
	@ColumnInfo(name = "thumbnail_image")
	@Json(name = "thumbnail_image") val thumbnail_image : String,
	@ColumnInfo(name = "event_name")
	@Json(name = "event_name") val event_name : String,
	@ColumnInfo(name = "event_date")
	@Json(name = "event_date") val event_date : Long,
	@ColumnInfo(name = "views")
	@Json(name = "views") val views : Int,
	@ColumnInfo(name = "likes")
	@Json(name = "likes") val likes : Int,
	@ColumnInfo(name = "shares")
	@Json(name = "shares") val shares : Int
)