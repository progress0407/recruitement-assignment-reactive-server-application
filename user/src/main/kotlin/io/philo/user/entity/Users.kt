package io.philo.user.entity

import org.springframework.data.annotation.Id

//@Table("users")
class Users(val name: String) {

    @Id
    var id: Long? = null

    override fun toString(): String {
        return "Customer(name='$name', id=$id)"
    }
}
