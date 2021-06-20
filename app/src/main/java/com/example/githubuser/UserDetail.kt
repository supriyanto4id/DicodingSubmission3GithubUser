package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.databinding.ActivityUserDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class UserDetail() : AppCompatActivity(){

    lateinit var binding: ActivityUserDetailBinding
    private lateinit var mainViewModel: MainViewModel
    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        mainViewModel =ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<UserItem>("extra_user")
        val text = user?.nameLogin
        text?.let {
            showLoading(true)
            mainViewModel.setDetailDataUser(it) }

        with(binding){
            Glide.with(imgUser)
                    .load(user?.imgAvatar)
                    .apply(RequestOptions().override(110,110))
                    .into(imgUser)

        }

        binding.tvUser.text = text


        mainViewModel.getDetailDataUser().observe(this,{UserDetailItem->
            if (UserDetailItem!=null){
               // var user =UserDetailItem.toArray()
                   for (data in UserDetailItem){
                       binding.tvCompany.text = data.company
                       binding.tvUrl.text = data.linkBlog
                       binding.tvLocation.text = data.location
                   }
            }
            showLoading(false)
        })
        //following dan followers tab
        val sectionsPagerAdapter = SectionsPagerAdapter(this)

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        sectionsPagerAdapter.username = text
        viewPager.adapter=sectionsPagerAdapter

        val tableLayout : TabLayout = findViewById(R.id.tabs)

        TabLayoutMediator(tableLayout,viewPager){ tab,posisition ->
            tab.text =resources.getString(TAB_TITLES[posisition])
        }.attach()
        supportActionBar?.elevation  = 0f
    }

    fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}

//interface ShowLoading{
//    var binding: ActivityUserDetailBinding
//    fun showLoading(state: Boolean) {
//        if (state) {
//            binding.progressBar.visibility = View.VISIBLE
//        } else {
//            binding.progressBar.visibility = View.GONE
//        }
//    }
//
//}