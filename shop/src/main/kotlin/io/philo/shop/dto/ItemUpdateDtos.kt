package io.philo.shop.dto

class ItemUpdateDtos

data class ItemUpdateRequest(
    val name: String = "",
    val price: Int = 0,
    val stockQuantity: Int = 0,
) {
    companion object
}
