package com.example.youtubetest

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MyAdapter(private val mContext: Context, private val mVideoList: List<SearchItem>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview, parent, false)

        return MyViewHolder(itemView)

    }

    override fun getItemCount() = this.mVideoList.count()

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val singleVideo = mVideoList[position]

        holder.videoId.text = "Video ID : " + singleVideo.id + " "
        holder.videoTitle.text = singleVideo.title
        holder.videoDescription.text = singleVideo.description

        Picasso.get()
            .load(singleVideo.thumbnailURL)
            .resize(480, 270)
            .centerCrop()
            .into(holder.videoThumbnail)

        holder.videoView.setOnClickListener {
            val intent = Intent(mContext, PlayerActivity::class.java)

            intent.putExtra("VIDEO_ID", singleVideo.id)
            intent.putExtra("VIDEO_TITLE", singleVideo.title)
            intent.putExtra("VIDEO_DESC", singleVideo.description)

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            mContext.startActivity(intent)
        }
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var videoThumbnail = view.findViewById(R.id.video_thumbnail) as ImageView
        var videoTitle = view.findViewById(R.id.video_title) as TextView
        var videoId= view.findViewById(R.id.video_id) as TextView
        var videoDescription = view.findViewById(R.id.video_description) as TextView
        var videoView = view.findViewById(R.id.video_view) as ConstraintLayout

    }
}