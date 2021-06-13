package com.example.mlbasedraughtdetectionapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.mlbasedraughtdetectionapp.EnterIPAddressActivity.Companion.ip_address

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var host_url:String
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        host_url = "http://$ip_address:8080"

        var real_test_btn = findViewById<Button>(R.id.real_test)
        var manual_model_test = findViewById<Button>(R.id.manual_test)

        real_test_btn.setOnClickListener {
            startActivity(Intent(this, GetResultFromCloud::class.java))
        }

        manual_model_test.setOnClickListener{
            startActivity(Intent(this, ManualtestModal::class.java))
        }
    }
}