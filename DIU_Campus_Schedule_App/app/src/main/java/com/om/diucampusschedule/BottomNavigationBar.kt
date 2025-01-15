package com.om.diucampusschedule

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.om.diucampusschedule.ui.theme.DIUCampusScheduleTheme

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
        NavigationBarItem(
            selected = currentDestination == "class_routine",
            onClick = { navController.navigate("class_routine") },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.class_routine_icon),
                    contentDescription = "Class Routine",
                    modifier = Modifier.scale(0.7f)
                )
           },
            label = { Text("Class Routine", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                unselectedIconColor = MaterialTheme.colorScheme.primary
            )
        )
        NavigationBarItem(
            selected = currentDestination == "tasks",
            onClick = { navController.navigate("tasks") },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.tasks_icon),
                    contentDescription = "Tasks",
                    modifier = Modifier.scale(0.7f)
                )
            },
            label = { Text("Tasks", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                unselectedIconColor = MaterialTheme.colorScheme.primary
            )
        )
        NavigationBarItem(
            selected = currentDestination == "settings",
            onClick = { navController.navigate("settings") },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.settings_icon),
                    contentDescription = "Settings",
                    modifier = Modifier.scale(0.7f)
                )
            },
            label = { Text("Settings", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                unselectedIconColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}

// for preview
@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    DIUCampusScheduleTheme {
        val navController = rememberNavController()
        BottomNavigationBar(navController = navController)
    }
}
