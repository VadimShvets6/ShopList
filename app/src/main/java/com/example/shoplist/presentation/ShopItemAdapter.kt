package com.example.shoplist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem
import com.google.android.material.textview.MaterialTextView

class ShopItemAdapter : RecyclerView.Adapter<ShopItemAdapter.ShopItemViewHolder>() {

    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_shop,
            parent,
            false
        )
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val item = shopList[position]
        var status = if(item.enable){
            "Active"
        } else {
            "Not active"
        }
        holder.view.setOnLongClickListener {
            true
        }
        if(item.enable){
            holder.tvName.text = "${item.name} $status"
            holder.tvCount.text = item.count.toString()
            holder.tvName.setTextColor(ContextCompat.getColor(holder.view.context, android.R.color.holo_red_dark))
        }
    }

    override fun onViewRecycled(holder: ShopItemViewHolder) {
        super.onViewRecycled(holder)
        holder.tvName.text = ""
        holder.tvCount.text = ""
        holder.tvName.setTextColor(ContextCompat.getColor(holder.view.context, android.R.color.white))
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<MaterialTextView>(R.id.textViewNameItem)
        val tvCount = view.findViewById<MaterialTextView>(R.id.textViewItemCount)
    }
}