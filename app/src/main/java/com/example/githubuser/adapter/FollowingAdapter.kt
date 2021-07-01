package com.example.githubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.R
import com.example.githubuser.data.UserItem

import com.example.githubuser.databinding.ItemFollowersFollowingBinding

class FollowingAdapter :RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>(){

    private val mData = ArrayList<UserItem>()
    fun setData(items: ArrayList<UserItem>){
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FollowingViewHolder {
        val mView =LayoutInflater.from(viewGroup.context).inflate(R.layout.item_followers_following,viewGroup,false)
        return FollowingViewHolder(mView)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int {
        return mData.size
    }

  inner  class FollowingViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        private val binding = ItemFollowersFollowingBinding.bind(itemView)
      fun bind(userItem: UserItem){
          with(binding){
              Glide.with(itemView.context)
                      .load(userItem.imgAvatar)
                      .apply(RequestOptions().override(110,110))
                      .into(imgFollowers)
              binding.tvName.text = userItem.nameLogin
          }
      }
    }

}