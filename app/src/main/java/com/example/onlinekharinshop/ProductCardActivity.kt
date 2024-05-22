package com.example.onlinekharinshop

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.onlinekharinshop.databinding.ActivityProductCardBinding
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class ProductCardActivity : AppCompatActivity(), CatalogAdapter.CatalogListener {

    private lateinit var binding: ActivityProductCardBinding
    private val presenter by lazy { ProductCardPresenter(this) }
    private val adapter: CatalogAdapter by lazy { CatalogAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
    }

    private fun initUi() {
        binding.tvAllPrice.text = "Итоговая цена: ${presenter.getAllPrice()}"

        binding.topAppBar.setNavigationOnClickListener {
            val intents = Intent(this@ProductCardActivity, CatalogActivity::class.java)
            intents.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        or Intent.FLAG_ACTIVITY_CLEAR_TASK
            )
            startActivity(intents)
            finish()
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.order -> navigateToOrder()
                R.id.delete -> deleteAllProducts()
            }
            true
        }

        binding.productList.adapter = adapter
        adapter.setItems(presenter.getCardItems())
        adapter.addListener(this)
    }

    fun notifyProductCarChanged() {
        binding.tvAllPrice.text = "Итоговая цена: ${presenter.getAllPrice()}"
    }

    private fun navigateToOrder() {
        val intent = Intent(this@ProductCardActivity, OrderActivity::class.java)
        intent.putExtra(KEY_ALL_PRICE, presenter.getAllPrice())
        startActivity(intent)
    }

    private fun deleteAllProducts() {
        presenter.deleteAllProducts()
    }

    fun setItems() {
        adapter.setItems(presenter.getCardItems())
    }

    override fun addToProductCard(product: ProductWithCounter) {
        presenter.addToCard(product)
    }

    override fun deleteFromProductCard(product: ProductWithCounter) {
        presenter.deleteFromCard(product)
    }

    override fun productClicked(product: Product) {
        val intent = Intent(this@ProductCardActivity, ProductInfoActivity::class.java)
        intent.putExtra(CatalogActivity.KEY_PRODUCT_INFO, Json.encodeToString(product))
        startActivity(intent)
    }

    companion object {
        const val KEY_ALL_PRICE = "KEY_ALL_PRICE"
        const val KEY_ALL_PRICE_WITHOUT_DISCOUNT = "KEY_ALL_PRICE_WITHOUT_DISCOUNT"
    }
}