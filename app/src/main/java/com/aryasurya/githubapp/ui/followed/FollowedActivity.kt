package com.aryasurya.githubapp.ui.followed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aryasurya.githubapp.R
import com.aryasurya.githubapp.databinding.ActivityFollowedBinding

class FollowedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFollowedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFollowedBinding.inflate(layoutInflater)


    }
}