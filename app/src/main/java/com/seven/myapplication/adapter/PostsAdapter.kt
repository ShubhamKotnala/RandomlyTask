package com.seven.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seven.myapplication.R
import com.seven.myapplication.model.Posts
import com.seven.myapplication.ui.CustomBottomSheetDialogFragment
import com.seven.myapplication.utils.Utils.getDate.getConvertedDate

class PostsAdapter(val posts: ArrayList<Posts>) : RecyclerView.Adapter<FeedViewHolder>() {

    lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_feeds, parent, false)

        context = parent.context

        return FeedViewHolder(view, context)
    }

    fun setData(list: List<Posts>) {
        posts.clear()
        posts.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        return holder.bind(posts[position])
    }
}

class FeedViewHolder(itemView: View, var context: Context) : RecyclerView.ViewHolder(itemView) {

    private val tvTitle: TextView = itemView.findViewById(R.id.tv_feed_name)
    private val tvLikes: TextView = itemView.findViewById(R.id.tv_likes)
    private val tvViews: TextView = itemView.findViewById(R.id.tv_views)
    private val tvDate: TextView = itemView.findViewById(R.id.tv_date)
    private val tvShares: TextView = itemView.findViewById(R.id.tv_share)
    private val imgThumb: ImageView = itemView.findViewById(R.id.image_thumb)
    private val cardView: CardView = itemView.findViewById(R.id.card_container)

    fun bind(post: Posts) {
        tvTitle.text = post.event_name.capitalize()
        tvLikes.text = post.likes.toString()
        tvShares.text = post.shares.toString()
        tvViews.text = post.views.toString()
        tvDate.text = getConvertedDate(post.event_date)

        Glide.with(context)
            .load(post.thumbnail_image.replace("http", "https"))
            .into(imgThumb)

        val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager

        cardView.setOnClickListener {
            CustomBottomSheetDialogFragment(post).run {
                show(manager, CustomBottomSheetDialogFragment.TAG)
            }
        }
    }
}