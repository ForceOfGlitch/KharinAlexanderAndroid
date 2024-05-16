package com.example.onlinekharinshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val presenter = Presenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editTextPhone = findViewById<EditText>(R.id.orderUserNum)
        val buttonAccept = findViewById<Button>(R.id.orderConfirmBtn)
        buttonAccept.setOnClickListener {
            Toast.makeText(applicationContext, "Телефон: " + editTextPhone.text,
                Toast.LENGTH_LONG).show()
        }

    }

    override fun onResume() {
        super.onResume()
        typeCartInfo()
    }

    fun typeCartInfo() {
        findViewById<TextView>(R.id.cartSumVal).text = presenter.getCartTotalCost()
    }

    fun typeProductInfo(infoMessage: String) {
        findViewById<TextView>(R.id.textView).text = infoMessage
    }


}