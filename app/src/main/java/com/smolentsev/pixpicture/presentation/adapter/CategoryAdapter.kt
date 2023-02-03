package com.smolentsev.pixpicture.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smolentsev.pixpicture.R
import com.smolentsev.pixpicture.domain.entity.Category
import com.smolentsev.pixpicture.domain.CategoryImageSet

class CategoryAdapter(): RecyclerView.Adapter<CategoryAdapter.ItemAdapterViewHolder>() {
    var category = listOf<Category>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onShopItemClickListener: ((Category) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_adapter,parent,false)
        return ItemAdapterViewHolder(view)

    }

    override fun onBindViewHolder(viewHolder: ItemAdapterViewHolder, position: Int) {
        val _itemList = category[position]

        viewHolder.view.setOnClickListener{
            onShopItemClickListener?.invoke(_itemList)
        }
        viewHolder.viewTitle.text = _itemList.name
        viewHolder.image.setImageResource(CategoryImageSet.imageSet(_itemList.id))

    }

    override fun getItemCount(): Int {
        return category.size
    }

    class ItemAdapterViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val viewTitle = view.findViewById<TextView>(R.id.category_text)
        val image = view.findViewById<ImageView>(R.id.imageBg)

    }
}