package io.philo.shop.entity

import org.springframework.data.annotation.Id

open class Item protected constructor(

    @Id
    open var id: Long? = null,
    open var name: String = "",
    open var price: Int = 0,
    open var stockQuantity: Int,
    open var userId: Long,
) {

    companion object {}

    protected constructor() : this(id = null, name = "", price = 0, stockQuantity = 0, userId = 0)

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
        return "Item(id=$id, name='$name', price=$price, stockQuantity=$stockQuantity)"
    }
}
