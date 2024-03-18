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

class Hexadecimal : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraAA5000Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HexadecimalScreen()
                }
            }
        }
    }
}
@Composable
fun HexadecimalScreen() {
    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var currentOperator by remember { mutableStateOf("") }
    var operation by remember { mutableStateOf("") }
    var resultHex by remember { mutableStateOf("") }
    var resultOct by remember { mutableStateOf("") }
    var resultDec by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Operacion: $operation")
        Spacer(modifier = Modifier.height(16.dp))
        Text("Resultado Hexadecimal: $resultHex")
        Text("Resultado Octal: $resultOct")
        Text("Resultado Decimal: $resultDec")
        Spacer(modifier = Modifier.height(16.dp))

        // Filas de botones
        (0..9 step 5).forEach { startIndex ->
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                for (i in startIndex until minOf(startIndex + 5, 10)) {
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

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf("A", "B", "C", "D", "E", "F").forEach { digit ->
                DigitButton(digit) {
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
                val (resultH, resultO, resultD) = performOperation(currentOperator, num1, num2)
                resultHex = resultH
                resultOct = resultO
                resultDec = resultD
            }
        }) {
            Text(text = "=")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            operation = ""
            resultHex = ""
            resultOct = ""
            resultDec = ""
            num1 = ""
            num2 = ""
            currentOperator = ""
        }) {
            Text(text = "Borrar")
        }
    }
}




@Composable
fun DigitButton(digit: String, onClick: (String) -> Unit) {
    Button(onClick = { onClick(digit) }) {
        Text(text = digit)
    }
}

@Composable
fun OperatorButton(operator: String, onClick: (String) -> Unit) {
    Button(onClick = { onClick(operator) }) {
        Text(text = operator)
    }
}

fun performOperation(operator: String, num1: String, num2: String): Triple<String, String, String> {
    val number1 = hexStringToDouble(num1)
    val number2 = hexStringToDouble(num2)
    val calculatedResult = when (operator) {
        "+" -> number1 + number2
        "-" -> number1 - number2
        "*" -> number1 * number2
        "/" -> if (number2 != 0.0) number1 / number2 else 0.0
        else -> 0.0
    }

    val resultInt = calculatedResult.toInt()
    val resultHex = Integer.toHexString(resultInt)
    val resultOct = Integer.toOctalString(resultInt)
    val resultDec = resultInt.toString()

    return Triple(resultHex, resultOct, resultDec)
}
fun hexStringToDouble(hex: String): Double {
    return hex.toLongOrNull(16)?.toDouble() ?: 0.0
}