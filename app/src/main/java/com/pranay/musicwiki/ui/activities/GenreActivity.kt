package com.pranay.musicwiki.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.pranay.musicwiki.api.RetrofitBuilder
import com.pranay.musicwiki.api.RetrofitHelper
import com.pranay.musicwiki.databinding.ActivityGenreDetailsBinding
import com.pranay.musicwiki.model.genreDetails.GenreDetails
import com.pranay.musicwiki.model.genreDetails.GenreDetailsTags
import com.pranay.musicwiki.model.genreDetails.Wiki
import com.pranay.musicwiki.ui.fragments.AlbumsFragment
import com.pranay.musicwiki.ui.fragments.ArtistsFragment
import com.pranay.musicwiki.ui.fragments.TracksFragment
import com.pranay.musicwiki.util.Status
import com.pranay.musicwiki.viewModel.MusicViewModel
import com.pranay.musicwiki.viewModel.MusicViewModelProviderFactory
import retrofit2.Response

class GenreActivity : AppCompatActivity() {

    //Lateinit to initialize the variables later(in initViews())
    private lateinit var binding: ActivityGenreDetailsBinding
    private lateinit var viewModel: MusicViewModel
    private lateinit var genreName: String
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    //Title of viewpager
    private val titles = arrayOf("Albums", "Artists", "Tracks")
    private val fragmentList: MutableList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenreDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val bundle = Bundle()
        bundle.putString("tag", intent.extras?.getString("genreName").toString())
        val albumFrag = AlbumsFragment()
        val artistsFrag = ArtistsFragment()
        val tracksFrag = TracksFragment()

        albumFrag.arguments = bundle
        artistsFrag.arguments = bundle
        tracksFrag.arguments = bundle

        fragmentList.add(albumFrag)
        fragmentList.add(artistsFrag)
        fragmentList.add(tracksFrag)

        //Initialize Views, Attach Adapter to ViewPager and set Toolbar in our MainActivity
        initViews()
        attachAdapter()
        setGenreName()
        setupViewModel()
        setupObservers()
    }

    private fun attachAdapter() {
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return fragmentList.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragmentList[position]
            }
        }
        val tabLayoutMediator = TabLayoutMediator(
            tabLayout, viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = titles[position]
        }

        //Attach to TabLayoutMediator(Viewpager2)
        tabLayoutMediator.attach()
    }

    private fun initViews() {
        viewPager = binding.viewPager
        tabLayout = binding.tabLayout
    }

    //Set Genre Name in TextView
    private fun setGenreName() {
        // Get genre name (value) from previous activity
        val extras = intent.extras
        if (extras != null) {
            genreName = extras.getString("genreName").toString()
        }

        //Capitalize the first character of the genre name
        //Eg change rock to Rock
        ("" + genreName[0].uppercaseChar() + genreName.subSequence(
            1,
            genreName.length
        )).also { binding.textGenreName.text = it }
    }


    //Setup ViewModels
    private fun setupViewModel() {
        viewModel =  ViewModelProvider(this,
            MusicViewModelProviderFactory(RetrofitHelper(RetrofitBuilder.apiService))
        ).get(MusicViewModel::class.java)
    }

    //Setup Observers
    private fun setupObservers() {
        viewModel.getGenreDetails(genreName).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { detail -> setGenreDescription(detail) }
                    }
                    Status.ERROR -> {
                        binding.textGenreName.text="Some network error occurred. Please try again later"
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }

    //Retrieve genre details and adds to our TextView
    private fun setGenreDescription(genres: Response<GenreDetails>) {
        val tag: GenreDetailsTags? = genres.body()?.genreDetailsTag
        val wiki: Wiki? = tag?.wiki
        val summary: String? = wiki?.summary
        binding.textGenreDetails.text = summary
    }
}