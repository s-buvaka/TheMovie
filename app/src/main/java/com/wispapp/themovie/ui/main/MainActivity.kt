package com.wispapp.themovie.ui.main

import android.graphics.Color
import android.os.Bundle
import com.jaeger.library.StatusBarUtil
import com.wispapp.themovie.R
import com.wispapp.themovie.ui.base.BaseActivity
import com.wispapp.themovie.ui.viewmodel.ConfigsViewModel
import com.wispapp.themovie.ui.viewmodel.MoviesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity() {

    private val moviesViewModel: MoviesViewModel by viewModel()
    private val configViewModel: ConfigsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StatusBarUtil.setColor(this, Color.TRANSPARENT)

        loadData()
    }

    private fun loadData() {
        moviesViewModel.getMovies()
        configViewModel.loadConfigs()
    }
}
