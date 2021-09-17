package com.example.bitcoinconverter

import android.icu.text.NumberFormat
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bitcoinconverter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding //Bind layout and ID in gradle module
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.convertButton.setOnClickListener { //set response when button is clicked
            calculateConversion()
        }
        binding.roundUpToggle.setOnClickListener { //set response when button is clicked
            calculateConversion()
        }
    }

    private fun calculateConversion() { //set algorithm for conversion calculation
        val conversion = binding.nairaAmountHint.text.toString().toDoubleOrNull()
        if (conversion == null || conversion == 0.00) {
            displayConversion(0.00)
            return //avoids app crashing when edit field input is empty and convert button activated
        }
        val rate = conversion / 19151415.75 //used BTC to NGN conversion rate as at 22.45 06/09/2021
        var convert = String.format("%.2f", rate).toDouble() //formats result to 2dp default
        if (binding.roundUpToggle.isChecked) {
            convert = convert.let { kotlin.math.round(it) } //Activates the round up toggle switch
        }
        displayConversion(convert)
    }

    private fun displayConversion(convert: Double) { //set attributes for results display
        binding.result.text =
            getString(R.string.result, NumberFormat.getNumberInstance().format(convert))
    }
}