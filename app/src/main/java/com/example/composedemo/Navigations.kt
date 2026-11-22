package com.example.composedemo

interface Navigations {
    val route: String
}

object ScreenNavigation : Navigations {
    override val route = "ScreenNavigation"
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