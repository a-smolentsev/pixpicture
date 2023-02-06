package com.smolentsev.pixpicture.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy.ALL
import com.bumptech.glide.load.engine.DiskCacheStrategy.RESOURCE
import com.smolentsev.pixpicture.R
import com.smolentsev.pixpicture.domain.entity.Hit

class ImageAllAdapter: RecyclerView.Adapter<ImageAllAdapter.ItemAdapterViewHolder>() {
    private val differCallback = object : DiffUtil.ItemCallback<Hit>(){
        override fun areItemsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            return oldItem.largeImageURL == newItem.largeImageURL
        }

        override fun areContentsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            return oldItem==newItem
        }

    }
    val differ = AsyncListDiffer(this,differCallback)

    var onImageClickListener: ((Hit) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.images_adapter, parent, false)
        return ItemAdapterViewHolder(view)

    }

    override fun onBindViewHolder(viewHolder: ItemAdapterViewHolder, position: Int) {
        val _image = differ.currentList[position]
        viewHolder.view.setOnClickListener {
            onImageClickListener?.invoke(_image)
        }
        Glide.with(viewHolder.itemView)
            .load(_image.largeImageURL)
            .diskCacheStrategy(ALL)
            .placeholder(R.color.loadcolor)
            .override(1000, 1000)
            .into(viewHolder.imagePreview)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class ItemAdapterViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imagePreview = view.findViewById<ImageView>(R.id.imagePreview)

    }

}