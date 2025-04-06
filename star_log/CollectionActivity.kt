package com.example.star_log

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.runtime.*



class CollectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StarlogTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GreetingCol(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                    GetData(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun GreetingCol(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.star_background),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            alignment = Alignment.CenterEnd,
            modifier = Modifier.fillMaxSize()
        )
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Collection Menu", fontSize = 24.sp, color = Color.LightGray)

        Spacer(modifier = Modifier.height(2.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,  // Change button background color
                contentColor = Color.LightGray    // Change button text color
            ),
            shape = RoundedCornerShape(12.dp),
            onClick = {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        },
            modifier = modifier
                .width(80.dp)
                .height(40.dp)
        ) {
            Text("Back", fontSize = 12.sp)
        }
    }
}

@Composable
fun GetData(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var locationInput by remember {mutableStateOf("")}
    var azimuthInput by remember {mutableStateOf("")}
    var altitudeInput by remember {mutableStateOf("")}
    var timeInput by remember {mutableStateOf("")}
    var dateInput by remember {mutableStateOf("")}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "\n\n\n\nLog Entry", fontSize = 20.sp, color = Color.LightGray)

        OutlinedTextField(
            value = locationInput,
            onValueChange = {locationInput = it},
            label = {Text("Enter in location (preferably zip code)")},
            modifier = Modifier.fillMaxWidth(),

        )

        OutlinedTextField(
            value = azimuthInput,
            onValueChange = {azimuthInput = it},
            label = {Text("Enter in azimuth of object")},
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = altitudeInput,
            onValueChange = {altitudeInput = it},
            label = {Text("Enter in altitude of object (not height)")},
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = timeInput,
            onValueChange = {timeInput = it},
            label = {Text("Enter in current time (preferably in local time)")},
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = dateInput,
            onValueChange = {dateInput = it},
            label = {Text("Enter in current date (preferably in local time)")},
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,  // Change button background color
                contentColor = Color.LightGray    // Change button text color
            ),
            shape = RoundedCornerShape(12.dp),
            onClick = {
            context.openFileOutput("log_entries.txt", Context.MODE_APPEND).use {
                it.write(("\n{" + locationInput + "," + azimuthInput + ","
                        + altitudeInput + "," + timeInput + "," + dateInput + "}").toByteArray())
            }
            locationInput = ""
            azimuthInput = ""
            altitudeInput = ""
            timeInput = ""
            dateInput = ""
        }) {
            Text("Save log")
        }
    }
}


@Composable
fun GreetingColPreview() {
    StarlogTheme {
        GreetingCol("Android")
    }
}