package com.pranay.musicwiki.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.news.musicwiki.Utils.Adapters.TopTracksAdapter

import com.pranay.musicwiki.R
import com.pranay.musicwiki.api.RetrofitBuilder
import com.pranay.musicwiki.api.RetrofitHelper

import com.pranay.musicwiki.databinding.FragmentTracksBinding
import com.pranay.musicwiki.model.topTracks.TopTracks
import com.pranay.musicwiki.model.topTracks.Track
import com.pranay.musicwiki.util.Status
import com.pranay.musicwiki.viewModel.MusicViewModel
import com.pranay.musicwiki.viewModel.MusicViewModelProviderFactory
import retrofit2.Response


class TracksFragment : Fragment() {
    //Late initialize views
    private lateinit var binding: FragmentTracksBinding
    private lateinit var viewModel: MusicViewModel
    private lateinit var topTracksAdapter: TopTracksAdapter
    private lateinit var recyclerView: RecyclerView
    lateinit var genre: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        genre = this.arguments?.getString("tag").toString()
        binding = FragmentTracksBinding.inflate(inflater, container, false)

        return binding.root     }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        genre = this.arguments?.getString("tag").toString()
        //Initialize lately initialized views,setup ViewModels,attach adapters to recyclerview
        //and setup Observers(for API calls) using user defined functions
        //Make network error TextView invisible
        if (::topTracksAdapter.isInitialized) {
            if (topTracksAdapter.itemCount > 0)
                recyclerView.adapter = null
        }
        initViews()
        setupUI()
        setupViewModel()
        setupObservers()
    }

    //Attach adapters to recyclerview
    private fun setupUI() {
        recyclerView.layoutManager = GridLayoutManager(
            activity,
            2,
            GridLayoutManager.VERTICAL,
            false
        )
        DividerItemDecoration(
            recyclerView.context,
            (recyclerView.layoutManager as GridLayoutManager).orientation
        )
        topTracksAdapter = TopTracksAdapter(arrayListOf())
        recyclerView.adapter = topTracksAdapter
    }

    //Setup ViewModels
    private fun setupViewModel() {
        viewModel =  ViewModelProvider(this,
            MusicViewModelProviderFactory(RetrofitHelper(RetrofitBuilder.apiService))
        )
            .get(MusicViewModel::class.java)
    }

    //Setup Observers
    private fun setupObservers() {
        activity?.let { it ->
            viewModel.getTopTracks(genre).observe(it, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            if (resource.data?.code() == 200) {
                                binding.progressBar.visibility = View.GONE
                                recyclerView.visibility = View.VISIBLE
                                retrieveList(resource.data)
                            } else {
                                binding.progressBar.visibility = View.GONE
                                binding.textViewEmpty.text =
                                    "Some error has occurred with status Code " + resource.data!!.code()
                                binding.textViewEmpty.visibility = View.VISIBLE
                                recyclerView.visibility = View.GONE
                            }
                        }
                        Status.ERROR -> {
                            binding.progressBar.visibility = View.GONE
                            recyclerView.visibility = View.GONE
                            //Show network error textView
                            binding.textViewEmpty.visibility = View.VISIBLE
                        }
                        Status.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                            recyclerView.visibility = View.GONE
                        }
                    }
                }
            })
        }
    }

    //Retrieve data and add in our adapters
    private fun retrieveList(topTracks: Response<TopTracks>) {
        val albums: List<Track> = topTracks.body()!!.tracks.track
        topTracksAdapter.apply {
            addTopTracks(albums)
            notifyDataSetChanged()
        }
    }

    //Initialize recyclerView
    private fun initViews() {
        recyclerView = requireView().findViewById(R.id.recycleView)
    }
}