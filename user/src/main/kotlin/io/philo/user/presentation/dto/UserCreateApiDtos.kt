package io.philo.user.presentation.dto

class UserCreateApiDtos

data class UserCreateResponse(val id: Long)

data class UserCreateRequest(val name: String)