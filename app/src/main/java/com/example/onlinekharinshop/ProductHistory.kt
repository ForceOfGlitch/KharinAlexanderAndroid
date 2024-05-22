package com.example.onlinekharinshop

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ProductHistory(private val activity: AppCompatActivity) {

    private val sharedPref = activity.getSharedPreferences(
        activity.getString(R.string.product_history_sp), Context.MODE_PRIVATE
    )

    fun getAllProductsHistory(): List<Product> {
        return sharedPref.getStringSet("STRING_SET", setOf())!!.map { str ->
            Json.decodeFromString(str)
        }
    }

    fun addProductToCart(product: Product) {
        val sets = sharedPref.getStringSet("STRING_SET", setOf())?.toMutableSet() ?: mutableSetOf()
        sets.add(Json.encodeToString(product))
        sharedPref.edit().putStringSet("STRING_SET", sets).apply()
    }
}