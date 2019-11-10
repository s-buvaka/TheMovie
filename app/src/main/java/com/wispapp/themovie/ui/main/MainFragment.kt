package com.wispapp.themovie.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.wispapp.themovie.R
import com.wispapp.themovie.core.database.model.MovieOverviewDao
import com.wispapp.themovie.core.viewmodel.MoviesViewModel
import com.wispapp.themovie.ui.base.BaseFragment
import com.wispapp.themovie.ui.base.recycler.GenericAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment(R.layout.fragment_main),
    GenericAdapter.OnItemClickListener<MovieOverviewDao> {

    private val moviesViewModel: MoviesViewModel by viewModel()
    private val adapter: GenericAdapter<MovieOverviewDao> by lazy { popularsMovieAdapter }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
    }

    override fun initView() {
        initRecycler()
    }

    override fun onClickItem(data: MovieOverviewDao) {
        Snackbar.make(popular_movie_recycler, "More", Snackbar.LENGTH_SHORT).show()
    }

    private fun initViewModel() {
        moviesViewModel.getPopularMovie()
        moviesViewModel.popularMovieLiveData.observe(this, Observer {
            adapter.update(it)
        })
    }

    private fun initRecycler() {
        popular_movie_recycler.adapter = adapter
    }

    private val popularsMovieAdapter = object : GenericAdapter<MovieOverviewDao>(this) {
        override fun getItemId(position: Int): Long = getItem(position).item.id.toLong()

        override fun getLayoutId(position: Int, obj: MovieOverviewDao): Int =
            R.layout.item_movie_overview
    }
}
