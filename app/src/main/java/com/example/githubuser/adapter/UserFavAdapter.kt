package com.example.githubuser.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.CustomOnItemClickListener
import com.example.githubuser.R
import com.example.githubuser.UserDetail
import com.example.githubuser.data.Favorite
import com.example.githubuser.databinding.ItemFavUserBinding

class UserFavAdapter(private val activity: Activity) : RecyclerView.Adapter<UserFavAdapter.UserFavViewHolder>() {

     var listUser = ArrayList<Favorite>()
         set(listUser) {
        if(listUser.size >0){
            this.listUser.clear()
        }
        this.listUser.addAll(listUser)
        notifyDataSetChanged()
         }

    fun addItem(favorite: Favorite){
        this.listUser.add(Favorite())
        notifyItemInserted(this.listUser.size-1)
    }

    fun updateItem(position: Int, favorite: Favorite){
        this.listUser[position]= favorite
        notifyItemChanged(position,favorite)

    }

    fun removeItem(position: Int){
        this.listUser.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listUser.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFavAdapter.UserFavViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fav_user,parent,false)
        return UserFavViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserFavAdapter.UserFavViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

   inner class UserFavViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding = ItemFavUserBinding.bind(itemView)
                
       fun bind(favorite:Favorite){
           binding.loginName.text = favorite.username
           Glide.with(itemView.context)
                   .load(favorite.avatar)
                   .apply(RequestOptions().override(110,110))
                   .into(binding.imgItemPhoto)
           Toast.makeText(itemView.context,"Hallo"+favorite,Toast.LENGTH_LONG).show()
           itemView.setOnClickListener(
                   CustomOnItemClickListener(
                    adapterPosition,
                        object:CustomOnItemClickListener.OnItemClickCallbackCustom{
                            override fun onItemClicked(view: View, position: Int) {
                                val intent =Intent(activity,UserDetail::class.java)
                                    intent.putExtra(UserDetail.EXTRA_POSITION,position)
                                    intent.putExtra(UserDetail.EXTRA_USER_FAV,favorite)
                               activity.startActivity(intent)
                            }

                        }
                   ))
       }

    }
}