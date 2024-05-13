package com.example.onlinekharinshop

class PriceValidatorImpl: PriceValidator {
    override fun validatePrice(price: Double): String {
        return if (price % 1 == 0.0) {
            price.toInt().toString()
        } else {
            "%.3f".format(price)
        }
    }

    override fun discountPrice(price: Double, discount: Int): Double {
        return if (discount > 0) price - (price * discount / 100) else price
    }
}