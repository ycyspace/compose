package com.ye.compose

import Feed
import Profile
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ye.compose.ui.AppViewModel
import login
import kotlin.system.exitProcess


@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun start(appViewModel: AppViewModel){
    val mainNavController = rememberNavController()
    NavHost(navController = mainNavController, startDestination = "home" ){
        composable("home"){home(appViewModel,mainNavController)}
        composable("login"){login(mainNavController, appViewModel)}
    }
}
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun home(appViewModel: AppViewModel,mainNavController: NavController) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = isSystemInDarkTheme()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent ,
            darkIcons = !useDarkIcons
        )
    }
    val bottomNavController = rememberNavController()
    var title by remember { mutableStateOf("首页") }
        Scaffold(
            topBar = {
                SmallTopAppBar(title = { Text(text = title,style = MaterialTheme.typography.titleMedium)})
            },
            bottomBar = {
                NavigationBar(
                ) {
                    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(imageVector = screen.icon , contentDescription ="" )},
                            label = { Text(text =screen.title,style = MaterialTheme.typography.labelMedium)},
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                bottomNavController.navigate(screen.route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(bottomNavController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    title= screen.title
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding -> NavHost(bottomNavController, startDestination = Screen.Feed.route, Modifier.padding(innerPadding)
        ) {
            composable(Screen.Profile.route) { Profile(mainNavController,appViewModel)}
            composable(Screen.Feed.route){Feed(mainNavController,appViewModel)}
        }
        }
}
sealed class Screen(val route: String, val title:String, val icon: ImageVector) {
    object Feed : Screen("feed","首页", Icons.Default.Home)
    object Profile : Screen("profile", "我的", Icons.Default.Person)

}
val items = listOf(
    Screen.Feed,
    Screen.Profile,

    )