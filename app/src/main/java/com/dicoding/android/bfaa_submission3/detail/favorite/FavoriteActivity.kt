package com.dicoding.android.bfaa_submission3.detail.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.android.bfaa_submission3.data.User
import com.dicoding.android.bfaa_submission3.data.local.FavoriteUser
import com.dicoding.android.bfaa_submission3.databinding.ActivityFavoriteBinding
import com.dicoding.android.bfaa_submission3.detail.UserDetailActivity

class FavoriteActivity : AppCompatActivity(){

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FavoriteAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val userDetailIntent = Intent(this@FavoriteActivity, UserDetailActivity::class.java)
                userDetailIntent.putExtra(UserDetailActivity.EXTRA_USER, data)
                startActivity(userDetailIntent)
            }
        })

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        binding.apply {
            rvUserFavorite.setHasFixedSize(true)
            rvUserFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUserFavorite.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(this, {
            if (it!=null){
                val list = mapList(it)
                adapter.setData(list)
            }
        })

        viewModel.getFavoriteUser()?.observe(this) { userItems ->
            if (userItems.count() != 0) {
                showNotFound(false)
            } else {
                showNotFound(true)
            }
        }
    }

    private fun showNotFound(state: Boolean) {
        binding.notFoundFavorite.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users){
            val userMapped = User(
                user.id,
                user.login,
                user.avatarUrl
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }

}