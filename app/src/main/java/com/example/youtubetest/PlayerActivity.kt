package com.example.youtubetest

import android.annotation.SuppressLint
import android.os.Bundle
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : YouTubeBaseActivity() {
    lateinit var youtubePlayerInit: YouTubePlayer.OnInitializedListener

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        play_title.text = intent.getStringExtra("VIDEO_TITLE")
        play_id.text = "Video ID : " + intent.getStringExtra("VIDEO_ID")
        play_desc.text = intent.getStringExtra("VIDEO_DESC")
        InitUI()
    }

    fun InitUI() {
        youtubePlayerInit = object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                youtubePlay: YouTubePlayer,
                restored: Boolean
            ) {
                youtubePlay.loadVideo(intent.getStringExtra("VIDEO_ID"))
            }

            override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {

            }
        }
        player_view.initialize(YoutubeConnector.KEY, youtubePlayerInit)
    }
}
