package com.wispapp.themovie.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.wispapp.themovie.R
import com.wispapp.themovie.core.model.database.models.MovieModel
import com.wispapp.themovie.core.viewmodel.MoviesViewModel
import com.wispapp.themovie.ui.base.BaseFragment
import com.wispapp.themovie.ui.moviedetails.MOVIE_ID
import com.wispapp.themovie.ui.recycler.GenericAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment(R.layout.fragment_main),
    GenericAdapter.OnItemClickListener<MovieModel> {

    private val moviesViewModel: MoviesViewModel by viewModel()

    private val nowPlayingMoviesAdapter by lazy { getMovieAdapter() }
    private val popularMoviesAdapter by lazy { getMovieAdapter() }
    private val topRatedMoviesAdapter by lazy { getMovieAdapter() }
    private val upcomingMoviesAdapter by lazy { getMovieAdapter() }

    override fun initViewModel() {
        popularMoviesObserve()
    }

    override fun initView() {
        initRecycler()
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
        moviesViewModel.exception.observe(this, Observer { errorMessage ->
            if (errorMessage != null && errorMessage.isNotEmpty())
                showError(errorMessage)
        })
    }

    override fun onClickItem(data: MovieModel) {
        val bundle = Bundle()
        bundle.putInt(MOVIE_ID, data.id)
        findNavController().navigate(R.id.movieDetailsFragment, bundle)
    }

    private fun popularMoviesObserve() {
        moviesViewModel.getMovies()

        moviesViewModel.nowPlayingMoviesLiveData.observe(this, Observer {
            nowPlayingMoviesAdapter.update(it)
        })
        moviesViewModel.popularMoviesLiveData.observe(this, Observer {
            popularMoviesAdapter.update(it)
        })
        moviesViewModel.topRatedMovieLiveData.observe(this, Observer {
            topRatedMoviesAdapter.update(it)
        })
        moviesViewModel.upcomingMoviesLiveData.observe(this, Observer {
            upcomingMoviesAdapter.update(it)
        })
    }

    private fun initRecycler() {
        now_playing_recycler.adapter = nowPlayingMoviesAdapter
        popular_recycler.adapter = popularMoviesAdapter
        top_rated_recycler.adapter = topRatedMoviesAdapter
        upcoming_recycler.adapter = upcomingMoviesAdapter
    }

    private fun getMovieAdapter() = object : GenericAdapter<MovieModel>(this) {
        override fun getLayoutId(position: Int, obj: MovieModel): Int = R.layout.item_movie
    }
}
