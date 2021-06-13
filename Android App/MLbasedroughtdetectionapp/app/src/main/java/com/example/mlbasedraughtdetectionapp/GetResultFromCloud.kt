package com.example.mlbasedraughtdetectionapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mlbasedraughtdetectionapp.MainActivity.Companion.host_url
import com.github.kittinunf.fuel.Fuel
import org.json.JSONObject

class GetResultFromCloud : AppCompatActivity() {
    var temp:String = "---"
    var hum:String = "---"
    var moist:String = "---"
    var res:String = "---"
    var accu:String = "---"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_result_from_cloud)

        var temperature_view = findViewById<TextView>(R.id.temp_view)
        var humidity_view = findViewById<TextView>(R.id.humididty)
        var soil_moisture_view = findViewById<TextView>(R.id.soil_moisture)
        var result_textview = findViewById<TextView>(R.id.result_status)
        var accuracy_view = findViewById<TextView>(R.id.accuracy_result)
        var test_button = findViewById<Button>(R.id.test_now_btn)
        var progressBar = findViewById<ProgressBar>(R.id.progressBar)

        test_button.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            Fuel.get("$host_url/access_ml_result")
                .response { request, response, result ->
                    Log.d("http", "request is : ${request.toString()}")
                    Log.d("http","Response is : ${response.toString()}")
                    val (bytes, error) = result
                    Log.d("http", "ERROR IS $error")
                    if (bytes != null) {
                        Log.d("http","[response bytes] ${String(bytes)}")
                        val jsonObject = JSONObject(String(bytes))

                        res = jsonObject.getString("condition")
                        temp = jsonObject.getString("temperature")
                        moist = jsonObject.getString("moisture")
                        hum = jsonObject.getString("humidity")
                        accu = jsonObject.getString("accuracy")
                        Log.d("http","Data is ${jsonObject.optString("condition")}")
                        progressBar.visibility = View.INVISIBLE
                    }
                }
            if(res=="Device is unavailable"){
                temperature_view.text = "---"
                humidity_view.text = "---"
                soil_moisture_view.text = "---"
                result_textview.text = "---"
                accuracy_view.text = "---"
            }else{
                temperature_view.text = temp
                humidity_view.text = hum
                soil_moisture_view.text = moist
                result_textview.text = res
                accuracy_view.text = "Accuracy : $accu"
            }
        }
    }
}