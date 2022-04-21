package com.example.shoplist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textview.MaterialTextView
import org.w3c.dom.Text
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapterRV: ShopItemAdapter
    private var shopItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shopItemContainer = findViewById(R.id.shop_item_fragment_container)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setupRecyclerView()
        viewModel.shopList.observe(this) {
            adapterRV.submitList(it)
        }
        val buttonAddItem = findViewById<FloatingActionButton>(R.id.button_add_item)
        buttonAddItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            }else{
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }
    }

    private fun isOnePaneMode(): Boolean {
        return shopItemContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .add(R.id.shop_item_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupRecyclerView() {
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
            if(isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            }else{
                launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
            }
        }
    }

    private fun setupOnLongClickListener() {
        adapterRV.onShopItemLongClickListener = {
            viewModel.editShopItem(it)
        }
    }

}