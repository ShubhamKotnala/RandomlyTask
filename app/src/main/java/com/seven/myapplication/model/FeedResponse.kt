package com.seven.myapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.seven.myapplication.utils.MyConverters
import com.squareup.moshi.Json

@Entity (tableName = FeedResponse.TABLE_NAME)
data class FeedResponse (
	@ColumnInfo(name = "posts")
	@TypeConverters(MyConverters::class)
	@Json(name ="posts") val posts : List<Posts>,
	@PrimaryKey
	@ColumnInfo(name = "page")
	@Json(name ="page") val page : Int
) {
	companion object {
		const val TABLE_NAME = "feeds"

	}
}