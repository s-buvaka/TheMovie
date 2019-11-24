package com.wispapp.themovie.ui.main

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.jaeger.library.StatusBarUtil
import com.wispapp.themovie.R
import com.wispapp.themovie.ui.base.BaseActivity


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StatusBarUtil.setColor(this, Color.TRANSPARENT)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_activity_main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item){
//            findViewById<View>(R.id.action_search) ->{}
//        }
//        return super.onOptionsItemSelected(item)
//    }
}
