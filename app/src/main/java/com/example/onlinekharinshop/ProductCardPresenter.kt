package com.example.onlinekharinshop

class ProductCardPresenter(private val activity: ProductCardActivity) {
    private val productCard = ProductCard(activity)
    private val priceValidator = PriceValidatorImpl()

//    mutableListOf(
//    ProductWithCounter(Product(price = 15.45675, name = "Boob_lick", description = "Вкусный БубЛик")),
//    ProductWithCounter(Product(price = 20.989895, name = "Korjic")),
//    ProductWithCounter(Product(price = 100.42121, name = "Yogurt", discount = 10)),
//    ProductWithCounter(Product(price = 5.5432, name = "Hleb", discount = 5)),
//    ProductWithCounter(Product(price = 3.33, name = "Goroh")),
//    ProductWithCounter(Product(price = 0.00000, name = "Voda")),
//    )

    fun addToCard(product: ProductWithCounter) {
        productCard.addProductToCart(product)
        activity.notifyProductCarChanged()
    }

    fun deleteFromCard(product: ProductWithCounter) {
        productCard.deleteProductFromCart(product)
        activity.notifyProductCarChanged()
        checkProductCounterZero(product)
    }

    fun deleteAllProducts() {
        productCard.deleteAllProductsFromCard()
        activity.setItems()
    }

    fun getAllPrice(): String {
        val products = productCard.getAllCardProducts()
        var totalPrice = 0.0
        for (product in products) {
            totalPrice += priceValidator.discountPrice(product.first.price, product.first.discount) * product.second
        }

        return priceValidator.validatePrice(priceValidator.discountPrice(totalPrice))
    }

    private fun checkProductCounterZero(productWithCounter: ProductWithCounter) {
        if (productWithCounter.counter - 1 <= 0) {
            activity.setItems()
        }
    }

    fun getCardItems(): List<ProductWithCounter> {
        val list = mutableListOf<ProductWithCounter>()
        for (pair in productCard.getAllCardProducts()) {
            list.add(ProductWithCounter(pair.first, pair.second))
        }
        return list
    }
}