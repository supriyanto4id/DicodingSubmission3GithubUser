package com.example.githubuser

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.databinding.ItemUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener, View.OnClickListener {

    private lateinit var adapter: UserAdapter
    private lateinit var binding: ActivityMainBinding
   private lateinit var bindingItemUser: ItemUserBinding

    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.recycleView.layoutManager = LinearLayoutManager(this)
        binding.recycleView.adapter = adapter

        binding.search.setOnQueryTextListener(this)


        mainViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        mainViewModel.getDataUser().observe(this,{UserItem->
            if (UserItem!=null){
                adapter.setData(UserItem)

            }
            showLoading(false)
        })
        //list item click to detail
        adapter.setOnItemCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItem) {
                val intentData = Intent(this@MainActivity, UserDetail::class.java)
                intentData.putExtra("extra_user", data)
                startActivity(intentData)
            }

        })





    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        inflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

//        if(item.itemId ==R.id.action_change_settings){
//            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
//            startActivity(mIntent)
//        }

        when(item.itemId){
            R.id.action_change_settings->{
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
            R.id.favorite->{
                val intent =Intent(this@MainActivity,FavoriteUserActivity::class.java)
                startActivity(intent)
            }
            R.id.Setting->{

            }
            }

        return super.onOptionsItemSelected(item)
    }


   private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        showLoading(true)
        mainViewModel.setDataUser(query.toString())
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}