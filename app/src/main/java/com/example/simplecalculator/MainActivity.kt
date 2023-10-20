package com.example.simplecalculator

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.simplecalculator.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(Color.parseColor("#FF018786"))
        )



        binding.plus.setOnClickListener {
            performOperation(Operation.ADD)
        }

        binding.minus.setOnClickListener {
            performOperation(Operation.SUBTRACT)
        }

        binding.multiply.setOnClickListener {
            performOperation(Operation.MULTIPLY)
        }

        binding.divide.setOnClickListener {
            performOperation(Operation.DIVIDE)
        }
    }

    private fun inputIsNotEmpty(): Boolean {
        if (TextUtils.isEmpty(binding.input1.text.toString().trim())) {
            binding.input1.error = "Required"
            return false
        }
        if (TextUtils.isEmpty(binding.input2.text.toString().trim())) {
            binding.input2.error = "Required"
            return false
        }
        return true
    }

    private fun getInputValue(input: EditText): BigDecimal {
        return input.text.toString().trim().toBigDecimal()
    }

    private fun performOperation(operation: Operation) {
        if (inputIsNotEmpty()) {
            val inputdata1 = getInputValue(binding.input1)
            val inputdata2 = getInputValue(binding.input2)

            val result = when (operation) {
                Operation.ADD -> inputdata1.add(inputdata2)
                Operation.SUBTRACT -> inputdata1.subtract(inputdata2)
                Operation.MULTIPLY -> inputdata1.multiply(inputdata2)
                Operation.DIVIDE -> {
                    if (inputdata2.compareTo(BigDecimal.ZERO) == 0) {
                        binding.input2.error = "Invalid input"
                        return
                    }
                    inputdata1.divide(inputdata2, 2, RoundingMode.HALF_UP)
                }
            }

            binding.result.text = result.toString()
        }
    }

    enum class Operation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE
    }
}
