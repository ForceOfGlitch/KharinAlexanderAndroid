package com.example.onlinekharinshop

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

data class ProductCard(val activity: AppCompatActivity) {

    private val sharedPref = activity.getSharedPreferences(
        activity.getString(R.string.product_card_sp), Context.MODE_PRIVATE
    )
    // fun getCartTotalCost() = productList.sumOf { it.product.price - (it.product.price * it.product.discount / 100) }

    // fun getProductList() = productList.toList()

    // fun getProductByName(name: String) = productList.find { it.product.name == name }

    fun addProductToCart(productWithCounter: ProductWithCounter){
        val objjsonstr = Json.encodeToString(productWithCounter.product)
        val count = sharedPref.getInt(objjsonstr, 0)

        with(sharedPref.edit()) {
            putInt(objjsonstr, count + 1)
            apply()
        }
    }

    fun deleteAllProductsFromCard(){
        sharedPref.edit().clear().apply()
    }

    fun deleteProductFromCart(productWithCounter: ProductWithCounter) {
        val objjsonstr = Json.encodeToString(productWithCounter.product)
        val count = sharedPref.getInt(objjsonstr, 0)
        with(sharedPref.edit()) {
            putInt(objjsonstr, if (count <= 0) 0 else count - 1)
            apply()
        }

        if(sharedPref.getInt(objjsonstr,0) <= 0){
            sharedPref.edit().remove(objjsonstr).apply()
        }
    }

    fun getAllCardProducts() : List<Pair<Product, Int>>{
      //  sharedPref.edit().clear().apply()
        return sharedPref.all.map {
            val key = it.key
            val value = it.value as Int
            Pair(Json.decodeFromString(key), value)
        }
    }
}