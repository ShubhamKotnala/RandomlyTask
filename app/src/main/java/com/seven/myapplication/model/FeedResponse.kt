package com.seven.myapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.seven.myapplication.utils.MyConverters

@Entity (tableName = FeedResponse.TABLE_NAME)
data class FeedResponse (
	@ColumnInfo(name = "posts")
	@TypeConverters(MyConverters::class)
	@SerializedName("posts") val posts : List<Posts>,
	@PrimaryKey
	@ColumnInfo(name = "page")
	@SerializedName("page") val page : Int
) {
	companion object {
		const val TABLE_NAME = "feeds"

	}
}