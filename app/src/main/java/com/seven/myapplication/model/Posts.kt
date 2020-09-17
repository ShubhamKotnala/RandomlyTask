package com.seven.myapplication.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Posts (

	@SerializedName("id") val id : String,
	@ColumnInfo(name = "thumbnail_image")
	@SerializedName("thumbnail_image") val thumbnail_image : String,
	@ColumnInfo(name = "event_name")
	@SerializedName("event_name") val event_name : String,
	@ColumnInfo(name = "event_date")
	@SerializedName("event_date") val event_date : Long,
	@ColumnInfo(name = "views")
	@SerializedName("views") val views : Int,
	@ColumnInfo(name = "likes")
	@SerializedName("likes") val likes : Int,
	@ColumnInfo(name = "shares")
	@SerializedName("shares") val shares : Int
)