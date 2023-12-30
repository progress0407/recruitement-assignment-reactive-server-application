package io.philo.entity

import org.springframework.data.annotation.Id

class Item protected constructor(

    @Id
    val id: Long? = null,
    var name: String = "",
    var price: Int = 0,
    var stockQuantity: Int
) {


    // default constructor for using JPA
    protected constructor() : this(id = null, name = "", price = 0, stockQuantity = 0)

    // Size가 존재하지 않는 경우
    constructor(
        name: String,
        price: Int,
        stockQuantity: Int
    ) : this(id = null, name, price, stockQuantity)

    companion object

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
