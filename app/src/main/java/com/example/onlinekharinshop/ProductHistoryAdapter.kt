package com.example.onlinekharinshop

import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinekharinshop.databinding.ItemCatalogBinding
import com.example.onlinekharinshop.databinding.ItemProductHistoryBinding

class ProductHistoryAdapter : RecyclerView.Adapter<ProductHistoryAdapter.ProductHistoryViewHolder>() {
    private val items = mutableListOf<Product>()
    var catalogListener: ProductHistoryListener? = null

    inner class ProductHistoryViewHolder(private val binding: ItemProductHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val priceValidator = PriceValidatorImpl()
        fun bind(item: Product) {
            binding.productName.text = item.name

            if (item.discount > 0) {
                val price = item.price
                val priceWithDiscount = priceValidator.validatePrice(
                    priceValidator.discountPrice(
                        item.price,
                        item.discount
                    )
                )
                val spannable = SpannableString("Цена: $price $priceWithDiscount").apply {
                    setSpan(
                        StrikethroughSpan(),
                        indexOf(price.toString()),
                        indexOf(price.toString()) + price.toString().length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }

                binding.productSumText.text = spannable

            } else {
                binding.productSumText.text = priceValidator.validatePrice(
                    priceValidator.discountPrice(
                        item.price,
                        item.discount
                    )
                )
            }

            binding.root.setOnClickListener {
                catalogListener?.historyProductClicked(product = item)
            }
        }
    }

    fun addListener(listener: ProductHistoryListener) {
        catalogListener = listener
    }

    fun setItems(items: List<Product>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    interface ProductHistoryListener {
        fun historyProductClicked(product: Product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHistoryViewHolder {
        return ProductHistoryViewHolder(
            ItemProductHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ProductHistoryViewHolder, position: Int) {
        holder.bind(items[position])
    }
}