package com.example.star_log

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.star_log.ui.theme.StarlogTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.remember
import androidx.compose.foundation.lazy.items
import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.text.style.TextAlign

data class LogEntry(
    val location: String,
    val azimuth: String,
    val altitude: String,
    val time: String,
    val date: String
)

class LogActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StarlogTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GreetingLog(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun GreetingLog(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val initialLogs = remember { getLog(context) }
    val logEntries = remember { mutableStateListOf<LogEntry>().apply { addAll(initialLogs) } }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.star_background),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            alignment = Alignment.CenterStart,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Log Menu", fontSize = 24.sp, color = Color.White)

            Spacer(modifier = Modifier.height(6.dp))

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray,  // Change button background color
                    contentColor = Color.White    // Change button text color
                ),
                shape = RoundedCornerShape(12.dp),
                onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .width(80.dp)
                    .height(40.dp)
            ) {
                Text("Back")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Location | Azimuth | Altitude | Time | Date", fontSize = 18.sp, color = Color.White)

            LazyColumn {
                items(logEntries) { entry ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(Color.DarkGray, RoundedCornerShape(8.dp)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                            Text(
                                text = "\t\t${entry.location} | ${entry.azimuth}° | ${entry.altitude}° | ${entry.time} | ${entry.date}",
                                color = Color.White,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Left
                            )

                        IconButton(onClick = {
                            logEntries.remove(entry)
                            saveLogToFile(context, logEntries)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }

        }
    }
}

fun getLog(context: Context): List<LogEntry> {
    val fileContent = context.openFileInput("log_entries.txt").bufferedReader().use {it.readText()}
    val lines = fileContent.lines()
        .map {it.trim()}
        .filter {it.startsWith("{") && it.endsWith("}")}
    return lines.mapNotNull {line ->
        val values = line.removeSurrounding("{", "}").split(",")

        if (values.size == 5) {
            try{
                LogEntry(
                    location = values[0],
                    azimuth = values[1],
                    altitude = values[2],
                    time = values[3],
                    date = values[4]
                )
            } catch(e: Exception) {
                null
            }
        } else {
            null
        }
    }
}

fun saveLogToFile(context: Context, entries: List<LogEntry>) {
    val data = entries.joinToString("\n") {
        "{${it.location},${it.azimuth},${it.altitude},${it.time},${it.date}}"
    }
    context.openFileOutput("log_entries.txt", Context.MODE_PRIVATE).use {
        it.write(data.toByteArray())
    }
}

@Composable
fun GreetingLogPreview() {
    StarlogTheme {
        GreetingLog()
    }
}