package com.dicoding.android.bfaa_submission3.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.android.bfaa_submission3.data.SearchList
import com.dicoding.android.bfaa_submission3.data.User
import com.dicoding.android.bfaa_submission3.control.RetrofitConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _users = MutableLiveData<ArrayList<User>>()
    val users: LiveData<ArrayList<User>> = _users

    fun searchUsers(query: String) {
        val client = RetrofitConfig.apiInstance.getSearchUsers(query)

        client.enqueue(object : Callback<SearchList> {
            override fun onResponse(
                call: Call<SearchList>,
                response: Response<SearchList>
            ) {
                if (response.isSuccessful) {
                    _users.postValue(response.body()?.items)
                }
            }

            override fun onFailure(call: Call<SearchList>, t: Throwable) {
                Log.e("MainViewModel", "onFailure searchUsers ${t.message}")
            }
        })
    }
}