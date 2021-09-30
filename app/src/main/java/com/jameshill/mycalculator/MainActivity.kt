package com.jameshill.mycalculator
// James Hill September 2021, Phila. PA.
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    //Declare variables
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit (view: View){
        tvInput.append((view as Button).text)
        //cast view as Button, get text from button
        lastNumeric = true //set flag to true
    }

    fun onClear (view:View){
        // function to clear the input field
        tvInput.text = "" //sets text to empty string
        //reset flags to starting state
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View){
        if (lastNumeric && !lastDot){
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator (view: View) {
        if (lastNumeric && !isOperatorAdded(tvInput.text.toString()))
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
    }

    fun onEqual (view: View){
        if (lastNumeric) {
            var tvValue = tvInput.text.toString()
            var prefix = ""
            try {
                if (tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if (tvValue.contains ("-")){
                    val splitValue = tvValue.split ("-")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (!prefix.isEmpty()){
                        one = prefix + one
                    }
                    tvInput.text = removeZero((one.toDouble() - two.toDouble()).toString())
                }

                else if (tvValue.contains ("+")){
                    val splitValue = tvValue.split ("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (!prefix.isEmpty()){
                        one = prefix + one
                    }
                    tvInput.text = removeZero((one.toDouble() + two.toDouble()).toString())
                }

                else if (tvValue.contains ("*")){
                    val splitValue = tvValue.split ("*")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (!prefix.isEmpty()){
                        one = prefix + one
                    }
                    tvInput.text = removeZero((one.toDouble() * two.toDouble()).toString())
                }

                else if (tvValue.contains ("/")){
                    val splitValue = tvValue.split ("/")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (!prefix.isEmpty()){
                        one = prefix + one
                    }
                    tvInput.text = removeZero((one.toDouble() / two.toDouble()).toString())
                }
            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun isOperatorAdded (value: String): Boolean {
        return if (value.startsWith("-")) {false} //checks for - used as a negative sign
        else {value.contains ("/")
                || value.contains ("*")
                || value.contains ("+")
                || value.contains( "-")}
    }

    private fun removeZero (result: String): String{
        var value = result
        if (result.contains(".0"))
            value = result.substring(0, result.length -2)
        return value
    }
}