package com.om.diucampusschedule


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.om.diucampusschedule.models.Task
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.io.File
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import com.om.diucampusschedule.utils.readTasksFromCsv
import com.om.diucampusschedule.utils.writeTasksToCsv
import java.util.UUID
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.style.TextOverflow
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import java.util.*
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.text.TextStyle

@Composable
fun TasksPage(navController: NavHostController) {
    val context = LocalContext.current
    val csvFile = File(context.filesDir, "tasks.csv")

    var taskList by remember { mutableStateOf(listOf<Task>()) }
    var showDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            taskList = readTasksFromCsv(csvFile)
        }
    }

    Scaffold(
        topBar = { AppTopBarWithAppName() },
        bottomBar = { BottomNavigationBar(navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Task")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        val pendingTasks = taskList.filter { !it.isCompleted }
        val completedTasks = taskList.filter { it.isCompleted }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 0.dp, start = 16.dp, end = 16.dp, bottom = 0.dp)
        ) {
            item {
                Text(
                    text = "Pending Tasks",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            items(pendingTasks) { task ->
                TaskCard(task = task, onUpdateTask = { updatedTask ->
                    taskList = taskList.map { if (it.id == task.id) updatedTask else it }
                    coroutineScope.launch { writeTasksToCsv(csvFile, taskList) }
                }, onDeleteTask = { deletedTask ->
                    taskList = taskList.filter { it.id != deletedTask.id }
                    coroutineScope.launch { writeTasksToCsv(csvFile, taskList) }
                })
            }
            item {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .height(1.dp)
                )
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Completed Tasks",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                    Text(
                        text = "Delete All",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .padding(vertical = 2.dp)
                            .clickable {
                                taskList = taskList.filter { !it.isCompleted }
                                coroutineScope.launch { writeTasksToCsv(csvFile, taskList) }
                                Toast.makeText(
                                    context,
                                    "All completed tasks deleted",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    )
                }
            }
            items(completedTasks) { task ->
                TaskCard(task = task, onUpdateTask = { updatedTask ->
                    taskList = taskList.map { if (it.id == task.id) updatedTask else it }
                    coroutineScope.launch { writeTasksToCsv(csvFile, taskList) }
                }, onDeleteTask = { deletedTask ->
                    taskList = taskList.filter { it.id != deletedTask.id }
                    coroutineScope.launch { writeTasksToCsv(csvFile, taskList) }
                })
            }
        }
    }

    if (showDialog) {
        AddTaskDialog(
            onDismiss = { showDialog = false },
            onAddTask = { newTask ->
                taskList = taskList + newTask
                coroutineScope.launch { writeTasksToCsv(csvFile, taskList) }
                showDialog = false
            }
        )
    }
}

