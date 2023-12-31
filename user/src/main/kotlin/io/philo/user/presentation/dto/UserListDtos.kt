package io.philo.user.presentation.dto

import io.philo.user.entity.User
import java.time.LocalDateTime

class UserListDtos

data class UserListResponse(
    val id: Long,
    val email: String,
    val name: String,
    val createdAt: LocalDateTime,
    val lastModifiedAt: LocalDateTime
) {
    constructor(user: User) : this(user.id!!, user.email, user.name, user.createdAt, user.lastModifiedAt)
}
