package com.example.myapplication.db.dao

import androidx.room.*
import com.seven.myapplication.model.FeedResponse
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface FeedsDataDao {

    @Insert
    fun insertFeeds(feed:FeedResponse) : Completable

    @Query("SELECT * FROM ${FeedResponse.TABLE_NAME}")
    fun getAllRecords(): Single<FeedResponse>

    @Query("SELECT * FROM ${FeedResponse.TABLE_NAME}")
    fun getAllData() : Observable<List<FeedResponse>>

    @Delete
    fun deleteFeeds(feed:FeedResponse) : Completable

    @Update
    fun updateFeeds(feed:FeedResponse)  : Completable
}