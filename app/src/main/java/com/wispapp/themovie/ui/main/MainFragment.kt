package com.wispapp.themovie.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.wispapp.themovie.R
import com.wispapp.themovie.core.model.database.models.MovieOverviewModel
import com.wispapp.themovie.core.viewmodel.MoviesViewModel
import com.wispapp.themovie.ui.base.BaseFragment
import com.wispapp.themovie.ui.base.recycler.GenericAdapter
import com.wispapp.themovie.ui.moviedetails.MOVIE_ID
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment(R.layout.fragment_main),
    GenericAdapter.OnItemClickListener<MovieOverviewModel> {

    private val moviesViewModel: MoviesViewModel by viewModel()
    private val adapter: GenericAdapter<MovieOverviewModel> by lazy { popularsMovieAdapter }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
    }

    override fun initView() {
        initRecycler()
    }

    override fun onClickItem(data: MovieOverviewModel) {
        val bundle = Bundle()
        bundle.putInt(MOVIE_ID, data.id)
        findNavController().navigate(R.id.movieDetailsFragment, bundle)
    }

    private fun initViewModel() {
        moviesViewModel.getPopularMovie()
        moviesViewModel.popularMovieLiveData.observe(this, Observer {
            adapter.update(it)
        })

        moviesViewModel.isDataLoading.observe(
            this,
            Observer { isDataLoaded ->
                if (isDataLoaded) showLoading()
                else hideLoading()
            })
    }

    private fun initRecycler() {
        popular_movie_recycler.adapter = adapter
    }

    private val popularsMovieAdapter = object : GenericAdapter<MovieOverviewModel>(this) {
        override fun getItemId(position: Int): Long = getItem(position).item.id.toLong()

        override fun getLayoutId(position: Int, obj: MovieOverviewModel): Int =
            R.layout.item_movie_overview
    }
}
