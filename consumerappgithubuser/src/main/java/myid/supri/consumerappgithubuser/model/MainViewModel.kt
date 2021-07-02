package myid.supri.consumerappgithubuser.model

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import myid.supri.consumerappgithubuser.data.UserDetailItem
import myid.supri.consumerappgithubuser.data.UserItem
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class MainViewModel : ViewModel() {

    var listUser = MutableLiveData<ArrayList<UserItem>>()

    var detailUser = MutableLiveData<ArrayList<UserDetailItem>>()

    var listFollowers = MutableLiveData<ArrayList<UserItem>>()

    var listFollowing = MutableLiveData<ArrayList<UserItem>>()

    fun setDataUser(user:String,context: Context){
        val listItemUser = ArrayList<UserItem>()
        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token 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")
        asyncClient.addHeader("User-Agent", "request")
        val url = "https://api.github.com/search/users?q=${user}"

        asyncClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                val result = responseBody?.let { String(it) }

                try {
                    val responObject = JSONObject(result)
                    val dataArray = responObject.getJSONArray("items")
                    for (i in 0 until dataArray.length()) {

                        val userItems = UserItem()
                        val dataObject = dataArray.getJSONObject(i)
                        userItems.nameLogin = dataObject.getString("login")
                        userItems.imgAvatar = dataObject.getString("avatar_url")

                        listItemUser.add(userItems)
                    }

                    listUser.postValue(listItemUser)
                   // showLoading(false)
                } catch (e: Exception) {
                    Toast.makeText(context, "Exception =" + e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                   // showLoading(false)
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {

                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
               Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
               // showLoading(false)
            }

        })
    }

    fun getDataUser() :LiveData<ArrayList<UserItem>>{
        return listUser
    }

    fun setDetailDataUser(user:String, context: Context){
        val detailUserAdd = ArrayList<UserDetailItem>()
        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization","token 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")
        asyncClient.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/${user}"

        asyncClient.get(url,object  : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                val result = responseBody?.let { String(it) }
                try {
                    val userDetail = UserDetailItem()
                    val responObject = JSONObject(result)

                    userDetail.nameLogin = responObject.getString("login")
                    userDetail.imgAvatar = responObject.getString("avatar_url")
                    userDetail.company =responObject.getString("company")
                    userDetail.linkBlog =responObject.getString("blog")
                    userDetail.location = responObject.getString("location")

                    detailUserAdd.add(userDetail)

                    detailUser.postValue(detailUserAdd)

                }catch (e: Exception){
                   Toast.makeText(context, "Exception ="+e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                  //  showLoading(false)
                }
               // showLoading(false)
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {

                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
               Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
              //  showLoading(false)
            }

        })
    }

    fun getDetailDataUser():LiveData<ArrayList<UserDetailItem>>{
    return detailUser
    }

    fun setListFollowers(user: String){
        val listItemFollowers = java.util.ArrayList<UserItem>()
        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization","token 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")
        asyncClient.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/${user}/followers"

        asyncClient.get(url,object : AsyncHttpResponseHandler(){


            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                val result = responseBody?.let { String(it) }


                try {
                    val jsonArray = JSONArray(result)

                    for (i in 0 until jsonArray.length()){
                        val followersItem = UserItem()
                        val jsonObject = jsonArray.getJSONObject(i)

                        followersItem.nameLogin = jsonObject.getString("login")
                        followersItem.imgAvatar =jsonObject.getString("avatar_url")
                        listItemFollowers.add(followersItem)

                    }

                    listFollowers.postValue(listItemFollowers)
                    //adapter.setData(listItemFollowers)

                }catch (e : Exception){
                   //Toast.makeText(activity, "Exception =" + e.message, Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
               //Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getListFollowers():LiveData<ArrayList<UserItem>>{
        return listFollowers
    }


    fun setListFollowing(user: String){
        val listItemFollowers = java.util.ArrayList<UserItem>()
        val asyncClient = AsyncHttpClient()

        asyncClient.addHeader("Authorization","token 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")
        asyncClient.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/${user}/following"
        asyncClient.get(url,object :AsyncHttpResponseHandler(){

            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                val result = responseBody?.let { String(it) }
                try {
                    val jsonArray = JSONArray(result)

                    for (i in 0 until jsonArray.length()){
                        val followersItem = UserItem()
                        val jsonObject = jsonArray.getJSONObject(i)

                        followersItem.nameLogin = jsonObject.getString("login")
                        followersItem.imgAvatar =jsonObject.getString("avatar_url")
                        listItemFollowers.add(followersItem)

                    }

                    //adapter.setData(listItemFollowers)
                    listFollowing.postValue(listItemFollowers)
                }catch (e : Exception){
                   //Toast.makeText(context, "Exception =" + e.message, Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
              // Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()

            }

        })
    }

    fun getListFollowing():LiveData<ArrayList<UserItem>>{
        return listFollowing
    }

}