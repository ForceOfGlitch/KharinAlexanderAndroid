package com.example.onlinekharinshop

import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinekharinshop.databinding.ItemCatalogBinding

class CatalogAdapter :
    RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder>() {
    private val items = mutableListOf<ProductWithCounter>()
    var catalogListener: CatalogListener? = null

    inner class CatalogViewHolder(private val binding: ItemCatalogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val priceValidator = PriceValidatorImpl()
        fun bind(item: ProductWithCounter) {
            binding.productName.text = item.product.name
            binding.productCounter.text = item.counter.toString()

            if (item.product.discount > 0) {
                val price = item.product.price
                val priceWithDiscount = priceValidator.validatePrice(
                    priceValidator.discountPrice(
                        item.product.price,
                        item.product.discount
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
                        item.product.price,
                        item.product.discount
                    )
                )
            }
            binding.addFromCardBtn.setOnClickListener {
                catalogListener?.addToProductCard(item)
                binding.productCounter.text = binding.root.context.getString(
                    R.string.product_counter,
                    binding.productCounter.text.toString().toInt() + 1
                )
            }

            binding.removeFromCardBtn.setOnClickListener {
                val index = items.indexOf(item)
                val curItem = items.getOrNull(index)
                curItem?.let { items[index].counter = items[index].counter - 1 }

                catalogListener?.deleteFromProductCard(item)
                binding.productCounter.text = binding.root.context.getString(
                    R.string.product_counter,
                    when (val newCount = binding.productCounter.text.toString().toInt()) {
                        in Integer.MIN_VALUE..0 -> 0
                        else -> newCount - 1
                    }
                )
            }

            binding.root.setOnClickListener {
                catalogListener?.productClicked(product = item.product)
            }
        }
    }

    fun addListener(listener: CatalogListener) {
        catalogListener = listener
    }

    fun setItems(items: List<ProductWithCounter>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    interface CatalogListener {
        fun addToProductCard(product: ProductWithCounter)
        fun deleteFromProductCard(product: ProductWithCounter)

        fun productClicked(product: Product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        return CatalogViewHolder(
            ItemCatalogBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        holder.bind(items[position])
    }
}