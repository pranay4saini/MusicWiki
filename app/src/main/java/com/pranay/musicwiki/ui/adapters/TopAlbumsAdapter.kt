package com.news.musicwiki.Utils.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pranay.musicwiki.databinding.TopAlbumRowBinding
import com.pranay.musicwiki.model.topAlbum.Album
import com.pranay.musicwiki.ui.activities.AlbumDetails



class TopAlbumsAdapter(private val albums: ArrayList<Album>) :
    RecyclerView.Adapter<TopAlbumsAdapter.ViewHolder>() {

    class ViewHolder(val binding: TopAlbumRowBinding) : RecyclerView.ViewHolder(binding.root) {
        //Bind data to Views
        fun bind(album: Album) {
            itemView.apply {
                binding.albumName.text = album.name
                binding.artistName.text = album.artist.name
                Glide.with(binding.topAlbumImage.context)
                    .asBitmap()
                    .load(album.image[2].url)

                    .into(binding.topAlbumImage)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, AlbumDetails::class.java)
                    intent.putExtra("albumName", album.name)
                    intent.putExtra("artistName", album.artist.name)
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
        return albums.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albums[position])
    }

    //Function to add genre to ArrayList of genre
    fun addTopAlbums(topAlbums: List<Album>) {
        this.albums.apply {
            clear()
            addAll(topAlbums)
        }
    }
}