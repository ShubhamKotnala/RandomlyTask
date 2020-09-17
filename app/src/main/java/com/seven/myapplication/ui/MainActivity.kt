package com.seven.myapplication.ui

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.seven.myapplication.R
import com.seven.myapplication.adapter.PostsAdapter
import com.seven.myapplication.database.FeedDatabase
import com.seven.myapplication.model.FeedResponse
import com.seven.myapplication.model.Posts
import com.seven.myapplication.utils.Utils
import com.seven.myapplication.viewmodel.MainViewModel
import com.yarolegovich.lovelydialog.LovelyChoiceDialog


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var progressbar: ProgressBar
    private lateinit var tvTitle : TextView
    private lateinit var tvNodata : TextView
    private lateinit var imgSort : ImageView
    private lateinit var rvCoupons: RecyclerView
    private lateinit var postsAdapter: PostsAdapter
    private var listPosts: ArrayList<Posts> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM;
        supportActionBar?.setCustomView(R.layout.activity_main);
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        var dataBaseInstance = FeedDatabase.getDatabaseInstance(this)
        viewModel?.setInstanceOfDb(dataBaseInstance)

        progressbar = findViewById(R.id.progress_circular)
        tvTitle = findViewById(R.id.tv_title)
        tvNodata = findViewById(R.id.tv_no_data)
        imgSort = findViewById(R.id.img_sort)
        rvCoupons = findViewById(R.id.rc_cupons)

        tvTitle.text = getString(R.string.title_main)

        viewModel.data.observe(this, Observer<FeedResponse> { response ->
            setProgressBarVisiblity(false)
            if (Utils.isOnline(this))
            if (response == null)
                Utils.showSnackBar(findViewById(R.id.main_activity_container), getString(R.string.something_went_wrong))
             else
                setCouponsAdapter(response.posts)
            else
                viewModel.getSavedData()
        })

        viewModel.offlineDataList.observe(this, Observer<List<Posts>> { response ->
            setProgressBarVisiblity(false)
                if (response == null) {
                    Utils.showSnackBar(findViewById(R.id.main_activity_container),getString(R.string.no_data_found))
                    tvNodata.visibility = VISIBLE
                } else {
                    setCouponsAdapter(response)
                }
        })

        imgSort.setOnClickListener { showSortDialog() }
    }

    private fun callFeedApi() {
        setProgressBarVisiblity(true)
        tvNodata.visibility = GONE

        if (Utils.isOnline(this)) {
            Utils.showSnackBar(findViewById(R.id.main_activity_container), getString(R.string.connected))
            viewModel.callFeedApi(1)
        } else {
            Utils.showSnackBar(findViewById(R.id.main_activity_container), getString(R.string.you_are_offline))
            viewModel.getSavedData()
        }
    }

    override fun onResume() {
        super.onResume()
        callFeedApi();
    }

    private fun showSortDialog() {
        val list = listOf<String>("Date", "Likes", "Views", "Shares")

        LovelyChoiceDialog(this)
            .setTopColorRes(R.color.colorPrimary)
            .setTitle("Please select an option to sort")
            .setIcon(R.drawable.ic_sort)
            .setItems(list
            ) { _, item ->
                if(listPosts.isEmpty()) return@setItems
                listPosts = viewModel.sortData(item, listPosts)
                postsAdapter.setData(listPosts)
            }
            .show()
    }

    private fun setCouponsAdapter(list: List<Posts>) {
        listPosts.clear()
        listPosts.addAll(list)

        postsAdapter = PostsAdapter(listPosts)

        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvCoupons.layoutManager = manager
        rvCoupons.adapter = postsAdapter
    }

    private fun setProgressBarVisiblity(boolean: Boolean) {
        if (boolean) progressbar.visibility = VISIBLE else progressbar.visibility = GONE
    }
}
