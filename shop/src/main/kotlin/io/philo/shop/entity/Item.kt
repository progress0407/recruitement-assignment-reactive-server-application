package io.philo.shop.entity

import org.springframework.data.annotation.Id

class Item private constructor(

    @Id
    var id: Long? = null,
    var name: String = "",
    var price: Int = 0,
    var stockQuantity: Int,
    var userId: Long
) {

    companion object {}

    constructor(
        name: String,
        price: Int,
        stockQuantity: Int,
        userId: Long
    ) : this(null, name, price, stockQuantity, userId)

    fun decreaseStockQuantity(orderQuantity: Int) {
        validateCanDecrease(orderQuantity)
        stockQuantity -= orderQuantity
    }

    private fun validateCanDecrease(orderQuantity: Int) {
        check(stockQuantity - orderQuantity >= 0) {
            "주문수량에 비해 상품의 재고수량이 충분하지 않습니다."
        }
    }

    override fun toString(): String {
        return "Item(name='$name', price=$price, stockQuantity=$stockQuantity, userId=$userId)"
    }
}
