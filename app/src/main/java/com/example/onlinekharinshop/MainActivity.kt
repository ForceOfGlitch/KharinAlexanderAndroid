package com.example.onlinekharinshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val presenter = Presenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        typeCartInfo()
    }

    fun typeCartInfo() {
        val cartText = presenter.cartInfoPrint()
        findViewById<TextView>(R.id.textView).text = cartText
    }

    fun typeProductInfo(infoMessage: String) {
        findViewById<TextView>(R.id.textView).text = infoMessage
    }


}