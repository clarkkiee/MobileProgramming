package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CalculatorApp()
                }
            }
        }
    }
}

@Composable
fun CalculatorApp() {
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = input.ifEmpty { "0" },
            fontSize = 48.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = result.ifEmpty { "0" },
            fontSize = 32.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.height(24.dp))

        val buttons = listOf<List<String>>(
            listOf("\u232B", "(", ")", "%"),
            listOf("7", "8", "9", "/"),
            listOf("4", "5", "6", "*"),
            listOf("1", "2", "3", "-"),
            listOf("C", "0", "=", "+"),
        )

        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                row.forEach { text ->
                   CalculatorButton(
                       text, onClick = {
                           when (text) {
                               "C" -> {
                                   input = ""
                                   result = ""
                               }

                               "=" -> {
                                   result = try {
                                       evalExpression(input)
                                   } catch (e: Exception) {
                                       "Error"
                                   }
                               }

                               "\u232B" -> {
                                   if (input.isNotEmpty()) {
                                       input = input.dropLast(1)
                                   }
                               }

                               else -> {
                                   input += text
                               }
                           }
                       }
                   )
                }
            }
        }

    }
}

@Composable
fun CalculatorButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(8.dp)
            .size(72.dp)
    ) {
        Text(text, fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}

fun evalExpression(expression: String): String {
    return try {
        val tokens = tokenize(expression)
        val postfix = infixToPostfix(tokens)
        val result = evaluatePostfix(postfix)
        return result.toString()
    } catch(e: Exception) {
        "Error"
    }
}

fun tokenize(expression: String): List<String> {
    val regex = Regex("([0-9]+(?:\\.[0-9]+)?)|([+\\-*/%()])")
    return regex.findAll(expression).map { it.value }.toList()
}

fun infixToPostfix(tokens: List<String>): List<String> {
    val output = mutableListOf<String>()
    val ops = mutableListOf<String>()

    val precedence = mapOf(
        "+" to 1, "-" to 1,
        "*" to 2, "/" to 2, "%" to 2,
        "(" to 0, ")" to 0,
    )

    for (token in tokens) {
        when {
            token.toDoubleOrNull() != null -> output.add(token)
            token == "(" -> ops.add(token)
            token == ")" -> {
                while (ops.isNotEmpty() && ops.last() != "(") {
                    output.add(ops.removeAt(ops.size - 1))
                }
                ops.removeAt(ops.size -  1)
            }

            else -> {
                while (ops.isNotEmpty() && precedence[ops.last()]!! >= precedence[token]!!) {
                    output.add(ops.removeAt(ops.size - 1))
                }
                ops.add(token)
            }
        }
    }

    while (ops.isNotEmpty()) {
        output.add(ops.removeAt(ops.size - 1))
    }

    return output
}

fun evaluatePostfix(postfix: List<String>): Double {
    val stack = mutableListOf<Double>()

    for (token in postfix) {
        when {
            token.toDoubleOrNull() != null -> stack.add(token.toDouble())
            token in listOf("+", "-", "/", "*", "%") -> {
                val operand2 = stack.removeAt(stack.size - 1)
                val operand1 = stack.removeAt(stack.size - 1)

                val result = when (token) {
                    "+" -> operand1 + operand2
                    "-" -> operand1 - operand2
                    "/" -> operand1 / operand2
                    "*" -> operand1 * operand2
                    "%" -> operand1 % operand2
                    else -> throw IllegalArgumentException("Invalid Operator")
                }

                stack.add(result)
            }
        }
    }

    return stack.last()
}

@Preview(showBackground = true)
@Composable
fun CalculatorAppPreview() {
    CalculatorTheme {
        CalculatorApp()
    }
}