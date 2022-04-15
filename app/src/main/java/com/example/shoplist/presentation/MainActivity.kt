package com.example.shoplist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem
import com.google.android.material.textview.MaterialTextView
import org.w3c.dom.Text
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapterRV : ShopItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setupRecyclerView()
        viewModel.shopList.observe(this){
            adapterRV.submitList(it)
        }
    }


    private fun setupRecyclerView(){
        val rvShopList = findViewById<RecyclerView>(R.id.recyclerViewShopList)
        adapterRV = ShopItemAdapter()
        with(rvShopList) {
            adapter = adapterRV
           recycledViewPool.setMaxRecycledViews(
                ShopItemAdapter.VIEW_TYPE_ENABLED,
                ShopItemAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopItemAdapter.VIEW_TYPE_DISABLED,
                ShopItemAdapter.MAX_POOL_SIZE
            )
        }
        setupOnLongClickListener()
        setupOnClickListener()
        setupOnSwiped(rvShopList)
    }

    private fun setupOnSwiped(rvShopList: RecyclerView?) {
        val myCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapterRV.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
        }
        val myHelper = ItemTouchHelper(myCallback)
        myHelper.attachToRecyclerView(rvShopList)
    }

    private fun setupOnClickListener() {
        adapterRV.onShopItemClickListener = {
            Log.d("Test", it.toString())
        }
    }

    private fun setupOnLongClickListener() {
        adapterRV.onShopItemLongClickListener = {
            viewModel.editShopItem(it)
        }
    }

}