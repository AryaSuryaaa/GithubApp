package com.aryasurya.githubapp.ui

import FollowersAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aryasurya.githubapp.databinding.FragmentFollowersBinding

class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private var position: Int? = null
    private var username: String? = null
    private lateinit var followersViewModel: FollowersViewModel

    companion object {
        const val ARG_POSITION = "section number"
        const val ARG_USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        followersViewModel = ViewModelProvider(requireActivity()).get(FollowersViewModel::class.java)

        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val index = arguments?.getInt(ARG_POSITION, 1)
//        binding.sectionLevel.text = getString(R.string.content_tab_text, index)

        binding.rvFollowersDetail.layoutManager = LinearLayoutManager(requireActivity())

        followersViewModel.listFollowers.observe(requireActivity()) {
            val adapter = FollowersAdapter()
            adapter.submitList(it)
            binding.rvFollowersDetail.adapter = adapter
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
}