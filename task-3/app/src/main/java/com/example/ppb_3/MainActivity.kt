package com.example.ppb_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ppb_3.ui.theme.Ppb3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ppb3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BirthdayGreeting(
                        modifier = Modifier.padding(innerPadding),
                        from = "Juan",
                        message = "Happy Birthday Sammy!"
                    )
                }
            }
        }
    }
}

@Composable
fun BirthdayGreeting(
    message: String,
    from: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.minimalist_birthday_bg),
            contentDescription = "Birthday Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFAD0C4))
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = message,
                        fontSize = 64.sp,
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 64.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.padding(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Text(
                    text = "From: $from",
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.W700,
                    color = Color(0xFFC599B6),
                    fontSize = 18.sp
                )
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingPreview() {
    Ppb3Theme {
        BirthdayGreeting(
            from = "Juan",
            message = "Happy Birthday Sammy!"
        )
    }
}