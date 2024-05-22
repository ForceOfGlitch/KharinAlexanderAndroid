package com.example.onlinekharinshop

import kotlinx.serialization.Serializable

@Serializable
data class ProductWithCounter(val product: Product, var counter: Int = 0)

// select count(*), pc.id from product_card pc group by pc.id