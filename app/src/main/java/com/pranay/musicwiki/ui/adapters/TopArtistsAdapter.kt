package com.news.musicwiki.Utils.Adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pranay.musicwiki.databinding.TopAlbumRowBinding
import com.pranay.musicwiki.model.topArtist.Artist
import com.pranay.musicwiki.ui.ArtistDetailActivity


class TopArtistsAdapter(private val artists: ArrayList<Artist>) :
    RecyclerView.Adapter<TopArtistsAdapter.ViewHolder>() {

    class ViewHolder(val binding: TopAlbumRowBinding) : RecyclerView.ViewHolder(binding.root) {
        //Bind data to Views
        fun bind(artist: Artist) {
            itemView.apply {
                binding.albumName.visibility = View.GONE
                binding.artistName.text = artist.name
                Glide.with(binding.topAlbumImage.context)
                    .asBitmap()
                    .load(artist.image[2].url)
//                    .placeholder(R.drawable.loading)
                    .into(binding.topAlbumImage)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, ArtistDetailActivity::class.java)
                    Log.e("MBID in ADAPTER",artist.mbid)
                    intent.putExtra("mbid", artist.mbid)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TopAlbumRowBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return artists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(artists[position])
    }

    //Function to add genre to ArrayList of genre
    fun addTopArtists(topArtists: List<Artist>) {
        this.artists.apply {
            clear()
            addAll(topArtists)
        }
    }
}