package io.philo.user.exception

import io.philo.shop.error.NotFoundException

class EntityNotFoundException(
    condition: Any,
    cause: Throwable?
) :
    NotFoundException("엔티티를 찾을 수 없습니다: $condition", cause) {

    constructor(condition: Any) : this(condition, null)
}