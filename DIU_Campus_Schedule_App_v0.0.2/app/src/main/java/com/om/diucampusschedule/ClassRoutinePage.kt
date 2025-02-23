package com.om.diucampusschedule

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassRoutinePage(navController: NavHostController) {
    val context = LocalContext.current
    var selectedImage by rememberSaveable { mutableStateOf(loadSelectedImage(context)) }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var showFullScreenImage by rememberSaveable { mutableStateOf(false) }
    var showNoRoutineFound by rememberSaveable { mutableStateOf(false) }
    var selectedDate by rememberSaveable { mutableStateOf(LocalDate.now()) }

    Scaffold(
        topBar = { AppTopBarWithAppName() },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .padding(top = 0.dp, start = 16.dp, end = 16.dp, bottom = 0.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
           item{
               // Mini Calendar
               CollapsibleMiniCalendar(
                   selectedDate = selectedDate,
                   onDateSelected = { selectedDate = it }
               )

               Spacer(modifier = Modifier.height(16.dp))


               // Search Bar Field
               OutlinedTextField(
                   value = searchQuery,
                   onValueChange = {
                       searchQuery = it
                       /*selectedImage = null*/
                       showNoRoutineFound = false
                   },
                   label = { Text("Search routine") },
                   modifier = Modifier.fillMaxWidth(),
                   leadingIcon = {
                       Icon(
                           imageVector = Icons.Filled.Search,
                           contentDescription = "Search Icon",
                           modifier = Modifier.size(20.dp)
                       )
                   },
                   shape = RoundedCornerShape(26.dp),
                   singleLine = true
               )

               Spacer(modifier = Modifier.height(16.dp))

               // Search Button
               Button(
                   onClick = {
                       val imageResource = findImageResource(searchQuery.uppercase(), context)
                       if(imageResource != null){
                           selectedImage = imageResource
                           saveSelectedImage(context, imageResource)
                           showNoRoutineFound = false
                       }
                       else {
                           /*selectedImage = null
                           showNoRoutineFound = true*/
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
                       .padding(bottom = 16.dp)
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
                           modifier = Modifier
                               .align(Alignment.Center)
                               .fillMaxWidth()
                               .fillMaxHeight(),
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
                               "Who needs a boyfriend or girlfriend when your developer understands your pain better than anyone else?\uD83D\uDC94 While they’re busy forgetting your favorite coffee order, We're over here coding an app that remembers your schedule, sends you reminders, and even saves you from the endless routine-hunting nightmare.\n" +
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
}

@Composable
fun FullScreenImageDialog(imageResId: Int, onDismiss: () -> Unit) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, panChange, _ ->
        scale = (scale * zoomChange).coerceIn(1f, 4f) //limit room for 1x to 4x
        val extraWidth = (scale - 1) * 1000 // adjust the value as needed
        val extraHeight = (scale - 1) * 1000 // adjust the value as needed
        val maxX = extraWidth / 2
        val maxY = extraHeight / 2

        offset = Offset(
            x = (offset.x + panChange.x).coerceIn(-maxX, maxX),
            y = (offset.y + panChange.y).coerceIn(-maxY, maxY)
        )
    }
    val coroutineScope = rememberCoroutineScope()

    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onDoubleTap = {
                                coroutineScope.launch {
                                    scale = if (scale > 1f) 1f else 2f
                                    offset = Offset.Zero
                                }
                            }
                        )
                    }
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

// Function to save the selected image in SharedPreferences
fun saveSelectedImage(context: Context, imageResId: Int?) {
    val sharedPreferences = context.getSharedPreferences("RoutinePreferences", Context.MODE_PRIVATE)
    sharedPreferences.edit().putInt("selectedImage", imageResId ?: -1).apply()
}

// Function to load the selected image from SharedPreferences
fun loadSelectedImage(context: Context): Int? {
    val sharedPreferences = context.getSharedPreferences("RoutinePreferences", Context.MODE_PRIVATE)
    val imageResId = sharedPreferences.getInt("selectedImage", -1)
    return if (imageResId != -1) imageResId else null
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