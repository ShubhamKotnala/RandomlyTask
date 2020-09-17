package com.seven.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seven.myapplication.database.FeedDatabase
import com.seven.myapplication.model.FeedResponse
import com.seven.myapplication.model.Posts
import com.seven.myapplication.network.APIClient
import com.seven.myapplication.network.APIConfig
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val apiClient = APIConfig.instance?.create(APIClient::class.java)
    private val compositeDisposable = CompositeDisposable()
    private var dataBaseInstance: FeedDatabase ?= null

    val data = MutableLiveData<FeedResponse>()
    val offlineDataList = MutableLiveData<List<Posts>>()

    fun setInstanceOfDb(dataBaseInstance: FeedDatabase) {
        this.dataBaseInstance = dataBaseInstance
    }

     fun callFeedApi(page: Int) {
         viewModelScope.launch {
            val response = apiClient!!.getFeeds(page)
             data.postValue(response)
             saveDataIntoDb(response)
         }
    }

    fun saveDataIntoDb(data: FeedResponse){
        dataBaseInstance?.feedDataDao()?.insertFeeds(data)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe ({
            },{ it.message })?.let {
                compositeDisposable.add(it)
            }
    }

    fun getSavedData() {
        dataBaseInstance?.feedDataDao()?.getAllData()!!.map { snapshot: List<FeedResponse>? ->
            val array = arrayListOf<Posts>()
            snapshot?.forEach {
                array.addAll(it.posts)
            }
            array
        }
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                offlineDataList.postValue(it)
            }, {it.message })?.let {
                compositeDisposable.add(it)
            }
    }

    fun sortData(type: String, list: ArrayList<Posts>) : ArrayList<Posts>{
        var sortedList=    when(type){
            "Date" -> list.sortedWith(compareByDescending { it.event_date })
            "Likes" -> list.sortedWith(compareByDescending { it.likes })
            "Views" -> list.sortedWith(compareByDescending { it.views })
            "Shares" -> list.sortedWith(compareByDescending { it.shares })
            else -> list
        }
        return ArrayList(sortedList)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}