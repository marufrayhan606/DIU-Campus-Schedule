package com.om.diucampusschedule

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
//import androidx.compose.material.icons.filled.Facebook
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.om.diucampusschedule.ui.theme.DIUCampusScheduleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage(
    navController: NavHostController,
    isDarkModeEnabled: Boolean,
    onDarkModeToggle: (Boolean) -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = { AppTopBarWithAppName()
            /*TopAppBar(
                title = { Text("Settings") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )*/
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 0.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // Dark Mode Card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Dark Mode",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Switch(
                        checked = isDarkModeEnabled,
                        onCheckedChange = { onDarkModeToggle(it) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Developers Info Card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "The-Coders-Who-Coded-It:",
                        style = MaterialTheme.typography.titleMedium
                    )

                    /*Divider(
                        modifier = Modifier.fillMaxWidth().height(1.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )*/

                    // Developer Ovi
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ismam_ovi),
                            contentDescription = "Ismam Ovi",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Ismam Hasan Ovi",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.facebook),
                                    contentDescription = "Facebook",
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "/coder.OVI",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .clickable {
                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/coder.OVI"))
                                            context.startActivity(intent)
                                        }
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.github),
                                    contentDescription = "Github",
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "/oviii-001",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .clickable {
                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/oviii-001"))
                                            context.startActivity(intent)
                                        }
                                )
                            }
                        }
                    }

                    // Developer Maruf
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.maruf_rayhan),
                            contentDescription = "Maruf Rayhan",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Maruf Rayhan",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.facebook),
                                    contentDescription = "Facebook",
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "/marufrayhan606",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .clickable {
                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/marufrayhan606"))
                                            context.startActivity(intent)
                                        }
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.github),
                                    contentDescription = "Facebook",
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "/marufrayhan606",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .clickable {
                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/marufrayhan606"))
                                            context.startActivity(intent)
                                        }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(50.dp))
            Divider(modifier = Modifier.fillMaxWidth().height(1.dp))
            // App Version Card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        "App Version: 0.0.1 (Beta)",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Red)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Code written by day, debugged by night🤯. Developed & still developing by Ismam Ovi & Maruf Rayhan, who may or may not have generated half of it from ChatGPT👀. © 2025 Runs on hope and Ctrl+Z. If something breaks, it’s a skill issue (ours)\uD83D\uDE4F. Good luck!😎",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.5.sp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPagePreview() {
    DIUCampusScheduleTheme {
        val navController = rememberNavController()
        SettingsPage(
            navController = navController,
            isDarkModeEnabled = false,
            onDarkModeToggle = {}
        )
    }
}
