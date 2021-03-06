package com.dicoding.android.bfaa_submission3.detail.follow.adapter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.android.bfaa_submission3.data.User
import com.dicoding.android.bfaa_submission3.control.RetrofitConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {
    private val _follow = MutableLiveData<ArrayList<User>>()
    val follow: LiveData<ArrayList<User>> = _follow

    fun setFollowers(username: String) {
        val client = RetrofitConfig.apiInstance.getFollowersUsers(username)

        client.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                if (response.isSuccessful) {
                    _follow.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.e("FollowViewModel", "onFailure setFollowers ${t.message}")
            }
        })
    }

    fun setFollowing(username: String) {
        val client = RetrofitConfig.apiInstance.getFollowingUsers(username)

        client.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                if (response.isSuccessful) {
                    _follow.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.e("FollowViewModel", "onFailure setFollowers ${t.message}")
            }
        })
    }
}