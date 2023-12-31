package io.philo.exception.constant

class EntityNotFoundException(entityInfo: String = "", cause: Throwable? = null) :
    NotFoundException("엔티티를 찾을 수 없습니다: ${entityInfo}", cause)