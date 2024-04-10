package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.graphics.green

class MainActivity : AppCompatActivity() {
    enum class Operation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE, EQUAL, NONE
    }

    var selectedOperation = Operation.NONE
    var opSelected = false
    var equalDone = false
    var addDone = false
    var subDone = false

    private var currentNumber = 0
    private var currentSum = 0
    private var numberQueue = arrayOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var result = 0

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
        val addButton : Button = findViewById(R.id.add_btn)
        val subButton : Button = findViewById(R.id.sub_btn)
        val mulButton : Button = findViewById(R.id.mul_btn)
        val divButton : Button = findViewById(R.id.div_btn)
        val equalButton : Button = findViewById(R.id.equal_btn)


        btn1.setOnClickListener{writeNum(t, 1)}
        btn2.setOnClickListener{writeNum(t, 2)}
        btn3.setOnClickListener{writeNum(t, 3)}
        btn4.setOnClickListener{writeNum(t, 4)}
        btn5.setOnClickListener{writeNum(t, 5)}
        btn6.setOnClickListener{writeNum(t, 6)}
        btn7.setOnClickListener{writeNum(t, 7)}
        btn8.setOnClickListener{writeNum(t, 8)}
        btn9.setOnClickListener{writeNum(t, 9)}
        btn0.setOnClickListener{writeNum(t, 0)}

        acButton.setOnClickListener {
            currentNumber = 0
            currentSum = 0
            //t.text = currentNumber.toString()
            updateScreen(t)
            selectedOperation = Operation.NONE
            opSelected = false
        }

        delButton.setOnClickListener {
            currentNumber /= 10
            currentSum = currentNumber
            //t.text = currentNumber.toString()
            updateScreen(t)

        }

        addButton.setOnClickListener {
            if(!addDone){
                opSelected = true
                selectedOperation = Operation.ADD
                addButton.setBackgroundColor(Color.parseColor("#808B96"))
                currentSum += currentNumber
                t.text = currentSum.toString()
                addDone = true
            }
        }

        equalButton.setOnClickListener {
            if(!equalDone) {
                if(selectedOperation == Operation.ADD) {
                    currentSum += currentNumber
                } else if(selectedOperation == Operation.SUBTRACT) {
                    currentSum -= currentNumber
                }
                currentNumber = currentSum
                updateScreen(t)
                equalDone = true
            }
            //selectedOperation = Operation.EQUAL //?
            //opSelected = false //?
        }

        subButton.setOnClickListener {
            if(!subDone){
                if(selectedOperation == Operation.NONE) {
                    currentSum = currentNumber
                    opSelected = true
                    selectedOperation = Operation.SUBTRACT
                    subButton.setBackgroundColor(Color.parseColor("#808B96"))
                    return@setOnClickListener
                }

                opSelected = true
                //selectedOperation = Operation.SUBTRACT
                //addButton.setBackgroundColor(Color.parseColor("#808B96"))
                currentSum -= currentNumber
                t.text = currentSum.toString()
                subDone = true
            }
        }











    }

    private fun writeNum(t: TextView, num: Int) {
        //equalDone = false
        if(opSelected || equalDone) {
            opSelected = false
            currentNumber = num
            updateScreen(t)
        } else {
            if(currentNumber.toString().length == 9) return

            currentNumber = currentNumber*10 + num
            //t.text = currentNumber.toString()
            updateScreen(t)
        }
        addDone = false
        subDone = false
        equalDone = false
    }

    private fun updateScreen(t: TextView) {
        t.text = currentNumber.toString()
    }
}