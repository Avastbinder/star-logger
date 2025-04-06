package com.example.star_log

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.star_log.ui.theme.StarlogTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.ui.text.style.TextAlign

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StarlogTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.star_background),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        )
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Main Menu", fontSize = 24.sp, color = Color.White)

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,  // Change button background color
                contentColor = Color.White    // Change button text color
            ),
            shape = RoundedCornerShape(16.dp),
            onClick = {
            val intent = Intent(context, LogActivity::class.java)
            context.startActivity(intent)
        },
            modifier = modifier
                .width(400.dp)
                .height(80.dp)
        ) {
            Text("Logs", fontSize = 18.sp)
        }

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,  // Change button background color
                contentColor = Color.LightGray    // Change button text color
            ),
            shape = RoundedCornerShape(16.dp),
            onClick = {
            val intent = Intent(context, CollectionActivity::class.java)
            context.startActivity(intent)
        },
            modifier = modifier
                .width(400.dp)
                .height(80.dp)
        ) {
            Text("Collect data", fontSize = 18.sp)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = "Created by: Aidan Vastbinder",
                fontSize = 8.sp,
                color = Color.LightGray,
                textAlign = TextAlign.Center
            )
        }

    }
}

@Composable
fun GreetingPreview() {
    StarlogTheme {
        Greeting("Android")
    }
}