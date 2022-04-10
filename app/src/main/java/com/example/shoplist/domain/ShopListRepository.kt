package com.example.shoplist.domain

interface ShopListRepository {

    fun insertShopItem(shopItem : ShopItem)

    fun deleteShopItem(shopItem : ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopList() : List<ShopItem>

    fun getShopItem(shopItemId : Int) : ShopItem
}