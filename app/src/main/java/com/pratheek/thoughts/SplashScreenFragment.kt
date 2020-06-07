package com.pratheek.thoughts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class SplashScreenFragment : BaseFragment() {
    override fun getLayoutResource(): Int {
        return R.layout.fragment_splash_screen
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun init() {
//        Nothing to do
    }

}