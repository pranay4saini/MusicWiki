package com.pranay.musicwiki.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pranay.musicwiki.Repository
import com.pranay.musicwiki.api.RetrofitHelper
import com.pranay.musicwiki.databinding.ActivityTagDetailsBinding

class TagDetails : AppCompatActivity() {
    lateinit var tags: String
    private lateinit var binding: ActivityTagDetailsBinding


//    lateinit var viewmodel: MusicViewModel
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityTagDetailsBinding.inflate(layoutInflater)
//        val view = binding.root
//        setContentView(view)
//        val services = RetrofitHelper()
//        val repository = Repository()
//
//
//
//        tags = intent.getStringExtra("tagName")!!
//
//        val adapter = ViewPagerAdapter(supportFragmentManager)
//        adapter.setFragment(AlbumFragment.newInstance(tags,this), "ALBUMS")
//        adapter.setFragment(ArtistFragment.newInstance(tags,this), "ARTISTS")
//        adapter.setFragment(TracksFragment.newInstance(tags,this), "TRACKS")
//        binding.viewPager.adapter = adapter
//        binding.tabs.setupWithViewPager(binding.viewPager)
//
//
//
//
//    }
}