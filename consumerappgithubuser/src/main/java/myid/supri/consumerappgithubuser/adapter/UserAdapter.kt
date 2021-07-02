package myid.supri.consumerappgithubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import myid.supri.consumerappgithubuser.R
import myid.supri.consumerappgithubuser.data.UserItem
import myid.supri.consumerappgithubuser.databinding.ItemUserBinding

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

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user,viewGroup,false)
        return UserViewHolder(mView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(mData[position])



    }





    override fun getItemCount(): Int {
        return mData.size
    }

    inner class UserViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemUserBinding.bind(itemView)
        fun bind(userItem: UserItem){
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

    }


    //interface untuk di gunakan di main activity
    interface OnItemClickCallback {
        fun onItemClicked(data: UserItem)

    }
}





