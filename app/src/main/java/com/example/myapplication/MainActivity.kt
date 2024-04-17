package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.graphics.green
import java.math.BigDecimal
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow

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
    private var currentNumberStr = "0"

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
            if(t.text.length == 1 || (t.text.length == 2 && t.text.startsWith('-'))){
                currentNumber = 0.0
            }else{
                val strResult = t.text.dropLast(1).toString()
                currentNumber = strResult.toDouble()
            }

            currentSum = currentNumber
            updateScreen(t)

        }

        addButton.setOnClickListener {
            isDecimal = false
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
                currentNumber = currentSum
                //t.text = currentSum.toString()
                updateScreen(t)
                addDone = true
                //equalDone = true
            }

        }

        subButton.setOnClickListener {
            isDecimal = false
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
                currentNumber = currentSum
                //t.text = currentSum.toString()
                updateScreen(t)
                subDone = true
                //equalDone = true
            }
        }

        mulButton.setOnClickListener {
            isDecimal = false
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
                currentNumber = currentSum
                //t.text = currentSum.toString()
                updateScreen(t)
                mulDone = true
                //equalDone = true
            }
        }

        divButton.setOnClickListener {
            isDecimal = false
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
                if(currentNumber == 0.0){
                    t.text = "Error"
                    return@setOnClickListener
                }
                currentSum /= currentNumber
                currentNumber = currentSum
                //t.text = currentSum.toString()
                updateScreen(t)
                divDone = true
                //equalDone = true
            }
        }



        equalButton.setOnClickListener {
            isDecimal = false
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
                    if(currentNumber == 0.0){
                        t.text = "Error"
                        currentSum = 0.0
                        mulDone = true
                        return@setOnClickListener
                    }
                    //currentSum = "%.1f".format((currentSum / currentNumber)).toDouble()
                    currentSum = (currentSum / currentNumber).toDouble()
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
                if(t.text.endsWith('.')) {
                    currentNumber = "%.1f".format(((currentNumber*10 + num)/10)).toDouble()
                }else {
                    currentNumber = currentNumber.toString().toDouble()
                    val digitCount = numOfDecimalDigits(currentNumber)
                    val a = currentNumber* 10.0.pow(digitCount.toDouble() + 1)
                    val newNumber = (a + num)/ (10.0.pow(
                        digitCount.toDouble() + 1)
                            )
                    currentNumber = newNumber
                }
                //currentNumber = "%.1f".format(((currentNumber*10 + num)/10)).toDouble()
            } else {
                currentNumber = num.toDouble()
            }
            if(checkIfWholeNumber(currentNumber)){
                t.text = currentNumber.toInt().toString()
            }else {
                val numOfDecimals = numOfDecimalDigits(currentNumber)
                val formattedNum = "%.${numOfDecimals}f".format(currentNumber)
                t.text = formattedNum
            }

        } else {
            if(t.text.length == 9) return

            if(isDecimal){
                if(t.text.endsWith('.')) {
                    if(num == 0){
                        t.text = currentNumber.toString()
                        return
                    }
                    currentNumber = "%.1f".format(((currentNumber*10 + num)/10)).toDouble()
                }else {
                    if(num == 0){
                        t.text = t.text.toString() + '0'
                        currentNumber = t.text.toString().toDouble() //does not work!!
                        return
                    }
                    val digitCount = numOfDecimalDigits(currentNumber)
                    val a = (currentNumber* 10.0.pow(digitCount.toDouble() + 1)).toInt()
                    val newNumber = (a + num)/ (10.0.pow(
                        digitCount.toDouble() + 1)
                    )
                    currentNumber = newNumber
                }
                //currentNumber = "%.1f".format(((currentNumber*10 + num)/10)).toDouble()
            }else {
                currentNumber = currentNumber*10 + num
            }

            if(checkIfWholeNumber(currentNumber)){
                t.text = currentNumber.toInt().toString()
            }else {
                val numOfDecimals = numOfDecimalDigits(currentNumber)
                val formattedNum = "%.${numOfDecimals}f".format(currentNumber)
                t.text = formattedNum
            }
            //updateScreen(t)
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
            t.text = currentNumber.toInt().toString() + '.'
            isDecimal = true
        }
    }

    private fun updateScreen(t: TextView) {
        if(checkIfWholeNumber(currentNumber)){
            t.text = currentNumber.toInt().toString()
        } else {
            currentNumber = currentNumber.toString().trimEnd('0').toDouble()
            val len = currentNumber.toString().length
            if(len >= 9) {
                val dec = numOfDecimalDigits(currentNumber)
                val x = 9 - len + dec
                t.text = "%.${x}f".format(currentNumber).trimEnd('0').toString()
            } else {
                t.text = currentNumber.toString()
            }

            //t.text = "%.1f".format(currentNumber)
            //t.text = currentNumber.toString()
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

    private fun numOfDecimalDigits(num: Double): Int {
        val str = num.toString()
        val decimalIndex = str.indexOf('.')
        return if (decimalIndex == -1) {
            0
        } else {
            str.length - decimalIndex - 1
        }
    }

}