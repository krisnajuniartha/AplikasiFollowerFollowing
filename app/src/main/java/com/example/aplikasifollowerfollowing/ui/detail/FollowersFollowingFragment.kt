package com.example.aplikasifollowerfollowing.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasifollowerfollowing.adapter.UsersAdapter
import com.example.aplikasifollowerfollowing.data.ItemsItem
import com.example.aplikasifollowerfollowing.databinding.FragmentFollowersFollowingBinding


class FollowersFollowingFragment : Fragment() {

    private var _binding: FragmentFollowersFollowingBinding? = null
    private val binding get() = _binding!!

    private var position = 0
    private var username = ""
    private val mainViewModel by viewModels<DetailUserViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_SECTION_NUMBER)
            username = it.getString(ARG_USERNAME).toString()
        }

        val layoutManager = LinearLayoutManager (requireActivity())
        binding.rvFollowerFollowing.layoutManager = layoutManager

        mainViewModel.isFollower.observe(requireActivity()){follower ->
            setUserFollower(follower)
        }

        mainViewModel.isFollowing.observe(requireActivity()){following ->
            setUserFollowing(following)
        }

        mainViewModel.isLoading.observe(requireActivity()){
            showLoading(it)
        }


        if (position == 1){
            mainViewModel.getFollower(username)

        } else {
            mainViewModel.getFollowing(username)
        }

    }

    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?,
                               savedInstanceState: Bundle?): View? {

        _binding = FragmentFollowersFollowingBinding.inflate(inflater, container, false)
        val view = binding.root
        return  view

    }

    private fun setUserFollower (follower: List<ItemsItem>) {
        val adapter = UsersAdapter(requireActivity())
        adapter.submitList(follower)
        binding.rvFollowerFollowing.adapter = adapter
    }

    private fun setUserFollowing (following: List<ItemsItem>) {
        val adapter = UsersAdapter(requireActivity())
        adapter.submitList(following)
        binding.rvFollowerFollowing.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_USERNAME = ""
    }
}