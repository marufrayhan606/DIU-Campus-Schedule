package com.om.diucampusschedule

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.om.diucampusschedule.ui.theme.DIUCampusScheduleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val sharedPreferences = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

        setContent {
            var isDarkModeEnabled by remember {
                mutableStateOf(sharedPreferences.getBoolean("dark_mode", false))
            }
            LaunchedEffect(isDarkModeEnabled) {
                sharedPreferences.edit().putBoolean("dark_mode", isDarkModeEnabled).apply()
            }
            DIUCampusScheduleTheme(darkTheme = isDarkModeEnabled) {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController)
                    }
                ) {
                    innerPadding ->
                    NavGraph(
                        navController = navController,
                        isDarkModeEnabled = isDarkModeEnabled,
                        onDarkModeToggle = { isDarkModeEnabled = it },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun NavGraph(
    navController: NavHostController,
    isDarkModeEnabled: Boolean,
    onDarkModeToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = "class_routine"){
        composable("class_routine"){ ClassRoutinePage(navController) }  // This is the starting page
        composable("tasks"){ TasksPage(navController) }  // This is the second page
        composable("settings"){
            SettingsPage(
                navController = navController,
                isDarkModeEnabled = isDarkModeEnabled,
                onDarkModeToggle = onDarkModeToggle
            )
        } // This is the third page
    }
}

