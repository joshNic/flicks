package com.my.flicks.YoutubeTrailer

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.my.flicks.Constants.YOU_TUBE_API_KEY
import com.my.flicks.R
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment

class YoutubeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.you_tube_api, container, false)
        val resultProperty = YoutubeFragmentArgs.fromBundle(arguments!!).selectedProperty

        val youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance()

        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.youtube_layout, youTubePlayerFragment as Fragment).commit()


        youTubePlayerFragment.initialize(YOU_TUBE_API_KEY, object : YouTubePlayer.OnInitializedListener {


            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider,
                player: YouTubePlayer,
                wasRestored: Boolean
            ) {
                if (!wasRestored) {
                    player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
                    player.loadVideo(resultProperty.key)
                    player.play()
                }
            }


            override fun onInitializationFailure(provider: YouTubePlayer.Provider, error: YouTubeInitializationResult) {
                // YouTube error
                val errorMessage = error.toString()
                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
            }
        })

        return rootView
    }
}
