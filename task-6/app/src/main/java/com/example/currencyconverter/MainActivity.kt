package com.example.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.KeyboardType
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme

// Data class untuk Currency
data class Currency(
    val code: String,
    val name: String,
    val rateToIDR: Double
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CurrencyConverterScreen()
                }
            }
        }
    }
}

@Composable
fun CurrencyConverterScreen() {
    val currencies = listOf(
        Currency("IDR", "Indonesian Rupiah", 1.0),
        Currency("USD", "United States Dollar", 16703.06),
        Currency("EUR", "Euro", 19048.17),
        Currency("JPY", "Japanese Yen", 117.2968),
        Currency("SGD", "Singapore Dollar", 12779.69),
        Currency("AUD", "Australian Dollar", 10738.4),
        Currency("MYR", "Malaysian Ringgit", 3862.87),
        Currency("CNY", "Chinese Yuan", 2300.19),
        Currency("KRW", "South Korean Won", 11.66),
        Currency("GBP", "British Pound", 22418.85)
    )

    var amount by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var fromCurrency by remember { mutableStateOf(currencies[0]) }
    var toCurrency by remember { mutableStateOf(currencies[1]) }

    fun convert(): String {
        val value = amount.toDoubleOrNull() ?: return ""
        val converted = value * (fromCurrency.rateToIDR / toCurrency.rateToIDR)
        return String.format("%.2f", converted)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ){

            Box(modifier = Modifier
                .fillMaxHeight()
                .weight(1f, fill = true)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "CURRENCY CONVERTER",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                CurrencyDropdown(
                    label = "Kurs Asal",
                    selectedCurrency = fromCurrency,
                    currencies = currencies,
                    onSelected = { fromCurrency = it }
                )

                CurrencyDropdown(
                    label = "Kurs Tujuan",
                    selectedCurrency = toCurrency,
                    currencies = currencies,
                    onSelected = { toCurrency = it }
                )

                OutlinedTextField(
                    value = amount,
                    onValueChange = {
                        if (it.all { c -> c.isDigit() || c == '.' }) amount = it
                    },
                    label = { Text("Nominal (${fromCurrency.code})") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = result,
                    onValueChange = {},
                    label = { Text("Hasil Konversi (${toCurrency.code})") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = { result = convert() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Konversi")
                }
            }

            Box(modifier = Modifier
                .fillMaxHeight()
                .weight(1f, fill = true)
            )

        }
    }
}

@Composable
fun CurrencyDropdown(
    label: String,
    selectedCurrency: Currency,
    currencies: List<Currency>,
    onSelected: (Currency) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(label, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 4.dp))

        OutlinedButton(
            onClick = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                painter = painterResource(getFlagRes(selectedCurrency.code)),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("${selectedCurrency.code} - ${selectedCurrency.name}")
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            currencies.forEach { currency ->
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(getFlagRes(currency.code)),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("${currency.code} - ${currency.name}")
                        }
                    },
                    onClick = {
                        onSelected(currency)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun getFlagRes(currencyCode: String): Int {
    val context = LocalContext.current
    return context.resources.getIdentifier(
        currencyCode.lowercase(), "drawable", context.packageName
    ).takeIf { it != 0 } ?: R.drawable.ic_launcher_foreground
}
