package com.example.aplikasifollowerfollowing.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aplikasifollowerfollowing.ui.detail.DetailActivity
import com.example.aplikasifollowerfollowing.data.ItemsItem
import com.example.aplikasifollowerfollowing.databinding.ProfileItemBinding

class UsersAdapter(val activity: Activity) : ListAdapter<ItemsItem, UsersAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ProfileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener{
            val moveDetailIntent = Intent(holder.itemView.context, DetailActivity::class.java)
            moveDetailIntent.putExtra(DetailActivity.USER_NAME, user.login)
            holder.itemView.context.startActivity(moveDetailIntent)
        }
    }


    inner class MyViewHolder(val binding: ProfileItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(username: ItemsItem) {
            Glide.with(binding.imageProfile.context)
                .load(username.avatarUrl)
                .into(binding.imageProfile)
            binding.tvProfile.text = "${username.login}"
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}