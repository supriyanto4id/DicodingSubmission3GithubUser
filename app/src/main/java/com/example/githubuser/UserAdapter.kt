package com.example.githubuser

import android.content.ClipData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.databinding.ItemUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    private var onItemClickCallback: OnItemClickCallback? = null
    private val mData = ArrayList<UserItem>()

    //membuat fungsi untuk di gunakan di main activity
    fun setOnItemCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback =onItemClickCallback
    }

    fun setData(items: ArrayList<UserItem>){
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user,viewGroup,false)
        return UserViewHolder(mView)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        holder.bind(mData[position])



    }





    override fun getItemCount(): Int {
        return mData.size
    }

    inner class UserViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemUserBinding.bind(itemView)
        fun bind(userItem:UserItem){
            with(binding){
                Glide.with(itemView.context)
                        .load(userItem.imgAvatar)
                        .apply(RequestOptions().override(110,110))
                        .into(imgItemPhoto)
                loginName.text = userItem.nameLogin


            }
            //membuat listener agar bisa di click di main activity
            itemView.setOnClickListener{ onItemClickCallback?.onItemClicked(userItem) }

        }

        fun favorite(status : Boolean){
            if (status == true){
                binding.favBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
            }else{
                binding.favBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }
    }


    //interface untuk di gunakan di main activity
    interface OnItemClickCallback {
        fun onItemClicked(data: UserItem)

    }
}





