package myid.supri.consumerappgithubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import myid.supri.consumerappgithubuser.UserDetail.Companion.EXTRA_USER
import myid.supri.consumerappgithubuser.adapter.UserAdapter
import myid.supri.consumerappgithubuser.data.UserItem
import myid.supri.consumerappgithubuser.databinding.ActivityMainBinding
import myid.supri.consumerappgithubuser.databinding.ItemUserBinding
import myid.supri.consumerappgithubuser.model.MainViewModel


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var adapter: UserAdapter
    private lateinit var binding: ActivityMainBinding

    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title="Consume Github User"

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
                intentData.putExtra(EXTRA_USER, data)
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

        when(item.itemId){
            R.id.action_change_settings->{
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
            }
            R.id.favorite->{
                val intent =Intent(this@MainActivity,FavoriteUserActivity::class.java)
                startActivity(intent)
            }
            R.id.setting_daily_notification->{
            val intent = Intent(this,NotificationActivity::class.java)
                startActivity(intent)
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
        mainViewModel.setDataUser(query.toString(),this)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }


}