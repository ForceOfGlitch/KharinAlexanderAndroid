package com.example.onlinekharinshop

data class ProductCard(private val productList: MutableList<Product>) {
    fun getCartTotalCost() = productList.sumOf { it.price - (it.price * it.discount / 100) }

    fun getProductList() = productList.toList()

    fun getProductByName(name: String) = productList.find { it.name == name }

    fun addProductToCart(product: Product) = productList.add(product)

    fun deleteProductFromCart() = productList.removeLast()

    fun deleteProductFromCart(name: String) = productList.removeIf { it.name == name }
}