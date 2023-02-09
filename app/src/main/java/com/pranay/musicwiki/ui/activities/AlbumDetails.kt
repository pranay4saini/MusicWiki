package com.pranay.musicwiki.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pranay.musicwiki.R
import com.pranay.musicwiki.api.RetrofitBuilder
import com.pranay.musicwiki.api.RetrofitHelper
import com.pranay.musicwiki.databinding.ActivityAlbumDetailsBinding
import com.pranay.musicwiki.model.album.AlbumDetails
import com.pranay.musicwiki.model.artists.ArtistsDetails
import com.pranay.musicwiki.model.genre.Tag
import com.pranay.musicwiki.model.topGenres.TopGenres
import com.pranay.musicwiki.ui.adapters.GenreAdapter
import com.pranay.musicwiki.util.Status
import com.pranay.musicwiki.viewModel.MusicViewModel
import com.pranay.musicwiki.viewModel.MusicViewModelProviderFactory
import retrofit2.Response
import java.net.URLEncoder

class AlbumDetails : AppCompatActivity() {
    //Late initialize views
    private lateinit var binding: ActivityAlbumDetailsBinding
    private lateinit var viewModel: MusicViewModel
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var recyclerView: RecyclerView
    lateinit var album: String
    lateinit var artist: String
    lateinit var albumEncoded: String
    lateinit var artistEncoded: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val bundle = intent.extras
        album = bundle!!.getString("albumName").toString()
        artist = bundle.getString("artistName").toString()
        albumEncoded = URLEncoder.encode(album, "UTF-8").replace("+", "%20")
        artistEncoded = URLEncoder.encode(artist, "UTF-8").replace("+", "%20")
        initViews()
        setupUI()
        setupViewModel()
        setupObservers()
    }

    //Attach adapters to recyclerview
    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        // Display all the items by passing -1 as argument(Logic in Adapter Class)
        genreAdapter = GenreAdapter(arrayListOf(), -1)
        recyclerView.adapter = genreAdapter
    }

    //Setup ViewModels
    private fun setupViewModel() {
       viewModel =  ViewModelProvider(this,MusicViewModelProviderFactory(RetrofitHelper(RetrofitBuilder.apiService)))
           .get(MusicViewModel::class.java)
//        viewModel = ViewModelProviders.of(
//            this,
//            ViewModelFactory(RetrofitHelper(RetrofitBuilder.apiService))
//        ).get(MusicViewModel::class.java)
    }

    //Setup Observers
    private fun setupObservers() {
        viewModel.getTopGenres(artist = artistEncoded, album = albumEncoded)
            .observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { genres -> retrieveList(genres) }
                        }
                        Status.ERROR -> {
                            Log.e("Response", it.message.toString())
                        }
                        Status.LOADING -> {

                        }


                    }
                }
            })

        viewModel.getAlbumDetails(artist = artistEncoded, album = albumEncoded)
            .observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Log.e("Response", resource.data.toString())
                            resource.data?.let { albumDetails -> setAlbumDetails(albumDetails) }
                        }
                        Status.ERROR -> {
                            Log.e("Error", it.message.toString())
                        }
                        Status.LOADING -> {

                        }
                    }
                }
            })

        viewModel.getArtistDetails(artist = artistEncoded)
            .observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Log.e("Response", resource.data.toString())
                            resource.data?.let { artistDetails -> setArtistDetails(artistDetails) }
                        }
                        Status.ERROR -> {
                            Log.e("Error", it.message.toString())
                        }
                        Status.LOADING -> {

                        }
                    }
                }
            })

    }

    //Retrieve data and add in our adapters
    private fun retrieveList(genres: Response<TopGenres>) {
        val tags: List<Tag> = genres.body()!!.toptags.tag
        genreAdapter.apply {
            addGenre(tags)
            notifyDataSetChanged()
        }
    }

    //Retrieve genre details and adds to our TextView
    private fun setAlbumDetails(albumsDetail: Response<AlbumDetails>) {
        val imageUrl: String = albumsDetail.body()!!.album.image[3].url
        binding.albumName.text = albumsDetail.body()!!.album.name
        binding.artistName.text = albumsDetail.body()!!.album.artist.toString()
        Glide.with(binding.albumImage.context)
            .load(imageUrl)
            .into(binding.albumImage)
    }

    //Retrieve genre details and adds to our TextView
    private fun setArtistDetails(artistDetail: Response<ArtistsDetails>) {
        val artistInformation: String = artistDetail.body()!!.artist.bio.summary
        binding.textArtistDetails.text=artistInformation
    }

    //Initialize recyclerView
    private fun initViews() {
        recyclerView = findViewById(R.id.recycleView)
    }
}