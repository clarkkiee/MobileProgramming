package com.example.ppb1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ppb1.ui.theme.Ppb1Theme
import kotlin.math.exp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ppb1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MyApp(
    modifier: Modifier = Modifier
) {
    var showOnBoarding by rememberSaveable { mutableStateOf(true) }
    Surface(modifier) {
        if (showOnBoarding) {
            OnBoardingScreen(onContinueClicked = { showOnBoarding = false })
        } else {
            Contents(onBackClicked = { showOnBoarding = true })
        }
    }
}

@Composable
fun Content(title: String, text: String, modifier: Modifier = Modifier) {

    var expanded by remember { mutableStateOf(false) }

    val extraPadding by animateDpAsState(
        if (expanded) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp).fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(24.dp).fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
                    .align(Alignment.CenterVertically).fillMaxWidth()

            ) {
                Text(text = title, style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center
                    )
                )
                if (expanded) {
                    Text(
                        text = text,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 36.dp)
                    )
                }

            }
            ElevatedButton(
                onClick = { expanded = !expanded }
            ) {
                Text(if (expanded) "Show less" else "Show more")
            }
        }

    }
}

@Composable
private fun Contents(
    modifier: Modifier = Modifier,
    contents: List<Pair<String, String>> = listOf(
        "About Me" to "My name is Aloysius Juan Farrel Lumentut. I'm a third-year Undergraduate Student of Informatics Engineering at Institut Teknologi Sepuluh Nopember.",
        "Interests" to "Juan is very interested in Software Engineering, especially focusing on Backend Development. The main tech stack I use is NodeJS (TypeScript and JavaScript) and Golang. Currently learning to expand into the DevOps domain.",
        "Contacts" to "Email: juanfarrel0404@gmail.com\n\nGithub:\nclarkkiee"
    ),
    onBackClicked: () -> Unit
) {
    Column(modifier = modifier.padding(vertical = 8.dp, horizontal = 2.dp)) {
        ElevatedButton(
            onClick = onBackClicked,
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Back To Home")
        }

        LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
            items(items = contents) { (title, content) ->
                Content(title = title, text = content)
            }
        }
    }
}

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    onContinueClicked: () -> Unit
){
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hello Android",  style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
        ))
        Text(text = "✨I'm Aloysius Juan✨",  style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.Normal, textAlign = TextAlign.Center
        ))
        Text(text = "Tugas 2 - PPB D 2024/2025",  style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.W300, textAlign = TextAlign.Center, fontSize = 18.sp,
        ))
        Button(
            modifier = Modifier.padding(vertical = 16.dp),
            onClick = onContinueClicked
        ) {
            Text("About Me!")
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun OnBoardingScreenPreview() {
    Ppb1Theme {
        OnBoardingScreen(onContinueClicked = {})
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun ContentsPreview(){
    Ppb1Theme {
        Contents(onBackClicked = {})
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun MyAppPreview() {
    Ppb1Theme {
        MyApp(Modifier.fillMaxSize())
    }
}