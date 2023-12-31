package io.philo.shop.presentation

import io.philo.dto.ResourceCreateResponse
import io.philo.shop.application.ItemService
import io.philo.shop.dto.ItemCreateRequest
import io.philo.shop.dto.ItemListResponse
import io.philo.shop.dto.ItemUpdateRequest
import mu.KotlinLogging
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RequestMapping("/items")
@RestController
class ItemController(private val itemService: ItemService) {

    private val log = KotlinLogging.logger { }

    /**
     * 상품 등록
     */
    @PostMapping
    @ResponseStatus(CREATED)
    fun add(
        @RequestBody request: ItemCreateRequest,
        @RequestHeader("userId") userId: String,
    ): Mono<ResourceCreateResponse> {

        val (name, price, stockQuantity) = request
        val itemId = itemService.registerItem(name, price, stockQuantity, userId.toLong())
        return itemId.map { ResourceCreateResponse(it) }
    }

    @GetMapping
    fun list(): Flux<ItemListResponse> { // todo 페이징!

        val responses = itemService.findItems().map { ItemListResponse(it) }

        return responses
    }

    @PutMapping("/{itemId}")
    fun update(
        @PathVariable itemId: Long,
        @RequestBody request: ItemUpdateRequest,
        @RequestHeader("userId") userId: String,
    ): Mono<Void> {

        val (name, price, stockQuantity) = request
        return itemService.update(itemId, name, price, stockQuantity)
    }

    @DeleteMapping("/{itemId}")
    fun delete(
        @PathVariable itemId: Long,
        @RequestHeader("userId") userId: String
    ): Mono<Void> {

        return itemService.delete(itemId)
    }

}