package com.example.composedemo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector

interface Navigations {
    val route: String
}

object BottomTabNavigation : Navigations {
    override val route = "BottomTabNavigation"
}

object TaskApp : Navigations {
    const val tasksCompleted = "tasksCompleted"
    override val route = "TaskApp"
}

object ComposeQuadrantApp : Navigations {
    override val route = "ComposeQuadrantApp"
}

object ComposeTutorialApp : Navigations {
    override val route = "ComposeTutorialApp"
}

object LittleLemonApp : Navigations {
    override val route = "LittleLemonApp"
}

object HomePageApp : Navigations {
    override val route = "HomePageApp"
}

object LoginScreenApp : Navigations {
    override val route = "LoginScreenApp"
}

// ================ Bottom Tab Navigation ================
interface BottomNavItem {
    val title: String
    val icon: ImageVector
    val route: String
}

object TaskAppBottomNav : BottomNavItem {
    override val title = "Task App"
    override val icon = Icons.Default.Home
    override val route = TaskApp.route
}

object ComposeTutorialAppBottomNav : BottomNavItem {
    override val title = "Compose Tutorial"
    override val icon = Icons.Default.Home
    override val route = ComposeTutorialApp.route
}

object ComposeQuadrantAppBottomNav : BottomNavItem {
    override val title = "Compose Quadrant"
    override val icon = Icons.Default.ThumbUp
    override val route = ComposeQuadrantApp.route
}

object LittleLemonAppBottomNav : BottomNavItem {
    override val title = "Little Lemon"
    override val icon = Icons.Default.ShoppingCart
    override val route = LittleLemonApp.route
}

object LoginScreenAppBottomNav : BottomNavItem {
    override val title = "Login"
    override val icon = Icons.Default.Settings
    override val route = LoginScreenApp.route
}