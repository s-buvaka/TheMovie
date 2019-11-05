package com.wispapp.themovie.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.wispapp.themovie.R
import com.wispapp.themovie.core.viewmodel.AppDataViewModel
import com.wispapp.themovie.ui.base.BaseActivity
import com.wispapp.themovie.ui.main.MainActivity
import org.koin.android.ext.android.inject

class SplashActivity : BaseActivity() {

    private val appDataViewModel: AppDataViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initViewModel()
    }

    private fun initViewModel() {
        appDataViewModel.getConfigs()
        appDataViewModel.appDataLoaderLiveData.observe(this, Observer { isDataLoaded ->
            if (isDataLoaded) startMainScreen()
        })
    }

    private fun startMainScreen() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
