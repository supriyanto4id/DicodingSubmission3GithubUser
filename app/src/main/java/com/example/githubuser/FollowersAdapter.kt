package com.example.githubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.databinding.ItemFollowersFollowingBinding

class FollowersAdapter : RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {


    private val mData = ArrayList<FollowersFollowingItem>()

    fun setData(items: ArrayList<FollowersFollowingItem>){
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FollowersAdapter.FollowersViewHolder {
       val mView =LayoutInflater.from(viewGroup.context).inflate(R.layout.item_followers_following,viewGroup,false)
        return FollowersViewHolder(mView)
    }

    override fun onBindViewHolder(holder: FollowersAdapter.FollowersViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class FollowersViewHolder (itemView:View) :RecyclerView.ViewHolder(itemView){
        private val binding = ItemFollowersFollowingBinding.bind(itemView)

        fun bind(userItem: FollowersFollowingItem){
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