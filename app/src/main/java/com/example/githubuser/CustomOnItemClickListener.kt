package com.example.githubuser

import android.view.View

class CustomOnItemClickListener (private val position: Int, private val onItemClickCallback: OnItemClickCallbackCustom) : View.OnClickListener {
    override fun onClick(view: View) {
        onItemClickCallback.onItemClicked(view, position)
    }
    interface OnItemClickCallbackCustom {
        fun onItemClicked(view: View, position: Int)
    }


}