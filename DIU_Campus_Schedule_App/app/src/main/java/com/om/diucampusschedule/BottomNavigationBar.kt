package com.om.diucampusschedule

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
        NavigationBarItem(
            selected = currentDestination == "class_routine",
            onClick = { navController.navigate("class_routine") },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.class_routine_icon),
                    contentDescription = "Class Routine"
                )
           },
            label = { Text("Class Routine") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                unselectedIconColor = MaterialTheme.colorScheme.primary
            )
        )
        NavigationBarItem(
            selected = currentDestination == "tasks",
            onClick = { navController.navigate("tasks") },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.tasks_icon),
                    contentDescription = "Tasks"
                )
            },
            label = { Text("Tasks") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                unselectedIconColor = MaterialTheme.colorScheme.primary
            )
        )
        NavigationBarItem(
            selected = currentDestination == "settings",
            onClick = { navController.navigate("settings") },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.settings_icon),
                    contentDescription = "Settings"
                )
            },
            label = { Text("Settings") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                unselectedIconColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}
