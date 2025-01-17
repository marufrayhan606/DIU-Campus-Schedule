package com.om.diucampusschedule

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun SettingsPage(
    navController: NavHostController,
    isDarkModeEnabled: Boolean,
    onDarkModeToggle: (Boolean) -> Unit
) {
    val context = LocalContext.current
    var showDevelopersInfo by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { AppTopBarWithAppName() },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .padding(top = 0.dp, start = 16.dp, end = 16.dp, bottom = 0.dp)
        ) {
            item {
                // Dark Mode Card
                PremiumCard(title = "Dark Mode", isTogglable = true, isChecked = isDarkModeEnabled, onToggle = { onDarkModeToggle(it) })

                Spacer(modifier = Modifier.height(16.dp))

                // Developers Info Card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(16.dp))
                        .clickable { showDevelopersInfo = !showDevelopersInfo },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "About Us",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End
                            ) {
                                if (!showDevelopersInfo) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ismam_ovi),
                                        contentDescription = "Ismam Ovi",
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Image(
                                        painter = painterResource(id = R.drawable.maruf_rayhan),
                                        contentDescription = "Md. Maruf Rayhan",
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        }
                        AnimatedVisibility(
                            visible = showDevelopersInfo,
                            enter = expandVertically(animationSpec = tween(500)) + fadeIn(animationSpec = tween(500)),
                            exit = shrinkVertically(animationSpec = tween(500))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp)
                            ) {
                                DeveloperProfile(
                                    name = "Ismam Hasan Ovi",
                                    id = "232-35-567",
                                    imageUrl = R.drawable.ismam_ovi,
                                    facebookUrl = "https://www.facebook.com/coder.OVI",
                                    githubUrl = "https://github.com/oviii-001"
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                DeveloperProfile(
                                    name = "Maruf Rayhan",
                                    id = "232-35-623",
                                    imageUrl = R.drawable.maruf_rayhan,
                                    facebookUrl = "https://www.facebook.com/marufrayhan606",
                                    githubUrl = "https://github.com/marufrayhan606"
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Credits Section
                Text(
                    text = "Class Routines provided by:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
                Text(
                    text = "diuroutine.com",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://diuroutine.com"))
                            context.startActivity(intent)
                        }
                )

                Spacer(modifier = Modifier.height(32.dp))

                // App Details Section
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "App Details",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "App Version: 0.0.1 (Beta)",
                            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Red)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "This app was developed by Ismam Hasan Ovi and Md. Maruf Rayhan, students of the SWE Department, Batch 41. The app is designed to help students manage their schedules and tasks effectively.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Credits for the class routines go to diuroutine.com.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "The app is still under active development and we appreciate any feedback to improve the user experience.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PremiumCard(title: String, isTogglable: Boolean = false, isChecked: Boolean = false, onToggle: (Boolean) -> Unit = {}) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                title,
                style = MaterialTheme.typography.titleMedium
            )
            if (isTogglable) {
                Switch(
                    checked = isChecked,
                    onCheckedChange = onToggle
                )
            }
        }
    }
}
@Composable
fun DeveloperProfile(name: String, id: String, imageUrl: Int, facebookUrl: String, githubUrl: String) {
    val context = LocalContext.current
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = imageUrl),
                    contentDescription = name,
                    modifier = Modifier
                        .size(80.dp) // Increased the size for a bigger profile image
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "SWE, Batch 41, ID: $id",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.facebook),
                            contentDescription = "Facebook",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .clickable {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl))
                                    context.startActivity(intent)
                                }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            painter = painterResource(id = R.drawable.github),
                            contentDescription = "Github",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .clickable {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl))
                                    context.startActivity(intent)
                                }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPagePreview() {
    SettingsPage(navController = NavHostController(LocalContext.current), isDarkModeEnabled = false, onDarkModeToggle = {})
}
