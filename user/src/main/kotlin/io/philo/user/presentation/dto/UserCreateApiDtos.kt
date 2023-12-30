package io.philo.user.presentation.dto

class UserCreateApiDtos

data class UserCreateRequest(val email: String, val name: String, val password: String)

data class UserCreateResponse(val id: Long)
