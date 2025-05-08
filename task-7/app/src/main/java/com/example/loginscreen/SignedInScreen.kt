package com.example.loginscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SignedInScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(36.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "SIGNED IN",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp,
                        fontFamily = FontFamily.SansSerif,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Welcome back again! You're Successfully Signed In",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily.SansSerif,
                    )
                )
            }

            Image(
                painter = painterResource(id = R.drawable.welcome),
                contentDescription = "signin ilustration",
                modifier = Modifier.size(300.dp)
            )

        }
    }
}
