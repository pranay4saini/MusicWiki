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
import com.news.musicwiki.Utils.Adapters.TopArtistsAdapter

import com.pranay.musicwiki.R
import com.pranay.musicwiki.api.RetrofitBuilder
import com.pranay.musicwiki.api.RetrofitHelper

import com.pranay.musicwiki.databinding.FragmentArtistsBinding
import com.pranay.musicwiki.model.topArtist.Artist
import com.pranay.musicwiki.model.topArtist.TopArtist

import com.pranay.musicwiki.util.Status
import com.pranay.musicwiki.viewModel.MusicViewModel
import com.pranay.musicwiki.viewModel.MusicViewModelProviderFactory
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [ArtistsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArtistsFragment : Fragment() {
    //Late initialize views
    private lateinit var binding: FragmentArtistsBinding
    private lateinit var viewModel: MusicViewModel
    private lateinit var topArtistsAdapter: TopArtistsAdapter
    private lateinit var recyclerView: RecyclerView
    lateinit var genre: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        genre = this.arguments?.getString("tag").toString()
        binding = FragmentArtistsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        genre = this.arguments?.getString("tag").toString()
        //Initialize lately initialized views,setup ViewModels,attach adapters to recyclerview
        //and setup Observers(for API calls) using user defined functions
        //Make network error TextView invisible
        if (::topArtistsAdapter.isInitialized) {
            if (topArtistsAdapter.itemCount > 0)
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
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as GridLayoutManager).orientation
            )
        )
        topArtistsAdapter = TopArtistsAdapter(arrayListOf())
        recyclerView.adapter = topArtistsAdapter
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
            viewModel.getTopArtists(genre).observe(it, Observer {
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
    private fun retrieveList(topAlbums: Response<TopArtist>) {
        val albums: List<Artist> = topAlbums.body()!!.topartists.artist
        topArtistsAdapter.apply {
            addTopArtists(albums)
            notifyDataSetChanged()
        }
    }

    //Initialize recyclerView
    private fun initViews() {
        recyclerView = requireView().findViewById(R.id.recycleView)
    }
}