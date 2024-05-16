package com.example.onlinekharinshop

class Presenter(private val activity: MainActivity) {
    val priceValidator = PriceValidatorImpl()
    private val productCard = ProductCard(
        mutableListOf(
            Product(15.45675, "Boob_lick"),
            Product(20.989895, "Korjic"),
            Product(100.42121, "Yogurt", 10),
            Product(5.5432, "Hleb", 5),
            Product(3.33, "Goroh"),
            Product(0.00000, "Voda"),
        )
    )

    fun productInfoPrint(productName : String) {
        val product = productCard.getProductByName(productName)
        activity.typeProductInfo(if (product == null) "Продукта с именем $productName не найдено" else product.name + " " + priceValidator.validatePrice(product.price))
    }

    fun cartInfoPrint(): String {
        var result = productCard.getProductList().joinToString("\n") { "${it.name} : ${priceValidator.validatePrice(priceValidator.discountPrice(it.price, it.discount))}" }
        result += "\n\nОбщая стоимость корзины: ${priceValidator.validatePrice(productCard.getCartTotalCost())}"
        return result
    }

    fun getCartTotalCost(): String {
        return priceValidator.validatePrice(productCard.getCartTotalCost())
    }
}