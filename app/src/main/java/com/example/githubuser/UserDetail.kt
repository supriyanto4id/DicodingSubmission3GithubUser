package com.example.githubuser

import android.content.ContentValues
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.data.Favorite
import com.example.githubuser.databinding.ActivityFavoriteUserBinding
import com.example.githubuser.databinding.ActivityUserDetailBinding
import com.example.githubuser.db.DatabaseContract.UserColumns.Companion.AVATAR_URL
import com.example.githubuser.db.DatabaseContract.UserColumns.Companion.BLOG_URL
import com.example.githubuser.db.DatabaseContract.UserColumns.Companion.COMPANY
import com.example.githubuser.db.DatabaseContract.UserColumns.Companion.LOCATION
import com.example.githubuser.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.example.githubuser.db.DatabaseContract.UserColumns.Companion._ID
import com.example.githubuser.db.DatabaseHelper
import com.example.githubuser.db.UserHelper
import com.example.githubuser.helper.MappingHelper
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class UserDetail() : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityUserDetailBinding

    private lateinit var mainViewModel: MainViewModel

    private lateinit var userHelper: UserHelper
    private var isFavorite = false
    private  var favorite: Favorite? =null

    private lateinit var userName :String
    private lateinit var imageAvatar: String
    private lateinit var company:String
    private lateinit var linkUrl: String
    private lateinit var location: String

    private lateinit var deleteFavorite :String

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_USER_FAV = "extra_user_fav"
        const val EXTRA_POSITION = "extra_position"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        userHelper = UserHelper.getInstance(applicationContext)
        userHelper.open()

        mainViewModel =ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val checked: Int = R.drawable.ic_baseline_favorite_24
        favorite = intent.getParcelableExtra(EXTRA_USER_FAV)

        if (favorite !=null ){
            setDataFav()
            isFavorite =true
            binding.Fav.setImageResource(checked)
        }else {
            setDataDetail()
        }


        mainViewModel.getDetailDataUser().observe(this,{UserDetailItem->
            if (UserDetailItem!=null){
               // var user =UserDetailItem.toArray()
                   for (data in UserDetailItem){
                       company = data.company.toString()
                       linkUrl= data.linkBlog.toString()
                       location =data.location.toString()
                       binding.tvCompany.text = company
                       binding.tvUrl.text = linkUrl
                       binding.tvLocation.text = location
                   }
            }
            showLoading(false)
        })

        viewPagerTabLayount()

       binding.Fav.setOnClickListener(this)
    }

    //for following and followes tab
    private fun viewPagerTabLayount(){
        val sectionsPagerAdapter = SectionsPagerAdapter(this)

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        sectionsPagerAdapter.username = userName
        viewPager.adapter=sectionsPagerAdapter

        val tableLayout : TabLayout = findViewById(R.id.tabs)

        TabLayoutMediator(tableLayout,viewPager){ tab,posisition ->
            tab.text =resources.getString(TAB_TITLES[posisition])
        }.attach()
        supportActionBar?.elevation  = 0f
    }

    fun setDataDetail(){

        val user = intent.getParcelableExtra<UserItem>("extra_user")
        val fav :Cursor=userHelper.queryByUsername(user?.nameLogin.toString())
        if (fav.moveToNext()){
         isFavorite =true
            binding.Fav.setImageResource(R.drawable.ic_baseline_favorite_24)
            deleteFavorite = user?.nameLogin.toString()
        }

        userName = user?.nameLogin.toString()
        userName?.let {
            showLoading(true)
            mainViewModel.setDetailDataUser(it) }
        imageAvatar= user?.imgAvatar.toString()
        with(binding){
            Glide.with(imgUser)
                    .load(imageAvatar)
                    .apply(RequestOptions().override(110,110))
                    .into(imgUser)
        }

        binding.tvUser.text = userName
    }



    fun setDataFav(){
         favorite= intent.getParcelableExtra(EXTRA_USER_FAV) as Favorite?
            deleteFavorite = favorite?.username.toString()
        userName = favorite?.username.toString()
        userName?.let {
            showLoading(true)
            mainViewModel.setDetailDataUser(it) }
        imageAvatar= favorite?.avatar.toString()
        with(binding){
            Glide.with(imgUser)
                    .load(imageAvatar)
                    .apply(RequestOptions().override(110,110))
                    .into(imgUser)
        }

        binding.tvUser.text = userName
    }

    override fun onClick(v: View) {

        val checked: Int = R.drawable.ic_baseline_favorite_24
        val unChecked: Int = R.drawable.ic_baseline_favorite_border_24
        if (v.id == R.id.Fav){
        if (isFavorite){
           // userHelper.deleteById(favorite?._id.toString())
            userHelper.deleteByUsername(deleteFavorite)
            Log.d("MyDatbase","data ="+deleteFavorite)
            Toast.makeText(this, "delete Favorite ku", Toast.LENGTH_SHORT).show()
            binding.Fav.setImageResource(unChecked)
            isFavorite =false
        } else{

            val values=ContentValues()
            values.put(USERNAME,userName)
            values.put(AVATAR_URL,imageAvatar)
            values.put(COMPANY,company)
            values.put(BLOG_URL,linkUrl)
            values.put(LOCATION, location)

            userHelper.insert(values)
            isFavorite =true
            Toast.makeText(this, "add Favorite", Toast.LENGTH_SHORT).show()
            binding.Fav.setImageResource(checked)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        userHelper.close()
    }

    fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
