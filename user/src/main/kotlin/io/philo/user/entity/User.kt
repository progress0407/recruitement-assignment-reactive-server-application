package io.philo.user.entity

import io.philo.entity.BaseEntity
import io.philo.support.PasswordEncoder
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("users")
class User private constructor(

    @Id var id: Long? = null,
    var email: String = "",
    val name: String,
    var password: String = "" // encoded Password
): BaseEntity() {

    companion object {}

    constructor(
        email: String,
        name: String,
        rawPassword: String,
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
