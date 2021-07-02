package com.example.githubuser

import android.database.ContentObserver
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.UserFavAdapter
import com.example.githubuser.databinding.ActivityFavoriteUserBinding
import com.example.githubuser.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.example.githubuser.db.UserHelper
import com.example.githubuser.helper.MappingHelper
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var adapter:UserFavAdapter
    private lateinit var binding:ActivityFavoriteUserBinding
    private lateinit var userHelper: UserHelper
    private lateinit var uriWithId:Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title =getString(R.string.favorite_user)


        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.recycleView.layoutManager =LinearLayoutManager(this)
        binding.recycleView.setHasFixedSize(true)
        adapter = UserFavAdapter(this)
        binding.recycleView.adapter=adapter

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver =  object : ContentObserver(handler){
            override fun onChange(selfChange: Boolean) {
                super.onChange(selfChange)
                loadNotesAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true,myObserver)
    }


    private fun loadNotesAsync(){
        GlobalScope.launch (Dispatchers.Main){
            userHelper = UserHelper.getInstance(applicationContext)
            userHelper.open()

            val deferredUser = async(Dispatchers.IO) {
               //val cursor = userHelper.queryyAll()
               val cursor = contentResolver.query(CONTENT_URI,null,null,null,null)
                   MappingHelper.mapCursorToArrayList(cursor)

            }


            val user =deferredUser.await()

            if (user.size >0){
                adapter.listUser =user
            }else{
                adapter.listUser =ArrayList()
                showSnackbarMessage("Tidak ada data saat ini")
            }

        }
    }

    private fun showSnackbarMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        loadNotesAsync()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}