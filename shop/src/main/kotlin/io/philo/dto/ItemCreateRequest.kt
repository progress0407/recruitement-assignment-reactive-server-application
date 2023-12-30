package io.philo.dto

data class ItemCreateRequest(
    val name: String = "",
    val price: Int = 0,
    val stockQuantity: Int = 0
) {
    companion object
}
