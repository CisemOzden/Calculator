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

        var t : TextView = findViewById(R.id.result)
        val btn1 : Button = findViewById(R.id.btn_1)
        val btn2 : Button = findViewById(R.id.btn_2)
        val btn3 : Button = findViewById(R.id.btn_3)
        val btn4 : Button = findViewById(R.id.btn_4)
        val btn5 : Button = findViewById(R.id.btn_5)
        val btn6 : Button = findViewById(R.id.btn_6)
        val btn7 : Button = findViewById(R.id.btn_7)
        val btn8 : Button = findViewById(R.id.btn_8)
        val btn9 : Button = findViewById(R.id.btn_9)
        val btn0 : Button = findViewById(R.id.btn_0)
        val acButton : Button = findViewById(R.id.ac_btn)
        val delButton : Button = findViewById(R.id.del_btn)

        btn1.setOnClickListener{writeNum(t, "1")}
        btn2.setOnClickListener{writeNum(t, "2")}
        btn3.setOnClickListener{writeNum(t, "3")}
        btn4.setOnClickListener{writeNum(t, "4")}
        btn5.setOnClickListener{writeNum(t, "5")}
        btn6.setOnClickListener{writeNum(t, "6")}
        btn7.setOnClickListener{writeNum(t, "7")}
        btn8.setOnClickListener{writeNum(t, "8")}
        btn9.setOnClickListener{writeNum(t, "9")}
        btn0.setOnClickListener{writeNum(t, "0")}

        acButton.setOnClickListener {
            t.text = "0"
        }

        delButton.setOnClickListener {
            if(t.text.toString().length == 1) {
                t.text = "0";
            } else {
                t.text = t.text.dropLast(1);
            }
        }





    }

    private fun writeNum(t: TextView, num: String) {
        if(t.text.toString() == "0") {
            t.text = "";
        }
        t.text = t.text.toString() + num;
    }
}