package com.example.onlinekharinshop

interface PriceValidator {
    fun validatePrice(price: Double): String
    fun discountPrice(price: Double, discount: Int=0): Double
}