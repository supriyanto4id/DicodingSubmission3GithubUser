package myid.supri.consumerappgithubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import myid.supri.consumerappgithubuser.adapter.UserFavAdapter
import myid.supri.consumerappgithubuser.databinding.ActivityFavoriteUserBinding
import myid.supri.consumerappgithubuser.db.UserHelper
import myid.supri.consumerappgithubuser.helper.MappingHelper
import kotlinx.coroutines.*

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var adapter:UserFavAdapter
    private lateinit var binding:ActivityFavoriteUserBinding
    private lateinit var userHelper: UserHelper

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

        loadNotesAsync()
    }


    private fun loadNotesAsync(){
        GlobalScope.launch (Dispatchers.Main){
            userHelper = UserHelper.getInstance(applicationContext)
            userHelper.open()
            val deferredUser = async(Dispatchers.IO) {
                val cursor = userHelper.queryyAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }


            val user =deferredUser.await()

            if (user.size>0){
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