package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.graphics.green
import kotlin.math.ceil
import kotlin.math.floor

class MainActivity : AppCompatActivity() {
    enum class Operation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE, EQUAL, NONE
    }

    private var selectedOperation = Operation.NONE
    private var opSelected = false
    private var equalDone = false
    private var addDone = false
    private var subDone = false
    private var mulDone = false
    private var divDone = false

    private var isDecimal = false


    private lateinit var addButton : Button
    private lateinit var subButton : Button
    private lateinit var mulButton : Button
    private lateinit var divButton : Button


    private var currentNumber = 0.0
    private var currentSum = 0.0
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
        val commaButton : Button = findViewById(R.id.comma_btn)
        val acButton : Button = findViewById(R.id.ac_btn)
        val delButton : Button = findViewById(R.id.del_btn)
        addButton = findViewById(R.id.add_btn)
        subButton = findViewById(R.id.sub_btn)
        mulButton = findViewById(R.id.mul_btn)
        divButton = findViewById(R.id.div_btn)
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
        commaButton.setOnClickListener {addComma(t)}

        acButton.setOnClickListener {
            currentNumber = 0.0
            currentSum = 0.0
            //t.text = currentNumber.toString()
            updateScreen(t)
            selectedOperation = Operation.NONE
            opSelected = false
            isDecimal = false
        }

        delButton.setOnClickListener {
            currentNumber /= 10
            currentSum = currentNumber
            //t.text = currentNumber.toString()
            updateScreen(t)

        }

        addButton.setOnClickListener {
            clearOperationClicks()
            addButton.setBackgroundColor(Color.parseColor("#808B96"))
            if(!addDone || equalDone){
                if(selectedOperation != Operation.ADD) {
                    currentSum = currentNumber
                    opSelected = true
                    selectedOperation = Operation.ADD
                    equalDone = false
                    return@setOnClickListener
                }
                opSelected = true
                currentSum += currentNumber
                t.text = currentSum.toString()
                addDone = true
                //equalDone = true
            }
        }

        subButton.setOnClickListener {
            clearOperationClicks()
            subButton.setBackgroundColor(Color.parseColor("#808B96"))
            if(!subDone || equalDone) {
                if(selectedOperation != Operation.SUBTRACT) {
                    currentSum = currentNumber
                    opSelected = true
                    selectedOperation = Operation.SUBTRACT
                    equalDone = false
                    return@setOnClickListener
                }
                opSelected = true
                currentSum -= currentNumber
                t.text = currentSum.toString()
                subDone = true
                //equalDone = true
            }
        }

        mulButton.setOnClickListener {
            clearOperationClicks()
            mulButton.setBackgroundColor(Color.parseColor("#808B96"))
            if(!mulDone || equalDone){
                if(selectedOperation != Operation.MULTIPLY) {
                    currentSum = currentNumber
                    opSelected = true
                    selectedOperation = Operation.MULTIPLY
                    equalDone = false
                    return@setOnClickListener
                }
                opSelected = true
                currentSum *= currentNumber
                t.text = currentSum.toString()
                mulDone = true
                //equalDone = true
            }
        }

        divButton.setOnClickListener {
            clearOperationClicks()
            divButton.setBackgroundColor(Color.parseColor("#808B96"))
            if(!divDone || equalDone){
                if(selectedOperation != Operation.DIVIDE) {
                    currentSum = currentNumber
                    opSelected = true
                    selectedOperation = Operation.DIVIDE
                    equalDone = false
                    return@setOnClickListener
                }
                opSelected = true
                currentSum /= currentNumber
                t.text = currentSum.toString()
                divDone = true
                //equalDone = true
            }
        }



        equalButton.setOnClickListener {
            clearOperationClicks()
            if(!equalDone) {
                if(selectedOperation == Operation.ADD) {
                    currentSum += currentNumber
                    addDone = true
                } else if(selectedOperation == Operation.SUBTRACT) {
                    currentSum -= currentNumber
                    subDone = true
                } else if(selectedOperation == Operation.MULTIPLY) {
                    currentSum *= currentNumber
                    mulDone = true
                } else if(selectedOperation == Operation.DIVIDE) {
                    currentSum = "%.1f".format((currentSum / currentNumber)).toDouble()
                    mulDone = true
                }
                selectedOperation = Operation.NONE
                currentNumber = currentSum
                updateScreen(t)
                equalDone = true
            }
            //selectedOperation = Operation.EQUAL //?
            //opSelected = false //?
        }











    }

    private fun writeNum(t: TextView, num: Int) {
        //equalDone = false
        clearOperationClicks()
        if(opSelected || equalDone) {
            opSelected = false
            if(isDecimal){
                currentNumber = "%.1f".format(((currentNumber*10 + num)/10)).toDouble()
            } else {
                currentNumber = num.toDouble()
            }
            updateScreen(t)
        } else {
            if(currentNumber.toString().length == 9) return

            if(isDecimal){
                currentNumber = "%.1f".format(((currentNumber*10 + num)/10)).toDouble()
            } else {
                currentNumber = currentNumber*10 + num
            }

            //t.text = currentNumber.toString()
            updateScreen(t)
        }
        addDone = false
        subDone = false
        mulDone = false
        divDone = false
        equalDone = false
    }

    private fun addComma(t: TextView) {
        clearOperationClicks()
        if (checkIfWholeNumber(currentNumber)) {
            t.text = currentNumber.toInt().toString() + ','
            isDecimal = true
        }
    }

    private fun updateScreen(t: TextView) {
        if(checkIfWholeNumber(currentNumber)){
            t.text = currentNumber.toInt().toString()
        } else {
            t.text = "%.1f".format(currentNumber)

        }
    }

    private fun clearOperationClicks() {
        addButton.setBackgroundColor(Color.parseColor("#5499C7"))
        subButton.setBackgroundColor(Color.parseColor("#5499C7"))
        mulButton.setBackgroundColor(Color.parseColor("#5499C7"))
        divButton.setBackgroundColor(Color.parseColor("#5499C7"))

    }

    private fun checkIfWholeNumber(num: Double) : Boolean {
        return ceil(num) == floor(num )
    }
}