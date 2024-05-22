package com.example.onlinekharinshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.example.onlinekharinshop.databinding.ActivityCatalogBinding
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CatalogActivity : AppCompatActivity(), CatalogAdapter.CatalogListener,
    ProductHistoryAdapter.ProductHistoryListener {
    private lateinit var binding: ActivityCatalogBinding
    private val catalogPresenter: CatalogPresenter by lazy { CatalogPresenter(this) }
    private val catalogAdapter: CatalogAdapter by lazy { CatalogAdapter() }
    private val historyAdapter: ProductHistoryAdapter by lazy { ProductHistoryAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        initUI()
    }

    fun initUI() {
        binding.topAppBar.setOnMenuItemClickListener {
            val intent = Intent(this@CatalogActivity, ProductCardActivity::class.java)
            startActivity(intent)
            true
        }
        binding.productList.adapter = catalogAdapter
        catalogAdapter.setItems(catalogPresenter.getAllItems())
        catalogAdapter.addListener(this)

        val sets = catalogPresenter.getProductsHistory()
        binding.tvHistory.isVisible = sets.isNotEmpty()

        binding.historyProductList.adapter = historyAdapter
        historyAdapter.setItems(catalogPresenter.getProductsHistory())
        historyAdapter.addListener(this)
    }

    override fun addToProductCard(product: ProductWithCounter) {
        catalogPresenter.addToCard(product)
    }

    override fun deleteFromProductCard(product: ProductWithCounter) {
        catalogPresenter.deleteFromCard(product)
    }

    override fun productClicked(product: Product) {
        catalogPresenter.addToHistory(product)
        val intent = Intent(this@CatalogActivity, ProductInfoActivity::class.java)
        intent.putExtra(KEY_PRODUCT_INFO, Json.encodeToString(product))
        startActivity(intent)
    }

    companion object {
        const val KEY_PRODUCT_INFO = "KEY_PRODUCT_INFO"
    }

    override fun historyProductClicked(product: Product) {

    }
}