package com.wispapp.themovie.ui.moviedetails.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.wispapp.themovie.R
import com.wispapp.themovie.core.model.database.models.TrailerModel
import com.wispapp.themovie.ui.base.BaseFragment
import com.wispapp.themovie.ui.moviedetails.interfaces.PlaybackYouTubeListener
import com.wispapp.themovie.ui.viewmodel.MoviesViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

private const val POS = "timer_position"

class TrailerFragment(private val playbackListener: PlaybackYouTubeListener? = null) :
    BaseFragment(R.layout.fragment_trailer) {

    private val moviesViewModel: MoviesViewModel by sharedViewModel()
    private var youtubePlayer: YouTubePlayer? = null
    private var videoTimer = 0

    override fun onResume() {
        super.onResume()
        getTrailerData()
    }

    override fun onPause() {
        super.onPause()
       // youtubePlayer?.release()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(POS, youtubePlayer?.currentTimeMillis ?: 0)
    }

    override fun initViewModel() {
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        videoTimer = savedInstanceState?.getInt(POS)?:0
    }

    override fun dataLoadingObserve() {
    }

    override fun exceptionObserve() {
        moviesViewModel.exception.observe(this, Observer { error ->
            if (error != null && error.errorMessage.isNotEmpty())
                showError(error.errorMessage, error.func)
        })
    }

    private fun getTrailerData() {
        val trailer = moviesViewModel.trailersLiveData.value?.find { it.id == getMovieId() }
        trailer?.let {
            startTrailer(it)
        } ?: run { showError(getString(R.string.video_upload_error), null) }
    }

    private fun startTrailer(trailer: TrailerModel) {
        @Suppress("CAST_NEVER_SUCCEEDS")
        val youtube = childFragmentManager.findFragmentById(R.id.youtube_player) as YouTubePlayerSupportFragment
        youtube.initialize(KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                player: YouTubePlayer?,
                wasRestored: Boolean
            ) {

                youtubePlayer = player
                player?.loadVideo(trailer.key)
                player?.seekToMillis(videoTimer)

                if (requireActivity().requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE)
                    youtubePlayer?.setFullscreen(true)

                player?.setOnFullscreenListener { isFullscreen ->
                    if (!isFullscreen)
                        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                }

                player?.setPlaybackEventListener(object : YouTubePlayer.PlaybackEventListener {
                    override fun onSeekTo(p0: Int) {}

                    override fun onBuffering(p0: Boolean) {
                        playbackListener?.onStopPlayback()
                    }

                    override fun onPlaying() {
                        playbackListener?.onStartPlay()
                    }

                    override fun onStopped() {
                        playbackListener?.onStopPlayback()
                    }

                    override fun onPaused() {
                        playbackListener?.onStopPlayback()
                    }
                })
            }

            override fun onInitializationFailure(
                provider: YouTubePlayer.Provider?,
                result: YouTubeInitializationResult?
            ) {
                showError(getString(R.string.video_upload_error), null)
            }
        })
    }

    private fun getMovieId() =
        arguments?.getString(MOVIE_ID) ?: run {
            showError(getString(R.string.video_upload_error), null)
            ""
        }

    companion object {

        private const val KEY = "AIzaSyACvygu6rqrc3l2KT7eu8Zygqfr_pT_Qo8"
        private const val MOVIE_ID = "movie_id"

        fun newInstance(
            listener: PlaybackYouTubeListener,
            trailerId: String
        ): TrailerFragment {
            val fragment = TrailerFragment(listener)
            val bundle = Bundle()
            bundle.putString(MOVIE_ID, trailerId)
            fragment.arguments = bundle
            return fragment
        }
    }
}
