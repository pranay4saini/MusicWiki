package com.pranay.musicwiki.ui


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pranay.musicwiki.databinding.ItemTagBinding

class TagAdapter( val context: Context) :RecyclerView.Adapter<TagAdapter.ViewHolder>() {

    private var tagList = mutableListOf<TagsListResponse>()
    class ViewHolder(val binding:ItemTagBinding) :RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTagBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tagList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val item =tagList[position]
        holder.binding.Button.text = item.name
        holder.binding.Button.setOnClickListener {
            val intent = Intent(context,TagDetails::class.java)
            context.startActivity(intent)
        }
    }
    fun setTags(tags: List<TagsListResponse>) {
        this.tagList = tags.toMutableList()
        notifyDataSetChanged()
    }

}