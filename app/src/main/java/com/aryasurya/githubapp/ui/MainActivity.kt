package com.aryasurya.githubapp.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.aryasurya.githubapp.R
import com.aryasurya.githubapp.data.remote.response.ItemsItem
import com.aryasurya.githubapp.databinding.ActivityMainBinding
import com.aryasurya.githubapp.helper.FollowersViewModelFactory
import com.aryasurya.githubapp.ui.detailuser.DetailActivity
import com.aryasurya.githubapp.ui.followed.FollowedActivity
import com.aryasurya.githubapp.ui.themesetting.ThemeViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var themeViewModel: ThemeViewModel
    private var clicked = false

    private var menu: Menu? = null


    private var themeNow = false

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarHome)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        themeViewModel =
            ViewModelProvider(this, FollowersViewModelFactory.getInstance("", this@MainActivity.application
            ))[ThemeViewModel::class.java]


        // Ambil pengaturan tema saat aplikasi pertama kali dijalankan
        themeViewModel.getThemeSetting().observe(this) { currentThemeSetting ->
            themeNow = currentThemeSetting
//                Log.d(TAG, "onCreate: theme saat ini : $currentThemeSetting")

            // Set tema awal aplikasi sesuai dengan pengaturan saat ini
            val initialTheme = if (currentThemeSetting) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }

            val themeItem = menu?.findItem(R.id.theme)
            if (themeNow) {
                themeItem?.setIcon(R.drawable.baseline_wb_sunny_24) // Ganti dengan ikon matahari
            } else {
                themeItem?.setIcon(R.drawable.baseline_nights_stay_24) // Ganti dengan ikon malam
            }

            AppCompatDelegate.setDefaultNightMode(initialTheme)
        }

        mainViewModel.listProfile.observe(this) {
            setUserData(it)
            Log.d(TAG, "onCreate: ${it.size}")
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.home_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = getString(R.string.github_username)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
//                    EspressoIdlingResource.increment()
//                    mainViewModel.searchUserByUsername(query ?: "")
                    mainViewModel.findUsers(query ?: "")
                    clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }
        // Simpan referensi menu ke properti menu
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu -> {
                val followedItem = menu?.findItem(R.id.followed)
                val themeItem = menu?.findItem(R.id.theme)

                if (!clicked) {
                    followedItem?.isVisible = true
                    themeItem?.isVisible = true
                } else {
                    followedItem?.isVisible = false
                    themeItem?.isVisible = false
                }

                clicked = !clicked // Toggle the visibility state
                return true

            }
            R.id.followed -> {
                val intentToFollowedActivity = Intent(this, FollowedActivity::class.java)
                startActivity(intentToFollowedActivity)
//                Toast.makeText(this, "Followed Account button clicked", Toast.LENGTH_SHORT).show()

                return true
            }
            R.id.theme -> {
                Log.d(TAG, "onClick: tema $themeNow")

                // Toggle antara tema terang dan tema gelap
                val newTheme = if (themeNow) {
                    AppCompatDelegate.MODE_NIGHT_NO
                } else {
                    AppCompatDelegate.MODE_NIGHT_YES
                }

                // Terapkan tema yang baru
                AppCompatDelegate.setDefaultNightMode(newTheme)

                // Simpan pengaturan tema yang baru ke dalam database
                themeViewModel.saveThemeSetting(!themeNow)

//                Toast.makeText(this@MainActivity, "Setting button clicked", Toast.LENGTH_SHORT).show()

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }



    private fun setUserData(userLogin: List<ItemsItem?>?) {
        binding.tvResultCount.text = getString(R.string.showing_results, userLogin?.size)
        val adapter = UsersAdapter()
        adapter.submitList(userLogin)
        binding.rvUsers.adapter = adapter

        adapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
                intentToDetail.putExtra("DATA", data.login)
                startActivity(intentToDetail)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}