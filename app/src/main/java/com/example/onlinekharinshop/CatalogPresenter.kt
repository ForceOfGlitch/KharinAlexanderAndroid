package com.example.onlinekharinshop

class CatalogPresenter(activity: CatalogActivity) {

    private val productCard = ProductCard(activity)
    private val productHistory = ProductHistory(activity) //

    fun addToCard(product: ProductWithCounter) {
        productCard.addProductToCart(product)
    }

    fun addToHistory(product: Product) = productHistory.addProductToCart(product)

    fun getProductsHistory(): List<Product> = productHistory.getAllProductsHistory()

    fun deleteFromCard(product: ProductWithCounter) {
        productCard.deleteProductFromCart(product)
    }

    fun getAllItems(): List<ProductWithCounter> {
        val listCardProducts = productCard.getAllCardProducts()

        return ProductStore.list.map { product ->
            val curCounter = listCardProducts.find { it.first.id == product.id }?.second ?: 0
            ProductWithCounter(product = product, counter = curCounter)
        }
    }
}