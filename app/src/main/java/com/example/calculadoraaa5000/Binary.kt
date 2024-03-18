package com.example.calculadoraaa5000

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.calculadoraaa5000.ui.theme.CalculadoraAA5000Theme
import kotlin.math.min

class Binary : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraAA5000Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BinaryScreen()
                }
            }
        }
    }
}
@Composable
fun BinaryScreen() {
    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var currentOperator by remember { mutableStateOf("") }
    var operation by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Operación: $operation")
        Spacer(modifier = Modifier.height(16.dp))
        Text("Resultado: $result")
        Spacer(modifier = Modifier.height(16.dp))

        // Filas de botones
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf("0", "1").forEach { digit ->
                DigitButtonBinary(digit) {
                    if (currentOperator.isEmpty()) {
                        num1 += it
                    } else {
                        num2 += it
                    }
                    operation += it
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf("+", "-", "*", "/").forEach { operator ->
                OperatorButtonBinary(operator) {
                    currentOperator = operator
                    operation += " $operator "
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de igual
        Button(onClick = {
            if (num1.isNotEmpty() && num2.isNotEmpty() && currentOperator.isNotEmpty()) {
                result = performOperation(currentOperator, num1.toInt(2), num2.toInt(2)).toString(2)
            }
        }) {
            Text(text = "=")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            operation = ""
            result = ""
            num1 = ""
            num2 = ""
            currentOperator = ""
        }) {
            Text(text = "Borrar")
        }
    }
}

@Composable
fun DigitButtonBinary(digit: String, onClick: (String) -> Unit) {
    Button(onClick = { onClick(digit) }) {
        Text(text = digit)
    }
}

@Composable
fun OperatorButtonBinary(operator: String, onClick: (String) -> Unit) {
    Button(onClick = { onClick(operator) }) {
        Text(text = operator)
    }
}

fun performOperation(operator: String, num1: Int, num2: Int): Int {
    return when (operator) {
        "+" -> num1 + num2
        "-" -> num1 - num2
        "*" -> num1 * num2
        "/" -> if (num2 != 0) num1 / num2 else 0
        else -> 0
    }
}
