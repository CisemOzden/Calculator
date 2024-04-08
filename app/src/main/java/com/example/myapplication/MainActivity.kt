package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var acButton = findViewById<Button>(R.id.ac_btn)
        //var b : Button = findViewById(R.id.button)

        //var t : TextView = findViewById(R.id.result)
        //b.setOnClickListener {
        //    if (t.text == "Hello World!") {
        //        t.text = "You clicked the button!"
        //    } else {
        //        t.text = "Hello World!"
        //    }
        //}

        var delButton : Button = findViewById(R.id.del_btn)

    }
}