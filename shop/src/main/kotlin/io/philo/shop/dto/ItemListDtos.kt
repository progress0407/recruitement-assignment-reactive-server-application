package io.philo.shop.dto

import io.philo.shop.entity.Item

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ItemListResponse

        if (name != other.name) return false
        if (price != other.price) return false
        if (availableQuantity != other.availableQuantity) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + price
        result = 31 * result + availableQuantity
        return result
    }
}
