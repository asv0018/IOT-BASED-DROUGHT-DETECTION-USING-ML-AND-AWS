package com.example.mlbasedraughtdetectionapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.example.mlbasedraughtdetectionapp.MainActivity.Companion.host_url
import com.github.kittinunf.fuel.Fuel
import com.google.gson.JsonObject
import org.json.JSONObject

class ManualtestModal : AppCompatActivity() {
    var res:String = "---"
    var accu:String = "---"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manualtest_modal)

        var get_temp_val = findViewById<EditText>(R.id.temperature_input)
        var get_humidity_val = findViewById<EditText>(R.id.humidity_input)
        var get_soil_moisture_val = findViewById<EditText>(R.id.soil_moisture_input)
        var obtain_results_button = findViewById<Button>(R.id.manual_test_now_button)
        var progressBar2 = findViewById<ProgressBar>(R.id.progressBar2)
        var accuracy_view = findViewById<TextView>(R.id.accuracy_result_manual_input)
        var result_view = findViewById<TextView>(R.id.result_status_manual_ip)

        obtain_results_button.setOnClickListener {
            if(!get_temp_val.text.isNullOrEmpty()){
                if(!get_humidity_val.text.isNullOrEmpty()){
                    if(!get_soil_moisture_val.text.isNullOrEmpty()){
                        var url = "$host_url/analyse_manual_data?temperature="+get_temp_val.text.toString()
                        url+="&humidity="+get_humidity_val.text.toString()
                        url+="&soil_moisture="+get_soil_moisture_val.text.toString()
                        progressBar2.visibility = View.VISIBLE
                        Log.d("http","The url requesting is $url")
                        Fuel.get("$url")
                            .response { request, response, result ->
                                Log.d("http", "request is : ${request.toString()}")
                                Log.d("http","Response is : ${response.toString()}")
                                val (bytes, error) = result
                                if (bytes != null) {
                                    Log.d("http","[response bytes] ${String(bytes)}")
                                    var jsonObject = JSONObject(String(bytes))
                                    res = jsonObject.getString("condition")
                                    accu = jsonObject.getString("accuracy")
                                    progressBar2.visibility = View.INVISIBLE
                                }
                            }
                    }
                }
            }
            accuracy_view.text = "Accuracy : $accu"
            result_view.text = res
        }

    }
}