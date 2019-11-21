package com.wispapp.themovie.ui.main

import android.graphics.Color
import android.os.Bundle
import com.jaeger.library.StatusBarUtil
import com.wispapp.themovie.R
import com.wispapp.themovie.ui.base.BaseActivity
import kotlinx.android.synthetic.main.view_error_layout.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StatusBarUtil.setColor(this, Color.TRANSPARENT)

        repeat_button.setOnClickListener { hideError() }
    }
}
