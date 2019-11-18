package com.wispapp.themovie.ui.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity : AppCompatActivity(), BaseView {

    override fun showLoading() {
        loading_view?.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loading_view?.visibility = View.GONE
    }

    override fun showError() {
        //TODO Not implemented
    }
}