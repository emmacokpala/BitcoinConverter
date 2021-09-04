package com.example.bitcoinconverter

import android.icu.text.NumberFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bitcoinconverter.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    //bind layout and IDs after Gradle module setup
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set Convert button to function
        binding.convertButton.setOnClickListener {
            calculateConversion()
        }
    }

    //create private function for conversion calculation
    private fun calculateConversion() {
        val conversion = binding.bitcoinAmountHint.text.toString().toDoubleOrNull()
        if (conversion == null || conversion == 0.0) {
            displayConversion(0.0)
            return
        }
        var rate = 20677810.04 * conversion

        //check toggle button return rounded up figure
        if (binding.roundUpToggle.isChecked) {
            rate = kotlin.math.ceil(rate)
        }
        displayConversion(rate)
    }

    //set to display result and formatting to include Nigerian currency symbol
    private fun displayConversion(rate: Double) {
        binding.result.text =
            getString(
                R.string.result,
                NumberFormat.getCurrencyInstance(Locale("us", "NG")).format(rate)
            )
    }
}