@Composable
fun AddTaskDialog(onDismiss: () -> Unit, onAddTask: (Task) -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.2f))
        ) {
            Card(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Column {
                    // New Task Heading Card

                    Text(
                        text = "New Task",
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    // Container for Buttons and Text Fields
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Column {
                            var title by remember { mutableStateOf("") }
                            var description by remember { mutableStateOf("") }
                            var date by remember { mutableStateOf("") }
                            var time by remember { mutableStateOf("") }
                            var showError by remember { mutableStateOf(false) }

                            val context = LocalContext.current
                            val calendar = Calendar.getInstance()

                            // Date Picker Dialog
                            val datePickerDialog = DatePickerDialog(
                                context,
                                { _, year, month, dayOfMonth ->
                                    date = "$dayOfMonth/${month + 1}/$year"
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            )

                            // Time Picker Dialog with 12-Hour Format
                            val timePickerDialog = TimePickerDialog(
                                context,
                                { _, hourOfDay, minute ->
                                    val hour = if (hourOfDay % 12 == 0) 12 else hourOfDay % 12
                                    val amPm = if (hourOfDay >= 12) "PM" else "AM"
                                    time = String.format("%02d:%02d %s", hour, minute, amPm)
                                },
                                calendar.get(Calendar.HOUR_OF_DAY),
                                calendar.get(Calendar.MINUTE),
                                false
                            )

                            OutlinedTextField(
                                value = title,
                                onValueChange = {
                                    title = it
                                    showError = false
                                },
                                label = { Text("Title") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                maxLines = 1,
                                isError = showError
                            )
                            if (showError) {
                                Text(
                                    text = "Title cannot be empty",
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = description,
                                onValueChange = { description = it },
                                label = { Text("Description") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            // Date Picker Button
                            Button(
                                onClick = { datePickerDialog.show() },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(text = if (date.isEmpty()) "Select Date" else date)
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Time Picker Button
                            Button(
                                onClick = { timePickerDialog.show() },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(text = if (time.isEmpty()) "Select Time" else time)
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                TextButton(onClick = onDismiss) {
                                    Text("Cancel")
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(
                                    modifier = Modifier.padding(bottom = 16.dp),
                                    onClick = {
                                        if (title.isEmpty()) {
                                            showError = true
                                        } else {
                                            onAddTask(Task(UUID.randomUUID(), title, description, date, time, false))
                                            onDismiss()
                                        }
                                    }
                                ) {
                                    Text("Add Task")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TaskCard(task: Task, onUpdateTask: (Task) -> Unit, onDeleteTask: (Task) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var offsetX by remember { mutableStateOf(0f) }
    val animatedOffsetX by animateDpAsState(targetValue = offsetX.dp)

    val swipeThreshold = 200f // Threshold to delete the task
    val maxSwipeDistance = 250f // Max distance to prevent swiping too far

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(
                color = if (offsetX > 0) Color(0xFFBE3144) else MaterialTheme.colorScheme.surfaceContainer,
                shape = MaterialTheme.shapes.large
            )
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        if (offsetX < swipeThreshold) {
                            offsetX = 0f // Reset the position if not swiped enough
                        }
                    }
                ) { change, dragAmount ->
                    change.consume()
                    offsetX = (offsetX + dragAmount / density).coerceIn(0f, maxSwipeDistance)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete",
            tint = Color.White,
            modifier = Modifier
                .size(38.dp)
                .align(Alignment.CenterStart)
                .padding(12.dp, 0.dp, 0.dp, 0.dp)
        )
        ElevatedCard(
            shape = MaterialTheme.shapes.large,
            onClick = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = animatedOffsetX),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp, // Larger font size for title
                        modifier = Modifier.weight(1f),
                        maxLines = if (expanded) Int.MAX_VALUE else 1, // Ensuring title is displayed in a single line when not expanded
                        overflow = TextOverflow.Ellipsis // Ellipsis for long titles
                    )
                    val animatedColor by animateColorAsState(
                        targetValue = MaterialTheme.colorScheme.primary,
                        animationSpec = tween(500)
                    )
                    val scale by animateFloatAsState(
                        targetValue = if (task.isCompleted) 1.2f else 1f,
                        animationSpec = tween(500)
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp) // Increase the clickable area
                                .clip(CircleShape)
                                .background(
                                    color = Color.Transparent,
                                    shape = CircleShape
                                )
                                .clickable {
                                    onUpdateTask(task.copy(isCompleted = !task.isCompleted))
                                }
                                .scale(scale),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (task.isCompleted) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle,
                                contentDescription = "Check",
                                tint = animatedColor,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
                AnimatedVisibility(
                    visible = expanded,
                    enter = expandVertically(animationSpec = tween(300)) + fadeIn(animationSpec = tween(300)),
                    exit = shrinkVertically(animationSpec = tween(300)) + fadeOut(animationSpec = tween(300))
                ) {
                    Column {
                        Text(
                            text = task.description,
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 14.sp
                        )
                        if (task.date.isNotEmpty() && task.time.isNotEmpty()) {
                            Text(
                                text = "${task.date} at ${task.time}",
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }

        if (offsetX >= swipeThreshold) {
            // Delay the reset to allow the deletion to complete
            LaunchedEffect(Unit) {
                offsetX = 0f
                onDeleteTask(task)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun defaultPreview() {
    val navController = rememberNavController()
//    TasksPage(navController)
//    AddTaskDialog() { }
}

