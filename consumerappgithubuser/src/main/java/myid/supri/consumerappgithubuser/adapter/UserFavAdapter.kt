package myid.supri.consumerappgithubuser.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import myid.supri.consumerappgithubuser.R
import myid.supri.consumerappgithubuser.data.Favorite
import myid.supri.consumerappgithubuser.databinding.ItemUserBinding

class UserFavAdapter(private val activity: Activity) : RecyclerView.Adapter<UserFavAdapter.UserFavViewHolder>() {

     var listUser = ArrayList<Favorite>()
         set(listUser) {
        if(listUser.size >0){
            this.listUser.clear()
        }
        this.listUser.addAll(listUser)
        notifyDataSetChanged()
         }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFavAdapter.UserFavViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user,parent,false)
        return UserFavViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserFavAdapter.UserFavViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

   inner class UserFavViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding = ItemUserBinding.bind(itemView)
                
       fun bind(favorite:Favorite){
           binding.loginName.text = favorite.username
           Glide.with(itemView.context)
                   .load(favorite.avatar)
                   .apply(RequestOptions().override(110,110))
                   .into(binding.imgItemPhoto)
       }

    }
}