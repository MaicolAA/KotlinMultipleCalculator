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

class Decimal : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraAA5000Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DecimalScreen()
                }
            }
        }
    }
}

@Composable
fun DecimalScreen() {
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
        (0..9 step 3).forEach { startIndex ->
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                for (i in startIndex until minOf(startIndex + 3, 10)) {
                    DigitButton(i.toString()) {
                        if (currentOperator.isEmpty()) {
                            num1 += it
                        } else {
                            num2 += it
                        }
                        operation += it
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf("+", "-", "*", "/").forEach { operator ->
                OperatorButton(operator) {
                    currentOperator = operator
                    operation += " $operator "
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de igual
        Button(onClick = {
            if (num1.isNotEmpty() && num2.isNotEmpty() && currentOperator.isNotEmpty()) {
                result = performOperationDecimal(currentOperator, num1, num2)
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




fun performOperationDecimal(operator: String, num1: String, num2: String): String {
    val number1 = num1.toDouble()
    val number2 = num2.toDouble()
    val calculatedResult = when (operator) {
        "+" -> number1 + number2
        "-" -> number1 - number2
        "*" -> number1 * number2
        "/" -> if (number2 != 0.0) number1 / number2 else 0.0
        else -> 0.0
    }

    return calculatedResult.toString()
}
