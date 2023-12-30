package io.philo.user.entity

import io.philo.support.PasswordEncoder
import org.springframework.data.annotation.Id

//@Table("users")
open class Users protected constructor(
    @Id
    val id: Long? = null,
    var email: String = "",
    val name: String,
    var password: String = "" // encoded Password
) {

    protected constructor() : this(email = "", name = "", password = "")

    constructor(
        email: String,
        name: String,
        rawPassword: String
    ) :
            this(
                email = email,
                name = name,
                password = PasswordEncoder.encodePassword(rawPassword)
            )

    fun isSamePassword(rawPassword: String?): Boolean {
        return PasswordEncoder.isSamePassword(rawPassword, password)
    }

    override fun toString(): String {
        return "Users(id=$id, email='$email', name='$name')"
    }
}
