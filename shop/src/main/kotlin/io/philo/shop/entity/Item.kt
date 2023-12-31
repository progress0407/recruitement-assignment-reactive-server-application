package io.philo.shop.entity

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

class Item private constructor(

    @Id
    var id: Long? = null,
    var name: String = "",
    var price: Int = 0,
    var stockQuantity: Int,
    var userId: Long,
) {
    var createdAt: LocalDateTime = LocalDateTime.now()
    @LastModifiedDate  lateinit var lastModifiedAt: LocalDateTime

    companion object {}

    constructor(
        name: String,
        price: Int,
        stockQuantity: Int,
        userId: Long,
    ) : this(null, name, price, stockQuantity, userId)

    fun updateInfo(name: String, price: Int, stockQuantity: Int) {
        this.name = name
        this.price = price
        this.stockQuantity = stockQuantity
    }

    override fun toString(): String {
        return "Item(name='$name', price=$price, stockQuantity=$stockQuantity, userId=$userId)"
    }
}
