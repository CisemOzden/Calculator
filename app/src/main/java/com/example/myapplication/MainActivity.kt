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
        ADD, SUBTRACT, MULTIPLY, DIVIDE, NONE
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
            updateScreen(t)
            clearOperationClicks()
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
                //complete current operation if there is one
                if(selectedOperation == Operation.SUBTRACT){
                    performSubtraction(t)
                    prepareForOperation(Operation.ADD)
                } else if(selectedOperation == Operation.DIVIDE) {
                    if(currentNumber == 0.0){
                        t.text = "Error"
                        currentSum = 0.0
                        return@setOnClickListener
                    }
                    performDivision(t)
                    prepareForOperation(Operation.ADD)
                } else if(selectedOperation == Operation.MULTIPLY) {
                    performMultiplication(t)
                    prepareForOperation(Operation.ADD)
                } else if(selectedOperation == Operation.NONE) {
                    currentSum = currentNumber
                    opSelected = true
                    selectedOperation = Operation.ADD
                    equalDone = false
                } else {
                    performAddition(t)
                }
            }
        }

        subButton.setOnClickListener {
            isDecimal = false
            clearOperationClicks()
            subButton.setBackgroundColor(Color.parseColor("#808B96"))
            if(!subDone || equalDone) {
                //complete current operation if there is one
                if(selectedOperation == Operation.ADD){
                    performAddition(t)
                    prepareForOperation(Operation.SUBTRACT)
                } else if(selectedOperation == Operation.DIVIDE) {
                    if(currentNumber == 0.0){
                        t.text = "Error"
                        currentSum = 0.0
                        return@setOnClickListener
                    }
                    performDivision(t)
                    prepareForOperation(Operation.SUBTRACT)
                } else if(selectedOperation == Operation.MULTIPLY) {
                    performMultiplication(t)
                    prepareForOperation(Operation.SUBTRACT)
                } else if(selectedOperation == Operation.NONE) {
                    currentSum = currentNumber
                    opSelected = true
                    selectedOperation = Operation.SUBTRACT
                    equalDone = false
                } else {
                    performSubtraction(t)
                }
            }
        }

        mulButton.setOnClickListener {
            isDecimal = false
            clearOperationClicks()
            mulButton.setBackgroundColor(Color.parseColor("#808B96"))
            if(!mulDone || equalDone){
                //complete current operation if there is one
                if(selectedOperation == Operation.ADD){
                    performAddition(t)
                    prepareForOperation(Operation.MULTIPLY)
                } else if(selectedOperation == Operation.DIVIDE) {
                    if(currentNumber == 0.0){
                        t.text = "Error"
                        currentSum = 0.0
                        return@setOnClickListener
                    }
                    performDivision(t)
                    prepareForOperation(Operation.MULTIPLY)
                } else if(selectedOperation == Operation.SUBTRACT) {
                    performSubtraction(t)
                    prepareForOperation(Operation.MULTIPLY)
                } else if(selectedOperation == Operation.NONE) {
                    currentSum = currentNumber
                    opSelected = true
                    selectedOperation = Operation.MULTIPLY
                    equalDone = false
                } else {
                    performMultiplication(t)
                }
            }
        }

        divButton.setOnClickListener {
            isDecimal = false
            clearOperationClicks()
            divButton.setBackgroundColor(Color.parseColor("#808B96"))
            if(!divDone || equalDone){
                //complete current operation if there is one
                if(selectedOperation == Operation.ADD){
                    performAddition(t)
                    prepareForOperation(Operation.DIVIDE)
                } else if(selectedOperation == Operation.MULTIPLY) {
                    performMultiplication(t)
                    prepareForOperation(Operation.DIVIDE)
                } else if(selectedOperation == Operation.SUBTRACT) {
                    performSubtraction(t)
                    prepareForOperation(Operation.DIVIDE)
                } else if(selectedOperation == Operation.NONE) {
                    currentSum = currentNumber
                    opSelected = true
                    selectedOperation = Operation.DIVIDE
                    equalDone = false
                } else {
                    if(currentNumber == 0.0){
                        t.text = "Error"
                        currentSum = 0.0
                        return@setOnClickListener
                    }
                    performDivision(t)
                }
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
                        divDone = true
                        return@setOnClickListener
                    }
                    currentSum = (currentSum / currentNumber).toDouble()
                    divDone = true
                }
                selectedOperation = Operation.NONE
                currentNumber = currentSum
                updateScreen(t)
                equalDone = true
            }
        }
    }


    private fun writeNum(t: TextView, num: Int) {
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
            } else {
                currentNumber = num.toDouble()
            }
            formatFinalResult(t)
        } else {
            if(t.text.length == 9) return
            if(isDecimal){
                if(t.text.endsWith('.')) {
                    if(num == 0){
                        t.text = currentNumber.toString()
                        return
                    }
                    t.text = t.text.toString() + num.toString()
                    currentNumber = t.text.toString().toDouble()
                }else {
                    if(num == 0){
                        t.text = t.text.toString() + '0'
                        return
                    }
                    t.text = t.text.toString() + num.toString()
                    currentNumber = t.text.toString().toDouble()
                }
            }else {
                currentNumber = currentNumber*10 + num
            }
            formatFinalResult(t)
        }
        addDone = false
        subDone = false
        mulDone = false
        divDone = false
        equalDone = false
    }

    private fun formatFinalResult(t: TextView){
        if(checkIfWholeNumber(currentNumber)){
            t.text = currentNumber.toInt().toString()
        }else {
            val numOfDecimals = numOfDecimalDigits(currentNumber)
            val formattedNum = "%.${numOfDecimals}f".format(currentNumber)
            t.text = formattedNum
        }
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
                t.text = currentNumber.toString()
                t.text = "%.${x}f".format(currentNumber).trimEnd('0').toString()
            } else {
                t.text = currentNumber.toString()
            }
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
        return if(decimalIndex == -1){
            0
        } else {
            str.length - decimalIndex - 1
        }
    }

    private fun prepareForOperation(operation: Operation){
        currentSum = currentNumber
        opSelected = true
        selectedOperation = operation
        equalDone = false
    }

    private fun performAddition(t: TextView){
        opSelected = true
        currentSum += currentNumber
        currentNumber = currentSum
        updateScreen(t)
        addDone = true
    }
    private fun performSubtraction(t: TextView){
        opSelected = true
        currentSum -= currentNumber
        currentNumber = currentSum
        updateScreen(t)
        subDone = true
    }

    private fun performMultiplication(t: TextView){
        opSelected = true
        currentSum *= currentNumber
        currentNumber = currentSum
        updateScreen(t)
        mulDone = true
    }

    private fun performDivision(t: TextView){
        opSelected = true
        currentSum /= currentNumber
        currentNumber = currentSum
        updateScreen(t)
        divDone = true
    }

}