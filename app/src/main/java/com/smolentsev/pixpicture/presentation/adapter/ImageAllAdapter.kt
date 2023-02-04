package com.smolentsev.pixpicture.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smolentsev.pixpicture.R
import com.smolentsev.pixpicture.domain.entity.Hit

class ImageAllAdapter() : RecyclerView.Adapter<ImageAllAdapter.ItemAdapterViewHolder>() {
    var image = listOf<Hit>()

    var onImageClickListener: ((Hit) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.images_adapter, parent, false)
        return ItemAdapterViewHolder(view)

    }

    override fun onBindViewHolder(viewHolder: ItemAdapterViewHolder, position: Int) {
        val _image = image[position]
        viewHolder.view.setOnClickListener {
            onImageClickListener?.invoke(_image)
        }
        Glide.with(viewHolder.itemView)
            .load(_image.largeImageURL)
            .placeholder(R.color.loadcolor)
            .override(1000, 1000)
            .into(viewHolder.imagePreview)

    }

    override fun getItemCount(): Int {
        return image.size
    }

    class ItemAdapterViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imagePreview = view.findViewById<ImageView>(R.id.imagePreview)

    }

}