package io.philo.user.entity

import io.philo.support.PasswordEncoder
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("users")
class User private constructor(

    @Id var id: Long? = null,
    var email: String = "",
    val name: String,
    var password: String = "", // encoded Password
) {

    var createdAt: LocalDateTime = LocalDateTime.now()
    @LastModifiedDate lateinit var lastModifiedAt: LocalDateTime

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
