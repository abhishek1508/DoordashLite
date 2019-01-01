package com.app.doordashlite.restaurants.repo.entity.local

enum class StatusType(var statusType: String) {
    STATUS_ORDER_ADVANCE("order-advance"),
    STATUS_PREORDER("pre-order"),
    STATUS_OPEN("open")
}