package com.example.onlinekharinshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.onlinekharinshop.ProductCardActivity.Companion.KEY_ALL_PRICE
import com.example.onlinekharinshop.ProductCardActivity.Companion.KEY_ALL_PRICE_WITHOUT_DISCOUNT
import com.example.onlinekharinshop.databinding.ActivityOrderBinding

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    private val priceValidator = PriceValidatorImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        binding.orderConfirmBtn.isEnabled = false
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        initListeners()
    }

    fun initListeners() {
        binding.orderConfirmBtn.setOnClickListener {
            Toast.makeText(
                applicationContext, "Телефон: " + binding.orderUserNum.text,
                Toast.LENGTH_LONG
            ).show()
        }

        binding.topAppBar.setNavigationOnClickListener {
            val intents = Intent(this@OrderActivity, CatalogActivity::class.java)
            intents.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        or Intent.FLAG_ACTIVITY_CLEAR_TASK
            )
            startActivity(intents)
            finish()
        }

        val allPrice = intent.getStringExtra(KEY_ALL_PRICE)
        val allWithoutDiscountPrice = intent.getStringExtra(KEY_ALL_PRICE_WITHOUT_DISCOUNT)
        binding.cartSumVal.text = allPrice
        binding.cartSumValDiscount.text = priceValidator.validatePrice(
            allWithoutDiscountPrice!!.toDouble() - allPrice!!.toDouble()
        )
        binding.cartSumValWithoutDiscount.text = allWithoutDiscountPrice

        binding.orderUserNum.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.orderUserNum.text.length == 11 && binding.orderUserNum.text[0] == '8') {
                    binding.orderUserNum.error = null
                    binding.orderConfirmBtn.isEnabled = true
                } else {
                    binding.orderUserNum.error = "Номер введён неправильно"
                    binding.orderConfirmBtn.isEnabled = false
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        )
    }

}
