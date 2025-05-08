package com.example.loginscreen

import android.content.Context
import android.util.Patterns
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.Typeface
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(36.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.signin),
                contentDescription = "signin ilustration",
                modifier = Modifier.size(250.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "SIGN IN",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp,
                        fontFamily = FontFamily.SansSerif,
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Sign in to your account",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily.SansSerif,
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                label = { Text("Email") },
                onValueChange = {
                    email = it
                    isError = !isValidEmail(it)
                },
                supportingText = {
                    if (isError) Text("Format email tidak valid", color = MaterialTheme.colorScheme.error)
                },
                isError = false,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                label = { Text("Password") },
                onValueChange = {
                    password = it
                },
                isError = false,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Forgot Password?",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Right,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.W500
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (email == "admin@gmail.com" && password == "admin123") {
                        navController.navigate("signedin")
                    } else {
                        email = ""
                        password = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Sign In",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 36.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Divider(
                    modifier = Modifier.weight(1f).height(1.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                )

                Text(
                    text = "Or Sign In with",
                    modifier = Modifier.padding(horizontal = 12.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.SansSerif,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        fontWeight = FontWeight.W500
                    )
                )

                Divider(
                    modifier = Modifier.weight(1f).height(1.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 36.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {

                IconButton(
                    onClick = { navController.navigate("signedin") },
                    modifier = Modifier.size(54.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.facebook),
                        contentDescription = "Sign in",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                IconButton(
                    onClick = { navController.navigate("signedin") },
                    modifier = Modifier.size(54.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "Sign in",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                IconButton(
                    onClick = { navController.navigate("signedin") },
                    modifier = Modifier.size(54.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.github),
                        contentDescription = "Sign in",
                        modifier = Modifier.fillMaxSize()
                    )
                }

            }


        }
    }

}

fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
