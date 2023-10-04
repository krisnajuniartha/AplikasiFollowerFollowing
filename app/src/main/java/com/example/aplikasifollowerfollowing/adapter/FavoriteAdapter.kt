package com.example.aplikasifollowerfollowing.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aplikasifollowerfollowing.FavoriteUserDiffCallback
import com.example.aplikasifollowerfollowing.database.FavoriteUserEntitity
import com.example.aplikasifollowerfollowing.databinding.ProfileItemBinding
import com.example.aplikasifollowerfollowing.ui.detail.DetailActivity
import com.example.aplikasifollowerfollowing.ui.detail.DetailActivity.Companion.USER_NAME

class FavoriteAdapter(): RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {
    private val listUser = ArrayList<FavoriteUserEntitity>()

    fun setListUser(listUserEntitity: List<FavoriteUserEntitity>){
        val diffCallback = FavoriteUserDiffCallback(this.listUser, listUserEntitity)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listUser.clear()
        this.listUser.addAll(listUserEntitity)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ProfileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    class MyViewHolder(val binding: ProfileItemBinding): RecyclerView.ViewHolder (binding.root){
        fun bind(user: FavoriteUserEntitity){
            binding.tvProfile.text = user.username
            Glide.with(binding.root)
                .load(user.avataruser)
                .into(binding.imageProfile)

            binding.tvItem.setOnClickListener {
                val intent = Intent(it.context, DetailActivity::class.java)
                intent.putExtra(USER_NAME, user.username)
                it.context.startActivity(intent)
            }
        }
    }
}