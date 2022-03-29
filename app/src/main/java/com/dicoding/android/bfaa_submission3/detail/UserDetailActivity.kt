package com.dicoding.android.bfaa_submission3.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.android.bfaa_submission3.data.User
import com.dicoding.android.bfaa_submission3.data.UserDetail
import com.dicoding.android.bfaa_submission3.R
import com.dicoding.android.bfaa_submission3.databinding.ActivityUserDetailBinding
import com.dicoding.android.bfaa_submission3.detail.follow.adapter.FollowPagerAdapter
import com.dicoding.android.bfaa_submission3.util.loadImage
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDetailActivity : AppCompatActivity() {
    private var _binding: ActivityUserDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var extraUser: User
    private var statusFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSectionPager()

        extraUser = intent.getParcelableExtra<User>(EXTRA_USER) as User
        extraUser.login?.let { setupViewModel(it) }
        setStatusFavorite()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.user_detail_menu, menu)

        val item: MenuItem = menu!!.findItem(R.id.favorite_menu)
        if (statusFavorite) {
            item.setIcon(R.drawable.ic_favorite)
        } else {
            item.setIcon(R.drawable.ic_favorite_white)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_menu -> {
                if (statusFavorite) {
                    item.setIcon(R.drawable.ic_favorite_white)
                    statusFavorite = false
                    detailViewModel.removeFavorite(extraUser.id!!)
                    Snackbar.make(
                        binding.root, R.string.remove_favorite,
                        Snackbar.LENGTH_LONG
                    ).show()
                } else {
                    item.setIcon(R.drawable.ic_favorite)
                    statusFavorite = true
                    detailViewModel.addToFavorite(extraUser.login!!, extraUser.id!!, extraUser.avatarUrl!!)
                    Snackbar.make(
                        binding.root,
                        R.string.add_favorite,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun populateDetail(user: UserDetail) {
        with(binding) {
            imgDetailAvatar.loadImage(user.avatarUrl)
            tvDetailName.text = user.name ?: "-"
            tvDetailUsername.text = user.login ?: "-"
            tvDetailFollower.text = getString(R.string.detail_followers, user.followers)
            tvDetailFollowing.text = getString(R.string.detail_following, user.following)
            tvDetailCompany.text = user.company ?: "-"
            tvDetailLocation.text = user.location ?: "-"
            tvDetailRepository.text = getString(R.string.detail_repository, user.publicRepos)
        }
    }

    private fun setupViewModel(username: String) {
        showLoading(true)
        detailViewModel = ViewModelProvider(
            this
        )[DetailViewModel::class.java]

        detailViewModel.setUserDetail(username)

        detailViewModel.userDetail.observe(this) {
            populateDetail(it)
            showLoading(false)
        }
    }

    private fun setupSectionPager() {
        val pagerAdapter = FollowPagerAdapter(this)
        val viewPager = binding.viewPagerDetail
        viewPager.adapter = pagerAdapter
        val tabs = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarDetail.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun setStatusFavorite() {
        CoroutineScope(Dispatchers.IO).launch {
            val count = detailViewModel.checkUser(extraUser.id!!)
            statusFavorite = count!! > 0
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.app_follower,
            R.string.app_following
        )
        const val EXTRA_USER = "extra_user"
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }
}