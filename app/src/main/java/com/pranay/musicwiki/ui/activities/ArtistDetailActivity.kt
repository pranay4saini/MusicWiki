package com.pranay.musicwiki.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.news.musicwiki.Utils.Adapters.ArtistTopTracksAdapter
import com.pranay.musicwiki.api.RetrofitBuilder
import com.pranay.musicwiki.api.RetrofitHelper

import com.pranay.musicwiki.databinding.ActivityArtistDetailBinding
import com.pranay.musicwiki.model.artistTopAlbum.Album
import com.pranay.musicwiki.model.artistTopAlbum.ArtistTopAlbums
import com.pranay.musicwiki.model.artistTopTracks.ArtistTopTracks
import com.pranay.musicwiki.model.artistTopTracks.Track
import com.pranay.musicwiki.model.artists.ArtistsDetails
import com.pranay.musicwiki.model.genre.Tag
import com.pranay.musicwiki.model.topGenres.TopGenres
import com.pranay.musicwiki.ui.adapters.ArtistTopAlbumAdapter
import com.pranay.musicwiki.ui.adapters.GenreAdapter
import com.pranay.musicwiki.util.Status
import com.pranay.musicwiki.viewModel.MusicViewModel
import com.pranay.musicwiki.viewModel.MusicViewModelProviderFactory
import retrofit2.Response
import kotlin.math.ln

class ArtistDetailActivity : AppCompatActivity() {

    //Late initialize views
    private lateinit var binding: ActivityArtistDetailBinding
    private lateinit var viewModel: MusicViewModel
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var topAlbumsAdapter: ArtistTopAlbumAdapter
    private lateinit var topTracksAdapter: ArtistTopTracksAdapter
    private lateinit var genreRecyclerView: RecyclerView
    private lateinit var albumsRecyclerView: RecyclerView
    private lateinit var tracksRecyclerView: RecyclerView

    lateinit var mbid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtistDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val bundle = intent.extras
        mbid = bundle!!.getString("mbid").toString()
        initViews()
        setupUI()
        setupViewModel()
        setupObservers()
    }
    //Attach adapters to recyclerview
    private fun setupUI() {
        genreRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        genreAdapter = GenreAdapter(arrayListOf(), -1)
        genreRecyclerView.adapter = genreAdapter

        albumsRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        topAlbumsAdapter = ArtistTopAlbumAdapter(arrayListOf())
        albumsRecyclerView.adapter = topAlbumsAdapter

        tracksRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        topTracksAdapter = ArtistTopTracksAdapter(arrayListOf())
        tracksRecyclerView.adapter = topTracksAdapter
    }

    //Setup ViewModels
    private fun setupViewModel() {
        viewModel =  ViewModelProvider(this,
            MusicViewModelProviderFactory(RetrofitHelper(RetrofitBuilder.apiService))
        )
            .get(MusicViewModel::class.java)
//        viewModel = ViewModelProviders.of(
//            this,
//            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
//        ).get(ViewModels::class.java)
    }

    //Setup Observers
    private fun setupObservers() {
        viewModel.getArtistTags(mbid = mbid)
            .observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {

                            resource.data?.let { genres -> retrieveGenreList(genres) }
                        }
                        Status.ERROR -> {
                            Log.e("Response", it.message.toString())
                        }
                        Status.LOADING -> {
                        }
                    }
                }
            })

        viewModel.getArtistAlbums(mbid = mbid)
            .observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Log.e("Response", "yes" + resource.data!!.code().toString())
                            retrieveAlbumsList(resource.data)
                        }
                        Status.ERROR -> {
                            Log.e("Error", it.message.toString())
                        }
                        Status.LOADING -> {

                        }
                    }
                }
            })

        viewModel.getArtistTracks(mbid = mbid)
            .observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Log.e("ResponseCheck", resource.data!!.body().toString())
                            retrieveTracksList(resource.data)
                        }
                        Status.ERROR -> {
                            Log.e("Error", it.message.toString())
                        }
                        Status.LOADING -> {
                        }
                    }
                }
            })

        viewModel.getArtistInfo(mbid = mbid)
            .observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Log.e("Response", resource.data.toString())
                            resource.data?.let { artistInfo -> setArtistDetails(artistInfo) }
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
    private fun retrieveGenreList(genres: Response<TopGenres>) {
        val tags: List<Tag> = genres.body()!!.toptags.tag
        genreAdapter.apply {
            addGenre(tags)
            notifyDataSetChanged()
        }
    }

    //Retrieve data and add in our adapters
    private fun retrieveAlbumsList(genres: Response<ArtistTopAlbums>) {
        val albums: List<Album> = genres.body()!!.topalbums.album
        topAlbumsAdapter.apply {
            addTopAlbums(albums)
            notifyDataSetChanged()
        }
    }

    //Retrieve data and add in our adapters
    private fun retrieveTracksList(genres: Response<ArtistTopTracks>) {
        val tracks: List<Track> = genres.body()!!.toptracks.track
        topTracksAdapter.apply {
            addTopTracks(tracks)
            notifyDataSetChanged()
        }
    }

    //Retrieve details and adds to our TextView
    private fun setArtistDetails(artistDetail: Response<ArtistsDetails>) {
        val playCount: Long = artistDetail.body()!!.artist.stats.playcount.toLong()
        val playCountString = numbersCount(playCount)
        val followersCount: Long = artistDetail.body()!!.artist.stats.listeners.toLong()
        val followersCountString = numbersCount(followersCount)
        val imageUrl = artistDetail.body()!!.artist.image[2].url
        binding.artistDescription.text = artistDetail.body()!!.artist.bio.summary
        binding.playCountNumber.text = playCountString
        binding.followersNumber.text = followersCountString
        Glide.with(binding.albumImage.context)
            .load(imageUrl)
//            .placeholder(R.drawable.loading)
            .into(binding.albumImage)
    }

    //Initialize recyclerView
    private fun initViews() {
        genreRecyclerView = binding.genreRecycleView
        tracksRecyclerView = binding.tracksRecycleView
        albumsRecyclerView = binding.albumsRecycleView
    }

    fun numbersCount(count: Long): String {
        if (count < 1000) return "" + count
        val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
        return String.format(
            "%.1f %c",
            count / Math.pow(1000.0, exp.toDouble()),
            "kMGTPE"[exp - 1]
        )
    }
}