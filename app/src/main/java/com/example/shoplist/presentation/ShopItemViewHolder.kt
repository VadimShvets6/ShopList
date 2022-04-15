package com.example.shoplist.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.google.android.material.textview.MaterialTextView

class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val tvName: MaterialTextView = view.findViewById(R.id.textViewNameItem)
    val tvCount: MaterialTextView = view.findViewById(R.id.textViewItemCount)
}