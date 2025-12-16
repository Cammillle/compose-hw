package com.example.cupcake.model

sealed class OrderEvent {
    data class SetQuantity(val quantity: Int) : OrderEvent()
    data class SetFlavor(val flavor: String) : OrderEvent()
    data class SetDate(val date: String) : OrderEvent()
    object CancelOrder : OrderEvent()
}