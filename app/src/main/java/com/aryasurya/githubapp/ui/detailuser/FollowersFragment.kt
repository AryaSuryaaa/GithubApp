package com.aryasurya.githubapp.ui.detailuser

import android.content.Intent
import com.aryasurya.githubapp.ui.FollowersAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aryasurya.githubapp.data.remote.response.FollowersResponseItem
import com.aryasurya.githubapp.databinding.FragmentFollowersBinding

class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private var position: Int? = null
    private var username: String? = null
    private lateinit var followersViewModel: FollowersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        followersViewModel = ViewModelProvider(requireActivity())[FollowersViewModel::class.java]

        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFollowersDetail.layoutManager = LinearLayoutManager(requireActivity())

        followersViewModel.listFollowers.observe(requireActivity()) { followersList ->
            val adapter = FollowersAdapter()
            adapter.submitList(followersList)
            binding.rvFollowersDetail.adapter = adapter

            adapter.setOnItemClickCallback(object : FollowersAdapter.OnItemClickCallback {
                override fun onItemClicked(data: FollowersResponseItem) {
                    val intentToDetail = Intent(requireActivity(), DetailActivity::class.java)
                    intentToDetail.putExtra("DATA", data.login)
                    startActivity(intentToDetail)
                }
            })
        }

        followersViewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }
    }

    override fun onResume() {
        super.onResume()

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }
        if (position == 1) {
            followersViewModel.findDataUserFollowers()
        } else {
            followersViewModel.findDataUserFollowing()
        }
    }

    private fun showLoading(state: Boolean) { binding.progressBarDetail.visibility = if (state) View.VISIBLE else View.GONE }

    override fun onDestroyView() {
        super.onDestroyView()
        // Hapus binding ketika fragment dihancurkan
        _binding = null
    }

    companion object {
        const val ARG_POSITION = "section number"
        const val ARG_USERNAME = "username"
    }
}
