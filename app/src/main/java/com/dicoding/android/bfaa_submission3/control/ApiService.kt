package com.dicoding.android.bfaa_submission3.control

import com.dicoding.android.bfaa_submission3.data.SearchList
import com.dicoding.android.bfaa_submission3.data.User
import com.dicoding.android.bfaa_submission3.data.UserDetail
import com.dicoding.android.bfaa_submission3.util.NetworkInfo.GITHUB_TOKEN
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: $GITHUB_TOKEN")
    fun getSearchUsers(
        @Query("q") query: String,
    ): Call<SearchList>

    @GET("users/{username}")
    @Headers("Authorization: $GITHUB_TOKEN")
    fun getDetailUsers(
        @Path("username") username: String,
    ): Call<UserDetail>

    @GET("users/{username}/following")
    @Headers("Authorization: $GITHUB_TOKEN")
    fun getFollowingUsers(
        @Path("username") username: String,
    ): Call<ArrayList<User>>

    @GET("users/{username}/followers")
    @Headers("Authorization: $GITHUB_TOKEN")
    fun getFollowersUsers(
        @Path("username") username: String,
    ): Call<ArrayList<User>>
}