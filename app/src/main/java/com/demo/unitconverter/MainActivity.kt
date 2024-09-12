package com.demo.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.unitconverter.ui.theme.UnitConverterTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        unitConverter()
                    }
                }
            }
        }
    }
}

// Helper functions moved outside the composable
fun isDistance(unit: String): Boolean = unit in listOf("Centimeter", "Meter", "Feet", "Millimeter")
fun isTemperature(unit: String): Boolean = unit in listOf("Celsius", "Fahrenheit", "Kelvin")
fun isWeight(unit: String): Boolean = unit in listOf("Kilogram", "Gram", "Pound")

@Composable
fun unitConverter() {
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    var inputunit by remember { mutableStateOf("Meters") }
    var outputUnit by remember { mutableStateOf("Meters") }
    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }
    val conversionFactor = remember { mutableStateOf(1.00) }
    val oConversionFactor = remember { mutableStateOf(1.00) }

    val customTextStyle = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontSize = 25.sp,
        fontWeight = FontWeight.SemiBold
    )

    fun converterUnits() {
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0

        val result = when {
            isDistance(inputunit) && isDistance(outputUnit) ->
                (inputValueDouble * conversionFactor.value * 100.0 / oConversionFactor.value).roundToInt() / 100.0
            isTemperature(inputunit) && isTemperature(outputUnit) -> when (inputunit to outputUnit) {
                "Celsius" to "Fahrenheit" -> (inputValueDouble * 9 / 5) + 32
                "Fahrenheit" to "Celsius" -> (inputValueDouble - 32) * 5 / 9
                "Celsius" to "Kelvin" -> inputValueDouble + 273.15
                "Kelvin" to "Celsius" -> inputValueDouble - 273.15
                else -> inputValueDouble // Same unit, no conversion needed
            }
            isWeight(inputunit) && isWeight(outputUnit) -> when (inputunit to outputUnit) {
                "Kilogram" to "Pound" -> inputValueDouble * 2.20462
                "Pound" to "Kilogram" -> inputValueDouble / 2.20462
                "Gram" to "Kilogram" -> inputValueDouble / 1000
                "Kilogram" to "Gram" -> inputValueDouble * 1000
                else -> (inputValueDouble * conversionFactor.value * 100.0 / oConversionFactor.value).roundToInt() / 100.0
            }
            else -> Double.NaN // Invalid conversion
        }

        outputValue = if (result.isNaN()) "Invalid conversion" else result.toString()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Unit Converter", style = customTextStyle)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
                converterUnits()
            },
            label = { Text("Enter the Value") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Box {
                Button(onClick = { iExpanded = true }) {
                    Text(inputunit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Dropdown")
                }
                DropdownMenu(expanded = iExpanded, onDismissRequest = { iExpanded = false }) {
                    // Existing distance conversions
                    DropdownMenuItem(text = { Text("Centimeter") }, onClick = {
                        iExpanded = false
                        inputunit = "Centimeter"
                        conversionFactor.value = 0.01
                        converterUnits()
                    })
                    DropdownMenuItem(text = { Text("Meter") }, onClick = {
                        iExpanded = false
                        inputunit = "Meter"
                        conversionFactor.value = 1.0
                        converterUnits()
                    })
                    DropdownMenuItem(text = { Text("Feet") }, onClick = {
                        iExpanded = false
                        inputunit = "Feet"
                        conversionFactor.value = 0.3048
                        converterUnits()
                    })
                    DropdownMenuItem(text = { Text("Millimeter") }, onClick = {
                        iExpanded = false
                        inputunit = "Millimeter"
                        conversionFactor.value = 0.001
                        converterUnits()
                    })
                    // New Temperature conversions
                    DropdownMenuItem(text = { Text("Celsius") }, onClick = {
                        iExpanded = false
                        inputunit = "Celsius"
                        converterUnits()
                    })
                    DropdownMenuItem(text = { Text("Fahrenheit") }, onClick = {
                        iExpanded = false
                        inputunit = "Fahrenheit"
                        converterUnits()
                    })
                    DropdownMenuItem(text = { Text("Kelvin") }, onClick = {
                        iExpanded = false
                        inputunit = "Kelvin"
                        converterUnits()
                    })
                    // New Weight conversions
                    DropdownMenuItem(text = { Text("Kilogram") }, onClick = {
                        iExpanded = false
                        inputunit = "Kilogram"
                        converterUnits()
                    })
                    DropdownMenuItem(text = { Text("Gram") }, onClick = {
                        iExpanded = false
                        inputunit = "Gram"
                        converterUnits()
                    })
                    DropdownMenuItem(text = { Text("Pound") }, onClick = {
                        iExpanded = false
                        inputunit = "Pound"
                        converterUnits()
                    })
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Output Button
            Box {
                Button(onClick = { oExpanded = true }) {
                    Text(outputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Dropdown")
                }
                DropdownMenu(expanded = oExpanded, onDismissRequest = { oExpanded = false }) {
                    // Existing distance conversions
                    DropdownMenuItem(text = { Text("Centimeter") }, onClick = {
                        oExpanded = false
                        outputUnit = "Centimeter"
                        oConversionFactor.value = 0.01
                        converterUnits()
                    })
                    DropdownMenuItem(text = { Text("Meter") }, onClick = {
                        oExpanded = false
                        outputUnit = "Meter"
                        oConversionFactor.value = 1.0
                        converterUnits()
                    })
                    DropdownMenuItem(text = { Text("Feet") }, onClick = {
                        oExpanded = false
                        outputUnit = "Feet"
                        oConversionFactor.value = 0.3048
                        converterUnits()
                    })
                    DropdownMenuItem(text = { Text("Millimeter") }, onClick = {
                        oExpanded = false
                        outputUnit = "Millimeter"
                        oConversionFactor.value = 0.001
                        converterUnits()
                    })
                    // New Temperature conversions
                    DropdownMenuItem(text = { Text("Celsius") }, onClick = {
                        oExpanded = false
                        outputUnit = "Celsius"
                        converterUnits()
                    })
                    DropdownMenuItem(text = { Text("Fahrenheit") }, onClick = {
                        oExpanded = false
                        outputUnit = "Fahrenheit"
                        converterUnits()
                    })
                    DropdownMenuItem(text = { Text("Kelvin") }, onClick = {
                        oExpanded = false
                        outputUnit = "Kelvin"
                        converterUnits()
                    })
                    // New Weight conversions
                    DropdownMenuItem(text = { Text("Kilogram") }, onClick = {
                        oExpanded = false
                        outputUnit = "Kilogram"
                        converterUnits()
                    })
                    DropdownMenuItem(text = { Text("Gram") }, onClick = {
                        oExpanded = false
                        outputUnit = "Gram"
                        converterUnits()
                    })
                    DropdownMenuItem(text = { Text("Pound") }, onClick = {
                        oExpanded = false
                        outputUnit = "Pound"
                        converterUnits()
                    })
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Result: $outputValue $outputUnit", style = MaterialTheme.typography.titleLarge)
    }
}
