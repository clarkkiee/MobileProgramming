package com.example.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme

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
    var isFromInput by remember { mutableStateOf(true) }
    var amountFrom by remember { mutableStateOf("") }
    var amountTo by remember { mutableStateOf("") }
    var fromCurrency by remember { mutableStateOf("IDR") }
    var toCurrency by remember { mutableStateOf("USD") }

    val rates = mapOf(
        "IDR" to 1.0,
        "USD" to 16703.06,
        "EUR" to 19048.17,
        "JPY" to 117.2968,
        "SGD" to 12779.69,
        "AUD" to 10738.4,
        "MYR" to 3862.87,
        "CNY" to 2300.19,
        "KRW" to 11.66,
        "GBP" to 22418.85
    )

    fun convert(value: String, from: String, to: String): String {
        val amount = value.toDoubleOrNull() ?: return ""
        val fromRate = rates[from] ?: 1.0
        val toRate = rates[to] ?: 1.0
        val converted = amount * (toRate / fromRate)
        return String.format("%.2f", converted)
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Row dengan jarak antar dropdown
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp) // Menambah jarak antar dropdown
        ) {
            // Dropdown pertama (Kurs Asal)
            CurrencyDropdown(
                label = "Kurs Asal",
                selectedCurrency = fromCurrency,
                currencies = rates.keys.toList(),
                onSelected = {fromCurrency = it},
                modifier = Modifier.weight(2f)
            )

            Icon(
                painter = painterResource(R.drawable.baseline_swap_horiz_24),
                contentDescription = "Arrow",
                modifier = Modifier
                    .size(32.dp) // Ukuran ikon yang lebih kecil
                    .align(Alignment.CenterVertically) // Menyelaraskan ikon ke tengah vertikal
            )

            // Dropdown kedua (Kurs Tujuan)
            CurrencyDropdown(
                label = "Kurs Tujuan",
                selectedCurrency = toCurrency,
                currencies = rates.keys.toList(),
                onSelected = {toCurrency = it},
                modifier = Modifier.weight(2f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Input nominal
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Nominal Asal")
                OutlinedTextField(
                    value = amountFrom,
                    onValueChange = { newValue ->
                        // Validasi hanya menerima angka
                        if (newValue.all { it.isDigit() || it == '.' }) {
                            amountFrom = newValue
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text("Nominal Tujuan")
                OutlinedTextField(
                    value = amountTo,
                    onValueChange = { newValue ->
                        // Validasi hanya menerima angka
                        if (newValue.all { it.isDigit() || it == '.' }) {
                            amountTo = newValue
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tombol untuk konversi
        Button(
            onClick = {
                // Melakukan konversi saat tombol ditekan
                if (isFromInput) {
                    amountTo = convert(amountFrom, fromCurrency, toCurrency)
                } else {
                    amountFrom = convert(amountTo, toCurrency, fromCurrency)
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Konversi")
        }
    }

}

@Composable
fun CurrencyDropdown(
    label: String,
    selectedCurrency: String,
    currencies: List<String>,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    // Menyimpan modifier untuk menyamakan lebar komponen
    val dropdownModifier = Modifier.fillMaxWidth()

    Column(modifier = modifier) {
        // Label untuk dropdown
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 4.dp)
        )

        // Tombol untuk memilih mata uang
        OutlinedButton(
            onClick = { expanded = !expanded },
            modifier = dropdownModifier.height(56.dp), // Menetapkan tinggi tombol
            shape = MaterialTheme.shapes.small, // Rounded corners
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary) // Border dengan warna primer
        ) {
            Text(
                text = selectedCurrency,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
            )
            Spacer(modifier = Modifier.weight(1f)) // Menggeser ikon ke kanan
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown Arrow",
                modifier = Modifier.size(24.dp) // Ukuran ikon lebih kecil
            )
        }

        // Dropdown menu untuk menampilkan daftar mata uang
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = dropdownModifier // Menetapkan lebar dropdown sama dengan tombol
        ) {
            // Column untuk dropdown yang scrollable
            Column(
                modifier = Modifier
                    .heightIn(max = 200.dp)  // Menetapkan batas tinggi untuk dropdown
                    .verticalScroll(rememberScrollState())  // Menambahkan scroll pada Column
            ) {
                currencies.forEach { currency ->
                    DropdownMenuItem(
                        text = {
                            Text(currency, style = MaterialTheme.typography.bodyMedium)
                        },
                        onClick = {
                            onSelected(currency)
                            expanded = false
                        },
                        modifier = Modifier
                            .padding(8.dp) // Memberikan padding pada tiap item
                            .fillMaxWidth() // Memastikan lebar item sama dengan tombol dropdown
                    )
                }
            }
        }
    }
}

