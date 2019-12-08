package com.wispapp.themovie.ui.moviedetails.fragments

import android.view.View
import androidx.lifecycle.Observer
import com.wispapp.themovie.R
import com.wispapp.themovie.ui.base.BaseFragment
import com.wispapp.themovie.ui.moviedetails.adapter.TrailersPagerAdapter
import com.wispapp.themovie.ui.moviedetails.adapter.TrailersPagerAdapter.STATE.*
import com.wispapp.themovie.ui.moviedetails.interfaces.PlaybackYouTubeListener
import com.wispapp.themovie.ui.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_movie_trailers.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TrailersPagerFragment : BaseFragment(R.layout.fragment_movie_trailers),
    PlaybackYouTubeListener {

    private val moviesViewModel: MoviesViewModel by sharedViewModel()

    private val pagerAdapter by lazy { TrailersPagerAdapter(this, this) }

    override fun initViewModel() {
        moviesViewModel.trailersLiveData.observe(this, Observer {
            pagerAdapter.update(it)
        })
    }

    override fun initView(view: View) {
        initTrailersViewPager()
    }

    override fun dataLoadingObserve() {
        moviesViewModel.isDataLoading.observe(this, Observer { isDataLoaded ->
            if (isDataLoaded)
                showLoading()
            else
                hideLoading()
        })
    }

    override fun exceptionObserve() {
        moviesViewModel.exception.observe(this, Observer { error ->
            if (error != null && error.errorMessage.isNotEmpty())
                showError(error.errorMessage, error.func)
        })
    }

    private fun initTrailersViewPager() {
        trailer_pager.adapter = pagerAdapter

        next_trailer_button.setOnClickListener {
            trailer_pager.currentItem = trailer_pager.currentItem + 1
        }
        previous_trailer_button.setOnClickListener {
            trailer_pager.currentItem = trailer_pager.currentItem - 1
        }
    }

    override fun onStartPlay() {
        next_trailer_button.visibility = View.GONE
        previous_trailer_button.visibility = View.GONE
    }

    override fun onStopPlayback() {
        when (pagerAdapter.state) {
            START_PAGE -> {
                next_trailer_button.visibility = View.VISIBLE
                previous_trailer_button.visibility = View.GONE
            }
            MIDDLE_PAGE -> {
                next_trailer_button.visibility = View.VISIBLE
                previous_trailer_button.visibility = View.VISIBLE
            }
            END_PAGE -> {
                next_trailer_button.visibility = View.GONE
                previous_trailer_button.visibility = View.VISIBLE
            }
            SINGLE_PAGE -> {
                next_trailer_button.visibility = View.GONE
                previous_trailer_button.visibility = View.GONE
            }
        }
    }
}
