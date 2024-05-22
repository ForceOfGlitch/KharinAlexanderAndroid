package com.example.onlinekharinshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.onlinekharinshop.CatalogActivity.Companion.KEY_PRODUCT_INFO
import com.example.onlinekharinshop.databinding.ActivityProductInfoBinding
import kotlinx.serialization.json.Json

class ProductInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getStringExtra(KEY_PRODUCT_INFO)?.let { str ->
            val product = Json.decodeFromString<Product>(str)
            initUi(product)
        }
    }

    private fun initUi(product: Product) {
        binding.topAppBar.setNavigationOnClickListener {
            val intents = Intent(this@ProductInfoActivity, CatalogActivity::class.java)
            intents.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        or Intent.FLAG_ACTIVITY_CLEAR_TASK
            )
            startActivity(intents)
            finish()        }

        with(product) {
            val priceValidator = PriceValidatorImpl()

            binding.productInfoName.text = name
            binding.productInfoPrice.text = getString(
                R.string.product_price_title,
                priceValidator.validatePrice(priceValidator.discountPrice(price, discount))
            )
            binding.productInfoDescription.text = description
        }
    }
}