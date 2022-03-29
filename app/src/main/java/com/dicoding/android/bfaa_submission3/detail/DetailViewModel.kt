package com.dicoding.android.bfaa_submission3.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.android.bfaa_submission3.data.UserDetail
import com.dicoding.android.bfaa_submission3.control.RetrofitConfig
import com.dicoding.android.bfaa_submission3.data.local.FavoriteUser
import com.dicoding.android.bfaa_submission3.data.local.FavoriteUserDao
import com.dicoding.android.bfaa_submission3.data.local.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val _userDetail = MutableLiveData<UserDetail>()
    val userDetail: LiveData<UserDetail> = _userDetail
    private var userDao: FavoriteUserDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun setUserDetail(username: String) {
        val client = RetrofitConfig.apiInstance.getDetailUsers(username)

        client.enqueue(object : Callback<UserDetail> {
            override fun onResponse(
                call: Call<UserDetail>,
                response: Response<UserDetail>
            ) {
                if(response.isSuccessful){
                    _userDetail.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                Log.e("DetailViewModel", "onFailure setUserDetail ${t.message}")
            }
        })
    }

    fun addToFavorite(username: String, id: Int, avatarUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                username,
                id,
                avatarUrl
            )
            userDao?.addToFavorite(user)
        }
    }

    fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }
}