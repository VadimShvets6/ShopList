package com.example.shoplist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    fun insertShopItem(shopItem : ShopItem)

    fun deleteShopItem(shopItem : ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopList() : LiveData<List<ShopItem>>

    fun getShopItem(shopItemId : Int) : ShopItem
}