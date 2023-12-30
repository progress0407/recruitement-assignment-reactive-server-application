package io.philo.dto

import io.philo.entity.Item

class ItemListApiDtos

data class ItemListResponses(
    val items: List<ItemListResponse> = emptyList()
)

data class ItemListResponse(
    val id: Long,
    val name: String,
    val price: Int,
    val availableQuantity: Int
) {
    constructor(item: Item) : this(item.id!!, item.name, item.price, item.stockQuantity)
}
