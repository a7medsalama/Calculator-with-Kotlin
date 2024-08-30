package com.example.calculator

import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var firstNumber = ""
    private var currentNumber = ""
    private var currentOperator = ""
    private var result = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // No Limited Screen
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        // get All Views
        binding.apply {
            // MainLayout Listener
            binding.mainLayout.children.filterIsInstance<Button>().forEach { button ->
                //Button Listener
                button.setOnClickListener {
                    val buttonText = button.text.toString()

                    when {
                        buttonText == "C" -> {
                            firstNumber = ""
                            currentNumber = ""
                            currentOperator = ""
                            result = ""
                            tvResult.text = "0"
                            tvFormula.text = ""
                        }

                        buttonText.matches(Regex("[0-9]")) -> {
                            if (currentOperator.isEmpty()) {
                                firstNumber += buttonText
                                tvResult.text = firstNumber
                            } else {
                                currentNumber += buttonText
                                tvResult.text = currentNumber
                            }
                        }
                        buttonText.matches(Regex("[+\\-*/]")) -> {
                            currentNumber = ""
                            if (tvResult.text.toString().isNotEmpty()) {
                                currentOperator = buttonText
                            }
                        }
                        buttonText == "=" -> {
                            if (currentNumber.isNotEmpty() && currentOperator.isNotEmpty()) {
                                tvFormula.text = "$firstNumber$currentOperator$currentNumber"
                                result = evaluateExpression(firstNumber,currentNumber,currentOperator)
                                firstNumber = result
                                tvResult.text= result
                            }
                        }
                        buttonText == "." ->  {
                            if (currentOperator.isEmpty()) {
                                if (! firstNumber.contains(".")) {
                                    if (firstNumber.isEmpty())
                                        firstNumber += "0$buttonText"
                                    else
                                        firstNumber += buttonText
                                    tvResult.text = firstNumber
                                }
                            }else {
                                if (! currentNumber.contains(".")) {
                                    if (currentNumber.isEmpty())
                                        currentNumber = "0$buttonText"
                                    else
                                        currentNumber += buttonText
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    private fun evaluateExpression(firstNum: String, secondNum: String,operator: String): String {
        val num1 = firstNum.toDouble()
        val num2 = secondNum.toDouble()

        return when(operator) {
            "+" -> (num1+num2).toString()
            "-" -> (num1-num2).toString()
            "*" -> (num1*num2).toString()
            "/" -> (num1/num2).toString()
            else -> ""
        }
    }
}

// Function