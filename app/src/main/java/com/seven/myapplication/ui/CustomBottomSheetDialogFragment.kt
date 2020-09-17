package com.seven.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.seven.myapplication.R
import com.seven.myapplication.model.Posts
import com.seven.myapplication.utils.Utils

class CustomBottomSheetDialogFragment(var post: Posts) : BottomSheetDialogFragment() {

    private lateinit var tvTitle: TextView
    private lateinit var tvLikes: TextView
    private lateinit var tvViews: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvShares: TextView
    private lateinit var imgThumb: ImageView

    companion object {
        const val TAG = "com.seven.myapplication.ui.CustomBottomSheetDialogFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)

        initializeWidgets(root)

        return root
    }

    private fun initializeWidgets(view : View) {
        tvTitle= view.findViewById(R.id.tv_feed_name)
        tvLikes= view.findViewById(R.id.tv_likes)
        tvViews= view.findViewById(R.id.tv_views)
        tvDate   = view.findViewById(R.id.tv_date)
        tvShares = view.findViewById(R.id.tv_share)
        imgThumb = view.findViewById(R.id.image_thumb)

        tvTitle.text = post.event_name.capitalize()
        tvLikes.text = post.likes.toString() + " " + getString(R.string.likes)
        tvShares.text = post.shares.toString() + " " + getString(R.string.shares)
        tvViews.text = post.views.toString() + " " + getString(R.string.views)
        tvDate.text = Utils.getConvertedDate(post.event_date)

        Glide.with(context!!.applicationContext)
            .load(post.thumbnail_image.replace("http", "https"))
            .into(imgThumb)
    }
}