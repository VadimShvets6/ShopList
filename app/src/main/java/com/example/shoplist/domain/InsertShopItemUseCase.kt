package com.example.shoplist.domain

class InsertShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun insertShopItem(shopItem : ShopItem) {
        shopListRepository.insertShopItem(shopItem)
    }
}