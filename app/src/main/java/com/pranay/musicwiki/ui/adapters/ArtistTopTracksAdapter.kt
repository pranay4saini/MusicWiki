package com.news.musicwiki.Utils.Adapters

import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.pranay.musicwiki.databinding.TopAlbumRowBinding
import com.pranay.musicwiki.model.artistTopTracks.Track

class ArtistTopTracksAdapter(private val tracks: ArrayList<Track>) :
    RecyclerView.Adapter<ArtistTopTracksAdapter.ViewHolder>() {

    class ViewHolder(val binding: TopAlbumRowBinding) : RecyclerView.ViewHolder(binding.root) {
        //Bind data to Views
        fun bind(track: Track) {
            itemView.apply {
                binding.albumName.text = track.name
                binding.artistName.text = track.artist.name
                Glide.with(binding.topAlbumImage.context)
                    .asBitmap()
                    .load(track.image[2].url)

                    .into(binding.topAlbumImage)
                itemView.setOnClickListener {

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
        return tracks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tracks[position])
    }

    //Function to add genre to ArrayList of genre
    fun addTopTracks(topTracks: List<Track>) {
        this.tracks.apply {
            clear()
            addAll(topTracks)
        }
    }
}