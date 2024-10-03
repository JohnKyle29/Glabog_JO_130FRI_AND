package com.capstone.bottomnavigation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.capstone.bottomnavigation.R
import com.capstone.bottomnavigation.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var currentNumber = "" // Store the current number input
    private var operator = "" // Store the operator (+, -, *, /)
    private var firstNumber = "" // Store the first operand
    private var secondNumber = "" // Store the second operand
    private var result = 0.0 // Store the result

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Get references to the UI components
        val displayTextView: TextView = binding.display

        // Number buttons
        val btn0: Button = root.findViewById(R.id.btn_0)
        val btn1: Button = root.findViewById(R.id.btn_1)
        val btn2: Button = root.findViewById(R.id.btn_2)
        val btn3: Button = root.findViewById(R.id.btn_3)
        val btn4: Button = root.findViewById(R.id.btn_4)
        val btn5: Button = root.findViewById(R.id.btn_5)
        val btn6: Button = root.findViewById(R.id.btn_6)
        val btn7: Button = root.findViewById(R.id.btn_7)
        val btn8: Button = root.findViewById(R.id.btn_8)
        val btn9: Button = root.findViewById(R.id.btn_9)

        // Operator buttons
        val btnAdd: Button = root.findViewById(R.id.btn_addition)
        val btnSubtract: Button = root.findViewById(R.id.btn_subtraction)
        val btnMultiply: Button = root.findViewById(R.id.btn_multiply)
        val btnDivide: Button = root.findViewById(R.id.btn_divide)
        val btnEqual: Button = root.findViewById(R.id.btn_equal)

        // Other buttons
        val btnClear: Button = root.findViewById(R.id.btn_clear)
        val btnDot: Button = root.findViewById(R.id.btn_dot)
        val btnPlusMinus: Button = root.findViewById(R.id.btn_plus_minus)
        val btnPercent: Button = root.findViewById(R.id.btn_percent)

        // Function to handle number button clicks
        val numberClickListener = View.OnClickListener { v ->
            val button = v as Button
            currentNumber += button.text.toString()
            displayTextView.text = currentNumber
        }

        // Assign number button listeners
        btn0.setOnClickListener(numberClickListener)
        btn1.setOnClickListener(numberClickListener)
        btn2.setOnClickListener(numberClickListener)
        btn3.setOnClickListener(numberClickListener)
        btn4.setOnClickListener(numberClickListener)
        btn5.setOnClickListener(numberClickListener)
        btn6.setOnClickListener(numberClickListener)
        btn7.setOnClickListener(numberClickListener)
        btn8.setOnClickListener(numberClickListener)
        btn9.setOnClickListener(numberClickListener)

        // Function to handle operator button clicks
        val operatorClickListener = View.OnClickListener { v ->
            val button = v as Button
            if (currentNumber.isNotEmpty()) {
                firstNumber = currentNumber
                operator = button.text.toString()
                currentNumber = ""
            }
        }

        // Assign operator button listeners
        btnAdd.setOnClickListener(operatorClickListener)
        btnSubtract.setOnClickListener(operatorClickListener)
        btnMultiply.setOnClickListener(operatorClickListener)
        btnDivide.setOnClickListener(operatorClickListener)

        // Handle equals button
        btnEqual.setOnClickListener {
            if (firstNumber.isNotEmpty() && currentNumber.isNotEmpty()) {
                secondNumber = currentNumber
                val num1 = firstNumber.toDouble()
                val num2 = secondNumber.toDouble()

                result = when (operator) {
                    "+" -> num1 + num2
                    "-" -> num1 - num2
                    "×" -> num1 * num2
                    "÷" -> num1 / num2
                    else -> 0.0
                }
                displayTextView.text = result.toString()
                firstNumber = result.toString()
                currentNumber = ""
            }
        }

        // Handle clear button
        btnClear.setOnClickListener {
            currentNumber = ""
            firstNumber = ""
            secondNumber = ""
            operator = ""
            result = 0.0
            displayTextView.text = "0"
        }

        // Handle dot button for decimals
        btnDot.setOnClickListener {
            if (!currentNumber.contains(".")) {
                currentNumber += "."
                displayTextView.text = currentNumber
            }
        }

        // Handle plus-minus button to toggle the sign
        btnPlusMinus.setOnClickListener {
            if (currentNumber.isNotEmpty()) {
                currentNumber = if (currentNumber.startsWith("-")) {
                    currentNumber.substring(1)
                } else {
                    "-$currentNumber"
                }
                displayTextView.text = currentNumber
            }
        }

        // Handle percent button
        btnPercent.setOnClickListener {
            if (currentNumber.isNotEmpty()) {
                val num = currentNumber.toDouble() / 100
                currentNumber = num.toString()
                displayTextView.text = currentNumber
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
