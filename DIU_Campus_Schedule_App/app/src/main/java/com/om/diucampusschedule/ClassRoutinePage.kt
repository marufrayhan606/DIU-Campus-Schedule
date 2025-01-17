package com.om.diucampusschedule

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.om.diucampusschedule.models.ImageNameMapper
import com.om.diucampusschedule.ui.theme.DIUCampusScheduleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassRoutinePage(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedImage by remember { mutableStateOf<Int?>(null) }
    var showFullScreenImage by remember { mutableStateOf(false) }
    var showNoRoutineFound by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { AppTopBarWithAppName() },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 0.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Search Field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    selectedImage = null
                    showNoRoutineFound = false
                },
                label = { Text("Search routine") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Search Button
            val context = LocalContext.current
            Button(
                onClick = {
                    val imageResource = findImageResource(searchQuery.uppercase(), context)
                    if(imageResource != null){
                        selectedImage = imageResource
                        showNoRoutineFound = false
                    }
                    else {
                        selectedImage = null
                        showNoRoutineFound = true
                    }

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Search")
            }

            Spacer(modifier = Modifier.height(50.dp))

            // Image of the class routine here
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                if (selectedImage != null) {
                    Image(
                        painter = painterResource(id = selectedImage!!),
                        contentDescription = "Class Routine",
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { showFullScreenImage = true },
                        contentScale = ContentScale.Fit
                    )
                }
                else if(showNoRoutineFound){
                    Text(
                        "No routine found",
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                }
                else {
                    Column(
                        modifier = Modifier.align(Alignment.Center).fillMaxWidth().fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "The app is under development!",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            textAlign = TextAlign.Center,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            color = Color.Gray
                        )
                        Text(
                            "Who needs a boyfriend or girlfriend when your developer understands your pain better than anyone else?\uD83D\uDC94 While they’re busy forgetting your favorite coffee order, I’m over here coding an app that remembers your schedule, sends you reminders, and even saves you from the endless routine-hunting nightmare.\n" +
                                    "Relationships might give you butterflies, but this app will give you peace of mind – and isn’t that what we all really need?\uD83D\uDE0E❤\uFE0F Until it’s ready to sweep you off your feet, enjoy the chaos, and remember: your developer’s dedication >>> late-night texts.\uD83D\uDE02",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
                            lineHeight = 15.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            // Full Screen Image Dialog
            if (showFullScreenImage && selectedImage != null) {
                FullScreenImageDialog(
                    imageResId = selectedImage!!,
                    onDismiss = { showFullScreenImage = false }
                )
            }
        }
    }
}

@Composable
fun FullScreenImageDialog(imageResId: Int, onDismiss: () -> Unit) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, _ ->
        scale *= zoomChange
        // Increase the sensitivity for translation
        offset += Offset(offsetChange.x * 2f, offsetChange.y * 2f)
    }

    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        translationX = offset.x
                        translationY = offset.y
                    }
                    .transformable(state = state)
            ) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = "Full Screen Class Routine",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

// Function to find the image resource based on the search query
fun findImageResource(searchQuery: String, context: android.content.Context): Int? {
    return ImageNameMapper.getResourseId(searchQuery)
}

// for preview
@Preview(showBackground = true)
@Composable
fun ClassRoutinePagePreview() {
    DIUCampusScheduleTheme {
        val navController = rememberNavController()
        ClassRoutinePage(navController = navController)
    }
}