package io.philo.user.presentation.dto

class UserLoginApiDtos

data class UserLoginRequest(val id: String, val password: String) {

    companion object {}
}
