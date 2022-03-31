package com.dicoding.android.bfaa_submission3.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.android.bfaa_submission3.data.User
import com.dicoding.android.bfaa_submission3.databinding.ItemUserBinding
import com.dicoding.android.bfaa_submission3.util.loadImage

class UserAdapter(private val callback: UserCallback) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private val mData = ArrayList<User>()

    fun setData(users: ArrayList<User>) {
        mData.clear()
        mData.addAll(users)
        notifyDataSetChanged()
    }

    interface UserCallback {
        fun onUserClick(user: User)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val userBinding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(userBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                imgDetailAvatar.loadImage(user.avatarUrl)
                tvName.text = user.login
                tvUrl.text = user.htmlurl
                root.setOnClickListener { callback.onUserClick(user) }
            }
        }
    }
}