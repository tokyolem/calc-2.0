package com.ftclub.calc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.ftclub.calc.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    /** Объявление переменной binding с поздней инициализацией
     Используется для получения доступа к объектам activity_main.xml */
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        supportActionBar!!.hide();

        onButtonClick()
    }

    /** Метод обработки нажатий кнопок Calculator */
    private fun onButtonClick() {
        setNumberListener(binding.one, "1")
        setNumberListener(binding.two, "2")
        setNumberListener(binding.three, "3")
        setNumberListener(binding.four, "4")
        setNumberListener(binding.five, "5")
        setNumberListener(binding.six, "6")
        setNumberListener(binding.seven, "7")
        setNumberListener(binding.eight, "8")
        setNumberListener(binding.nine, "9")
        setNumberListener(binding.zero, "0")
        setNumberListener(binding.sub, "-")
        setNumberListener(binding.div, "/")
        setNumberListener(binding.mod, "*")
        setNumberListener(binding.add, "+")
        setNumberListener(binding.left, "(")
        setNumberListener(binding.right, ")")
        setNumberListener(binding.dot, ".")

        binding.clearText.setOnClickListener {
            binding.mathOperation.text = ""
            binding.resultText.text = ""
        }

        binding.back.setOnClickListener {
            val str = binding.mathOperation.text.toString()
            if (str.isNotEmpty()) {
                binding.mathOperation.text = str.substring(0, str.length - 1)
            }
            binding.resultText.text = ""
        }

        binding.result.setOnClickListener {
            getResult()
        }
    }

    /** Метод, производящий подсчёт введённого математического выражения */
    private fun getResult() {
        try {
            val ex = ExpressionBuilder(binding.mathOperation.text.toString()).build()
            val result = ex.evaluate()
            val longRes = result.toLong()

            if (result == longRes.toDouble()) {
                binding.resultText.text = longRes.toString()
            } else {
                binding.resultText.text = result.toString()
            }
        } catch (e: Exception) {
            binding.resultText.text = "Error"
            Log.i("Expression error", "$e")
        }
    }

    /** Метод установки случшателя кнопок */
    private fun setNumberListener(view: View, sign: String) {
        view.setOnClickListener {
            enterNumber(sign)
        }
    }

    /** Метод используется для передачи введённого символа в строку математического выражения */
    private fun enterNumber(sign: String) {
        if (binding.resultText.text != "") {
            binding.mathOperation.text = binding.resultText.text
            binding.resultText.text = ""
        }

        if (isSign(binding.mathOperation.text.toString()) && isSign(sign)) {
            var ex = binding.mathOperation.text.toString()
            ex = ex.dropLast(1) + sign
            binding.mathOperation.text = ex
        } else {
            binding.mathOperation.append(sign)
        }
    }

    /** Метод служит для проверки последнего введённого символа на наличие знака действия */
    private fun isSign(ex: String): Boolean {
        if (ex.isEmpty()) return false

        if (ex.last() == '/' || ex.last() == '*' || ex.last() == '+'
            || ex.last() == '-' || ex.last() == '.' || ex.last() == '(' || ex.last() == ')'
        ) {
            return true
        }

        return false
    }

}