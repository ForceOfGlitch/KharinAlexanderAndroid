package com.example.onlinekharinshop

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Product(
    val id: String,
    val price: Double,
    val name: String,
    val description: String = "",
    val discount: Int = 0
)