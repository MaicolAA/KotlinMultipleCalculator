package com.example.calculadoraaa5000

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.calculadoraaa5000.styles.h5TextStyle
import com.example.calculadoraaa5000.ui.theme.CalculadoraAA5000Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraAA5000Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        navigateToBinary = { navigateToBinary() },
                        navigateToDecimal = { navigateToDecimal() },
                        navigateToHexadecimal = { navigateToHexadecimal() }
                    )
                }
            }
        }
    }

    private fun navigateToBinary() {
        startActivity(Intent(this, Binary::class.java))
    }

    private fun navigateToHexadecimal() {
        startActivity(Intent(this, Hexadecimal::class.java))
    }
    private fun navigateToDecimal() {
        startActivity(Intent(this, Decimal::class.java))
    }

}

@Composable
fun MainScreen(
    navigateToBinary: () -> Unit,
    navigateToHexadecimal: () -> Unit,
    navigateToDecimal: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenido a las calculadoras de Maicol AA",
            style = h5TextStyle,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Button(
            onClick = navigateToDecimal,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Calculadora Decimal")
        }

        Button(
            onClick = navigateToBinary,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Calculadora Binaria")
        }
        Button(
            onClick = navigateToHexadecimal
        ) {
            Text("Calculadora Hexadecimal")
        }
    }
}
