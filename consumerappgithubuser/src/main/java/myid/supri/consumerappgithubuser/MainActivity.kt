package myid.supri.consumerappgithubuser


import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread



import android.widget.*

import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

import myid.supri.consumerappgithubuser.adapter.UserFavAdapter
import myid.supri.consumerappgithubuser.databinding.ActivityMainBinding
import myid.supri.consumerappgithubuser.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import myid.supri.consumerappgithubuser.helper.MappingHelper

import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var adapter: UserFavAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycleView.layoutManager = LinearLayoutManager(this)
        binding.recycleView.setHasFixedSize(true)
        adapter = UserFavAdapter(this)
        binding.recycleView.adapter = adapter


        val handleTherad = HandlerThread("DataObserve")
        handleTherad.start()
        val handler = Handler(handleTherad.looper)
        val myObserve = object:ContentObserver(handler){
            override fun onChange(selfChange: Boolean) {
                super.onChange(selfChange)
                loadUserAsync()

            }
        }


        contentResolver.registerContentObserver(CONTENT_URI, true, myObserve)

        loadUserAsync()
    }

    private fun loadUserAsync(){
        GlobalScope.launch (Dispatchers.Main){

            val deferredUser = async(Dispatchers.IO) {

                val cursor = contentResolver?.query(CONTENT_URI,null,null,null,null)
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



}