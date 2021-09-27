package com.example.bitcoinconverter

import android.icu.text.NumberFormat
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.bitcoinconverter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var amountHint = 0
    private var costLayout = 0
    private var roundUpToggle = 0
    private lateinit var binding: ActivityMainBinding //Bind layout and ID in gradle module
    private lateinit var spinner: Spinner
    companion object{
        const val TAG = "Main Activity"
        const val KEY_AMOUNTHINT = "amountHint"
        const val KEY_COST_LAYOUT = "costLayout"
        const val KEY_ROUNDUPTOGGLE = "roundUpToggle"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
        if(savedInstanceState != null) {
            amountHint = savedInstanceState.getInt(KEY_AMOUNTHINT, 0)
            costLayout = savedInstanceState.getInt(KEY_COST_LAYOUT, 0)
            roundUpToggle = savedInstanceState.getInt(KEY_ROUNDUPTOGGLE, 0)
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        spinner = findViewById(R.id.spinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.currency_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            spinner.onItemSelectedListener = this
        }
        setOnItemSelectedListener()

        binding.convertButton.setOnClickListener { //set response when button is clicked
            calculateConversion()
        }
        binding.roundUpToggle.setOnClickListener { //set response when button is clicked
            calculateConversion()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume Called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause Called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy Called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart Called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_AMOUNTHINT, amountHint)
        outState.putInt(KEY_COST_LAYOUT, costLayout)
        outState.putInt(KEY_ROUNDUPTOGGLE, roundUpToggle)
        Log.d(TAG, "onSaveInstanceCalled")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        val text: String = parent?.getItemAtPosition(pos).toString()
        when (text) {
            "USD" -> 43215.08.toString()
            "GBP" -> 35039.40.toString()
            "CAD" -> 54786.85.toString()
            "JPY" -> 5293661.15.toString()
            "NGN" -> 17868555.64.toString()
            "EUR" -> 41047.92.toString()
            "CNY" -> 311704.18.toString()
            "AUD" -> 66283.78.toString()
            "ETH" -> 14.03.toString()
            "BNB" -> 125.toString()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun setOnItemSelectedListener() {
        val spinner: Spinner = findViewById(R.id.spinner)
        spinner.onItemSelectedListener = this
        calculateConversion()
    }

    private fun calculateConversion() {
        val input = binding.amountHint.text.toString().toDoubleOrNull()
        if (input == null || input == 0.00) {
            displayConversion(0.00)
            return //avoids app crashing when edit field input is empty and convert button activated
        }

        var convert = String.format("%.2f", ).toDouble() //formats result to 2dp default
        if (binding.roundUpToggle.isChecked) {
            convert = convert.let { kotlin.math.round(it) } //Activates the round up toggle switch
        }
        displayConversion(convert)
    }

    private fun displayConversion(convert: Double) { //set attributes for results display
        binding.result.text =
            getString(R.string.result, NumberFormat.getNumberInstance().format(convert))
    }



//    private fun calculateConversion() { //set algorithm for conversion calculation
//        val conversion = binding.amountHint.text.toString().toDoubleOrNull()
//        if (conversion == null || conversion == 0.00) {
//            displayConversion(0.00)
//            return //avoids app crashing when edit field input is empty and convert button activated
//        }
//        val rate = conversion / 19151415.75 //used BTC to NGN conversion rate as at 22.45 06/09/2021
//        var convert = String.format("%.2f", rate).toDouble() //formats result to 2dp default
//        if (binding.roundUpToggle.isChecked) {
//            convert = convert.let { kotlin.math.round(it) } //Activates the round up toggle switch
//        }
//        displayConversion(convert)

//    private fun displayConversion(convert: Double) { //set attributes for results display
//        binding.result.text =
//            getString(R.string.result, NumberFormat.getNumberInstance().format(convert))
//    }
}