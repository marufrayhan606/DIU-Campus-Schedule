package com.om.diucampusschedule


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
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
import com.om.diucampusschedule.ui.theme.DIUCampusScheduleTheme

import kotlinx.coroutines.launch
import readTasksFromCsv
import writeTasksToCsv


@Composable
fun TasksPage(navController: NavHostController) {
    val context = LocalContext.current
    val csvFile = File(context.filesDir, "tasks.csv")

    var taskList by remember { mutableStateOf(listOf<Task>()) }
    var taskIdCounter by remember { mutableStateOf(1) }
    var showDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            taskList = readTasksFromCsv(csvFile)
            taskIdCounter = taskList.size + 1
        }
    }

    Scaffold(
        topBar = { AppTopBarWithAppName() },
        bottomBar = { BottomNavigationBar(navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
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
                    modifier = Modifier.padding(vertical = 2.dp)
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
                                Toast.makeText(context, "All completed tasks deleted", Toast.LENGTH_SHORT).show()
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
                taskList = taskList + newTask.copy(id = taskIdCounter)
                taskIdCounter++
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
                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
        ) {
            Card(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var title by remember { mutableStateOf("") }
                    var description by remember { mutableStateOf("") }
                    var date by remember { mutableStateOf("") }
                    var time by remember { mutableStateOf("") }

                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = date,
                        onValueChange = { date = it },
                        label = { Text("Date") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = time,
                        onValueChange = { time = it },
                        label = { Text("Time") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1
                    )
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
                            onClick = {
                                onAddTask(Task(0, title, description, date, time, false))
                                onDismiss()
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

@Composable
fun TaskCard(task: Task, onUpdateTask: (Task) -> Unit, onDeleteTask: (Task) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f),
                    maxLines = 1 // Ensuring title is displayed in a single line
                )
                val animatedColor by animateColorAsState(
                    targetValue = if (task.isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary,
                    animationSpec = tween(500)
                )
                val scale by animateFloatAsState(
                    targetValue = if (task.isCompleted) 1.2f else 1f,
                    animationSpec = tween(500)
                )
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
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(animationSpec = tween(300)) + fadeIn(animationSpec = tween(300)),
                exit = shrinkVertically(animationSpec = tween(300)) + fadeOut(animationSpec = tween(300))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column{
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
                    // Delete button
                    IconButton(onClick = { onDeleteTask(task) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Task",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}


/*@Preview(showBackground = true)
@Composable
fun TasksPagePreview() {
    DIUCampusScheduleTheme {
        TasksPage(navController = rememberNavController())
    }
}*/
