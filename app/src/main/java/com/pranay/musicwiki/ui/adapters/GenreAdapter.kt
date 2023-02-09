package com.pranay.musicwiki.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pranay.musicwiki.databinding.ItemGenreRowBinding

import com.pranay.musicwiki.model.genre.Tag
import com.pranay.musicwiki.ui.activities.GenreActivity

class GenreAdapter(private val genres: ArrayList<Tag>, private val count: Int) :
    RecyclerView.Adapter<GenreAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemGenreRowBinding) : RecyclerView.ViewHolder(binding.root) {
        //Bind data to Views
        fun bind(tag: Tag) {
            itemView.apply {
                binding.genreName.text = tag.name
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, GenreActivity::class.java)
                intent.putExtra("genreName", tag.name)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGenreRowBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (count != -1)
            count
        else {
            genres.size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(genres[position])
    }

    //Function to add genre to ArrayList of genre
    fun addGenre(topTags: List<Tag>) {
        this.genres.apply {
            clear()
            addAll(topTags)
        }
    }
}
