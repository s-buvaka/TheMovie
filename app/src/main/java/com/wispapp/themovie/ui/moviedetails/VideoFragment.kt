package com.wispapp.themovie.ui.moviedetails

import android.view.View
import androidx.lifecycle.Observer
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.wispapp.themovie.R
import com.wispapp.themovie.ui.base.BaseFragment
import com.wispapp.themovie.ui.viewmodel.MoviesViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

private const val KEY = "AIzaSyACvygu6rqrc3l2KT7eu8Zygqfr_pT_Qo8"

class VideoFragment : BaseFragment(R.layout.fragment_video) {

    private val moviesViewModel: MoviesViewModel by sharedViewModel()

    override fun initViewModel() {
    }

    override fun initView(view: View) {
        initVideoView()
    }

    override fun dataLoadingObserve() {
    }

    override fun exceptionObserve() {
        moviesViewModel.exception.observe(this, Observer { error ->
            if (error != null && error.errorMessage.isNotEmpty())
                showError(error.errorMessage, error.func)
        })
    }

    private fun initVideoView() {
        @Suppress("CAST_NEVER_SUCCEEDS") val youtube =
            childFragmentManager.findFragmentById(R.id.youtube_player) as YouTubePlayerSupportFragment
        youtube.initialize(KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                player: YouTubePlayer?,
                wasRestored: Boolean
            ) {
                player?.loadVideo("t433PEQGErc")
            }

            override fun onInitializationFailure(
                provider: YouTubePlayer.Provider?,
                result: YouTubeInitializationResult?
            ) {
                //  showError("", null)
            }
        })
    }


}
