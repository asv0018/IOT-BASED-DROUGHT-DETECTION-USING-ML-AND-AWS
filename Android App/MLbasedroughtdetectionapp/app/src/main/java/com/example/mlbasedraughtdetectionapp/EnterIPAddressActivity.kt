package com.example.mlbasedraughtdetectionapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class EnterIPAddressActivity : AppCompatActivity() {
    companion object{
        lateinit var ip_address:String
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_ipaddress)

        var get_ip_address = findViewById<EditText>(R.id.ip_address_input)
        var connect_to_aws_btn = findViewById<Button>(R.id.connect_to_aws_btn)

        connect_to_aws_btn.setOnClickListener {
            ip_address = get_ip_address.text.toString()
            if(!ip_address.isNullOrEmpty()){
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
}