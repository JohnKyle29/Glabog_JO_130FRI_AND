package com.capstone.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var currentNumber: String = ""
    private var previousNumber: String = ""
    private var operation: String? = null
    private val decimalFormat = DecimalFormat("#.##")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        // Number buttons
        val buttons = listOf(
            findViewById<Button>(R.id.btn_0),
            findViewById<Button>(R.id.btn_1),
            findViewById<Button>(R.id.btn_2),
            findViewById<Button>(R.id.btn_3),
            findViewById<Button>(R.id.btn_4),
            findViewById<Button>(R.id.btn_5),
            findViewById<Button>(R.id.btn_6),
            findViewById<Button>(R.id.btn_7),
            findViewById<Button>(R.id.btn_8),
            findViewById<Button>(R.id.btn_9)
        )

        buttons.forEach { button ->
            button.setOnClickListener { onNumberClick(button.text.toString()) }
        }

        // Operation buttons
        findViewById<Button>(R.id.btn_addition).setOnClickListener { onOperationClick("+") }
        findViewById<Button>(R.id.btn_subtraction).setOnClickListener { onOperationClick("-") }
        findViewById<Button>(R.id.btn_multiply).setOnClickListener { onOperationClick("*") }
        findViewById<Button>(R.id.btn_divide).setOnClickListener { onOperationClick("/") }
        findViewById<Button>(R.id.btn_percent).setOnClickListener { onPercentClick() }

        // Other buttons
        findViewById<Button>(R.id.btn_equal).setOnClickListener { onEqualClick() }
        findViewById<Button>(R.id.btn_clear).setOnClickListener { onClearClick() }
        findViewById<Button>(R.id.btn_dot).setOnClickListener { onDotClick() }
    }

    private fun onNumberClick(number: String) {
        if (currentNumber.length < 10) {  // Limiting input length for better display
            currentNumber += number
            display.text = currentNumber
        }
    }

    private fun onOperationClick(op: String) {
        if (currentNumber.isNotEmpty()) {
            previousNumber = currentNumber
            currentNumber = ""
            operation = op
        }
    }

    private fun onEqualClick() {
        if (currentNumber.isNotEmpty() && previousNumber.isNotEmpty()) {
            val result = when (operation) {
                "+" -> previousNumber.toDouble() + currentNumber.toDouble()
                "-" -> previousNumber.toDouble() - currentNumber.toDouble()
                "*" -> previousNumber.toDouble() * currentNumber.toDouble()
                "/" -> if (currentNumber != "0") previousNumber.toDouble() / currentNumber.toDouble() else Double.NaN
                else -> return
            }
            displayResult(result)
        }
    }

    private fun onPercentClick() {
        if (currentNumber.isNotEmpty()) {
            // Calculate percentage based on the current number
            val percentage = currentNumber.toDouble() / 100
            // Display the result
            displayResult(percentage)
        }
    }


    private fun onClearClick() {
        currentNumber = ""
        previousNumber = ""
        operation = null
        display.text = "0"
    }

    private fun onDotClick() {
        if (!currentNumber.contains(".")) {
            if (currentNumber.isEmpty()) {
                currentNumber = "0."
            } else {
                currentNumber += "."
            }
            display.text = currentNumber
        }
    }

    private fun displayResult(result: Double) {
        display.text = if (result.isNaN()) "Error" else decimalFormat.format(result)
        currentNumber = ""
        previousNumber = ""
        operation = null
    }
}