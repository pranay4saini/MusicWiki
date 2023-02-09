package com.pranay.musicwiki.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pranay.musicwiki.R
import com.pranay.musicwiki.api.RetrofitBuilder
import com.pranay.musicwiki.api.RetrofitHelper
import com.pranay.musicwiki.databinding.ActivityMainBinding
import com.pranay.musicwiki.model.genre.Genre
import com.pranay.musicwiki.model.genre.Tag
import com.pranay.musicwiki.ui.adapters.GenreAdapter
import com.pranay.musicwiki.util.Status
import com.pranay.musicwiki.viewModel.MusicViewModel
import com.pranay.musicwiki.viewModel.MusicViewModelProviderFactory
import retrofit2.Response


class MainActivity : AppCompatActivity(), View.OnClickListener  {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MusicViewModel
    private lateinit var genreAdapterMore: GenreAdapter
    private lateinit var genreAdapterLess: GenreAdapter
    private lateinit var recyclerView: RecyclerView
    private var flagLess = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        binding.spinKitMasterView.visibility= View.VISIBLE

        initViews()
        setupUI()
        setupViewModel()
        setupObservers()
        binding.textViewEmpty.visibility = View.GONE
        binding.loadMoreButton.setOnClickListener(this)





    }

    //Attach adapters to recyclerview
    private fun setupUI() {
        recyclerView.layoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
        // Display only 10 items instead of the total size initially (4x3)
        genreAdapterLess = GenreAdapter(arrayListOf(), 10)
        // Display all the items by passing -1 as argument(Logic in Adapter Class)
        genreAdapterMore = GenreAdapter(arrayListOf(), -1)
        recyclerView.adapter = genreAdapterLess
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
        viewModel.getGenre().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        if (resource.data!!.code() == 200) {
                            binding.progressBar.visibility = View.GONE
                            recyclerView.visibility = View.VISIBLE
                            //Retrieve response and add to adapters using this function
                            retrieveList(resource.data)
                        }
                        else{
                            binding.progressBar.visibility = View.GONE
                            binding.textViewEmpty.text="Some error has occurred with status Code "+ resource.data.code()
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

    //Retrieve data and add in our adapters
    private fun retrieveList(genres: Response<Genre>) {
        val tags: List<Tag> = genres.body()!!.toptags.tag
        //To display less items
        genreAdapterLess.apply {
            addGenre(tags)
            notifyDataSetChanged()
        }
        //To display more items items
        genreAdapterMore.apply {
            addGenre(tags)
            notifyDataSetChanged()
        }
    }

    //Initialize recyclerView
    private fun initViews() {
        recyclerView = binding.recycleView
    }

    override fun onClick(p0: View?) {
        binding.loadMoreButton.setOnClickListener {
            Log.e("HERE", "Test")
            if (!flagLess) {
                Log.e("HERE", "lessFalse")
                recyclerView.adapter = genreAdapterLess
                binding.loadMoreButton.setImageResource(R.drawable.arrow_down)
                flagLess = true
            } else {
                Log.e("HERE", "lessTrue")
                recyclerView.adapter = genreAdapterMore
                binding.loadMoreButton.setImageResource(R.drawable.arrow_down)
                flagLess = false
            }
        }
    }
}