package io.philo.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

abstract class BaseEntity {

    @CreatedDate lateinit var createdAt: LocalDateTime

    @LastModifiedDate lateinit var lastModifiedAt: LocalDateTime
}