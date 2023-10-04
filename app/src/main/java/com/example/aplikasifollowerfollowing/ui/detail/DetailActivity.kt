package com.example.aplikasifollowerfollowing.ui.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.aplikasifollowerfollowing.R
import com.example.aplikasifollowerfollowing.adapter.SectionsPagerAdapter
import com.example.aplikasifollowerfollowing.data.DetailUserResponse
import com.example.aplikasifollowerfollowing.database.FavoriteUserEntitity
import com.example.aplikasifollowerfollowing.databinding.ActivityDetailBinding
import com.example.aplikasifollowerfollowing.factory.ViewModelFactory
import com.example.aplikasifollowerfollowing.ui.favorite.FavoriteViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailUserViewModel by viewModels<DetailUserViewModel>()
    private var name: String? = null

    private val favoriteUserViewModel by viewModels <FavoriteViewModel>() {
        ViewModelFactory.getInstance(application)
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        name = intent.getStringExtra(USER_NAME)
        detailUserViewModel.getUserDetail(name.toString())

        //menambahkan user ke favorite
        detailUserViewModel.users.observe(this){ user ->
            if (user != null)
                setUserDetailData(user)
                favoriteUserViewModel.isFavorite(name.toString()).observe(this) {isFavorite->
                    val favoriteUser = FavoriteUserEntitity(user.login, user.avatarUrl)
                    if (isFavorite){
                        binding.btnFavorite.setImageDrawable(
                            ContextCompat.getDrawable(
                                binding.btnFavorite.context,
                                R.drawable.ic_fav_active
                            )
                        )

                        binding.btnFavorite.setOnClickListener {
                            favoriteUserViewModel.deleteFavorite(favoriteUser)
                            Toast.makeText(this, "User sudah dihapus", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        binding.btnFavorite.setImageDrawable(
                            ContextCompat.getDrawable(
                                binding.btnFavorite.context,
                                R.drawable.ic_fav_nonactive
                            )
                        )

                        binding.btnFavorite.setOnClickListener {
                            favoriteUserViewModel.saveFavorite(favoriteUser)
                            Toast.makeText(this, "User berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
        }



        detailUserViewModel.isLoading.observe(this){
            showLoading(it)
        }


        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        sectionsPagerAdapter.username = name.toString()
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager){ tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        detailUserViewModel.snackbarText.observe(this) {
            Snackbar.make(window.decorView.rootView, it, Snackbar.LENGTH_SHORT).show()
        }


    }

    private fun setUserDetailData(users: DetailUserResponse){
        binding.apply{
            textUsername.visibility = View.VISIBLE
            textUsername.text = "${users.login}"

            textNamaDetail.visibility = View.VISIBLE
            textNamaDetail.text = users.name

            textFollower.visibility = View.VISIBLE
            textFollower.text = "${users.followers} follower"

            textFollowing.visibility = View.VISIBLE
            textFollowing.text = "${users.following} following"

            detailImage.visibility = View.VISIBLE
            Glide.with(detailImage.context)
                .load(users.avatarUrl)
                .into(detailImage)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    companion object {

        const val USER_NAME = ""
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

}