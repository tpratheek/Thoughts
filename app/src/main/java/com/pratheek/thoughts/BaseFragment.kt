package com.pratheek.thoughts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import butterknife.ButterKnife

abstract class BaseFragment : Fragment() {

    abstract fun getLayoutResource(): Int

    abstract fun init()

    lateinit var layoutView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layoutView = inflater.inflate(getLayoutResource(), container, false)
        ButterKnife.bind(this, layoutView)
        return layoutView
    }
}