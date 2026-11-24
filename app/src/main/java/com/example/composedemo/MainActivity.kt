package com.example.composedemo

import android.annotation.SuppressLint
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApp()
        }

    }
}

// ======================= Main App =======================
@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "main_selection_screen") {
        composable("main_selection_screen") {
            MainSelectionScreen(navController)
        }

        composable(BottomTabNavigation.route) {
            BottomTabNavigation()
        }

        composable(HomePageApp.route) {
            ScreenNavigationApp(navController)
        }

        composable(
            route = TaskApp.route + "/{${TaskApp.tasksCompleted}}",
            arguments = listOf(
                navArgument(TaskApp.tasksCompleted) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            TaskApp(backStackEntry.arguments?.getInt(TaskApp.tasksCompleted))
        }

        composable(ComposeTutorialApp.route) {
            ComposeTutorialApp()
        }

        composable(ComposeQuadrantApp.route) {
            ComposeQuadrantApp()
        }

        composable(LittleLemonApp.route) {
            LittleLemonApp()
        }

        composable(LoginScreenApp.route) {
            LoginScreenApp()
        }
    }
}

@Composable
fun MainSelectionScreen(navController: NavHostController) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        NavButton(
            "Screen Navigation",
            onClick = {
                navController.navigate(HomePageApp.route)
            }
        )

        NavButton(
            "Bottom Tab Navigation",
            onClick = {
                navController.navigate(BottomTabNavigation.route)
            }
        )
    }
}

// ======================= Bottom Tab Navigation =======================
@Composable
fun BottomTabNavigation() {
    val bottomNavController = rememberNavController()

    val destinationList = listOf(
        TaskAppBottomNav,
        ComposeTutorialAppBottomNav,
        ComposeQuadrantAppBottomNav,
        LittleLemonAppBottomNav,
        LoginScreenAppBottomNav
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                destinationList.forEach { item ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            bottomNavController.navigate(item.route) {
                                popUpTo(bottomNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },
                        label = { Text(item.title) }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = bottomNavController,
            startDestination = TaskAppBottomNav.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(TaskApp.route) { TaskApp(null) }
            composable(ComposeTutorialApp.route) { ComposeTutorialApp() }
            composable(ComposeQuadrantApp.route) { ComposeQuadrantApp() }
            composable(LittleLemonApp.route) { LittleLemonApp() }
            composable(LoginScreenApp.route) { LoginScreenApp() }
        }
    }
}

// HomePage
@Composable
fun NavButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(size = 12.dp),
        modifier = Modifier
            .width(250.dp)
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Text(text)
    }
}

// ======================= HomePage App =======================
@Composable
fun ScreenNavigationApp(navController: NavHostController) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    )
    {
        NavButton(
            "Task App",
            onClick = {
                navController.navigate(TaskApp.route + "/10")
            }
        )

        NavButton(
            "Compose Tutorial App",
            onClick = {
                navController.navigate(ComposeTutorialApp.route)
            }
        )

        NavButton(
            "Compose Quadrant App",
            onClick = {
                navController.navigate(ComposeQuadrantApp.route)
            }
        )

        NavButton(
            "Little Lemon App",
            onClick = {
                navController.navigate(LittleLemonApp.route)
            }
        )

        NavButton(
            "LoginScreen App",
            onClick = {
                navController.navigate(LoginScreenApp.route)
            }
        )
    }
}

// ======================= Compose Tutorial App =======================
@Composable
fun BannerImage(modifier: Modifier = Modifier) {
    val image = painterResource(R.drawable.bg_compose_background)

    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
    )
}

@Composable
fun HeaderText(textContent: String, modifier: Modifier = Modifier) {
    Text(
        text = textContent,
        fontSize = 24.sp,
        fontWeight = FontWeight.W400,
        modifier = modifier.padding(16.dp)
    )
}

@Composable
fun ContentText(textContent: String, modifier: Modifier) {
    Text(
        text = textContent,
        fontSize = 16.sp,
        textAlign = TextAlign.Justify,
        modifier = modifier
    )
}

@Composable
fun ComposeTutorialApp() {
    Column {
        BannerImage()

        HeaderText(stringResource(R.string.header_text))

        ContentText(
            stringResource(R.string.intro_text), Modifier.padding(
                start = 16.dp,
                end = 16.dp
            )
        )

        ContentText(stringResource(R.string.content_text), Modifier.padding(16.dp))
    }
}

// ======================= Task App (All Task Completed Screen) =======================
@Composable
fun CheckmarkImage() {
    Image(
        painter = painterResource(R.drawable.ic_task_completed),
        contentDescription = null,
    )
}

@Composable
fun TaskApp(tasksCompleted: Int?) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        CheckmarkImage()

        Text(
            text = if (tasksCompleted != null && tasksCompleted > 0) "All $tasksCompleted Tasks completed!" else "All tasks completed!",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
        )

        Text("Nice Work!", fontSize = 16.sp)
    }
}

// ======================= Compose Quadrant App =======================
@Composable
fun Quadrant(titleText: String, bodyText: String, bgColor: Color, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(bgColor)
            .padding(16.dp)
            .fillMaxHeight()
    ) {
        Text(titleText, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))

        Text(bodyText, textAlign = TextAlign.Justify, lineHeight = 28.sp)
    }
}

@Composable
fun ComposeQuadrantApp() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.weight(0.1f, fill = true)
        ) {
            Quadrant(
                titleText = stringResource(R.string.q1_title_text),
                bodyText = stringResource(R.string.q1_body_text),
                bgColor = colorResource(R.color.q1),
                modifier = Modifier.weight(0.1f)
            )

            Quadrant(
                titleText = stringResource(R.string.q2_title_text),
                bodyText = stringResource(R.string.q2_body_text),
                bgColor = colorResource(R.color.q2),
                modifier = Modifier.weight(0.1f)
            )
        }

        Row(
            modifier = Modifier.weight(0.1f, fill = true)
        ) {
            Quadrant(
                titleText = stringResource(R.string.q3_title_text),
                bodyText = stringResource(R.string.q3_body_text),
                bgColor = colorResource(R.color.q3),
                modifier = Modifier.weight(0.1f)
            )

            Quadrant(
                titleText = stringResource(R.string.q4_title_text),
                bodyText = stringResource(R.string.q4_body_text),
                bgColor = colorResource(R.color.q4),
                modifier = Modifier.weight(0.1f)
            )
        }
    }
}

// ======================= Little Lemon App =======================
@SuppressLint("AutoboxingStateCreation")
@Composable
fun LittleLemonApp() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current

    val bottomNavController = rememberNavController()

    val bottomTabs = listOf(
        LittleLemonMenuScreen,
        LittleLemonHomeScreen,
        LittleLemonLocationScreen
    )

    val selectedIndex = rememberSaveable{ mutableIntStateOf(0) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerPanel(
                    drawerState = drawerState,
                    scope = scope
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(drawerState, scope)
            },

            bottomBar = {
                NavigationBar {
                    for ((index, destination) in bottomTabs.withIndex()) {
                        NavigationBarItem(
                            selected = selectedIndex.intValue == index,

                            onClick = {
                                selectedIndex.intValue = index

                                bottomNavController.navigate(destination.route) {
                                    popUpTo(LittleLemonHomeScreen.route)
                                    launchSingleTop = true
                                }
                            },

                            icon = {
                                Icon(
                                    painter = painterResource(id = destination.icon),
                                    contentDescription = destination.title
                                )
                            },

                            label = { Text(text = destination.title) }
                        )
                    }
                }
            }
        ) { padding ->
            val modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.bg_off_white))
                .padding(padding)

            Box(modifier = modifier) {
                NavHost(navController = bottomNavController, startDestination = LittleLemonHomeScreen.route) {
                    composable(LittleLemonHomeScreen.route) {
                        if (configuration.orientation == ORIENTATION_LANDSCAPE) {
                            Row(
                                modifier = modifier,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                HeroSection(
                                    modifier = Modifier
                                        .weight(0.5f)
                                        .fillMaxHeight()
                                        .verticalScroll(rememberScrollState())
                                )
                                LowerPanel(
                                    modifier = Modifier
                                        .weight(0.5f)
                                        .fillMaxHeight()
                                        .verticalScroll(rememberScrollState())
                                )
                            }
                        } else {
                            Column(
                                modifier = modifier
                                    .verticalScroll(rememberScrollState())
                            ) {
                                HeroSection()
                                LowerPanel()
                            }
                        }
                    }

                    composable(LittleLemonMenuScreen.route) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text("Menu Screen", fontSize = 24.sp)
                        }
                    }

                    composable(LittleLemonLocationScreen.route) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text("Location Screen", fontSize = 24.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HeroSection(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier
            .fillMaxWidth()
            .background(color = colorResource(R.color.teal_700))
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.little_lemon),
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = colorResource(R.color.little_lemon_yellow),
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = stringResource(R.string.chicago),
            color = colorResource(R.color.white),
            fontSize = 22.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.hero_description),
                color = colorResource(R.color.white),
                fontSize = 16.sp,
                lineHeight = 20.sp,
                modifier = Modifier.weight(0.6f)
            )

            Image(
                painter = painterResource(R.drawable.little_lemon_hero_image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clip(shape = RoundedCornerShape(size = 10.dp))
                    .weight(0.4f)
                    .height(120.dp)
            )
        }

        Button(
            onClick = {
                Toast.makeText(
                    context, "Order Successful", Toast.LENGTH_SHORT
                ).show()


            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.little_lemon_yellow)),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.hero_cta_btn_text),
                color = colorResource(R.color.text_black),
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
            )
        }
    }
}

@Composable
fun LowerPanel(modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .background(color = colorResource(R.color.white))
        .padding(8.dp)) {
        WeeklySpecialCard()
        Spacer(Modifier.height(8.dp))
        MenuDish()
    }
}

@Composable
fun WeeklySpecialCard(modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Weekly Special",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(8.dp)
        )
    }
}

@Composable
fun MenuDish(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column {
                    Text(
                        text = "Greek Salad",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "The famous Greek salad of " +
                                "crispy lettuce, peppers, olives, " +
                                "our Chicago ...",
                        color = Color.Gray,
                        modifier = Modifier
                            .padding(top = 5.dp, bottom = 5.dp)
                            .fillMaxWidth(.75f)
                    )

                    Text(
                        text = "$12.99",
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.greeksalad),
                    contentDescription = "",
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            thickness = 1.dp,
            color = Color.LightGray.copy(alpha = 0.5F)
        )
    }
}

// ======================= Little Lemon Login Screen =======================
@Composable
fun LoginScreenApp() {
    // Data/State
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }

    val context = LocalContext.current

    // UI
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        // Logo
        Image(
            painter = painterResource(
                id = R.drawable.littlelemonlogo
            ),
            contentDescription = "Logo Image",
            modifier = Modifier.padding(10.dp)
        )

        // Username
        TextField(
            value = username,
            onValueChange = {
                username = it
            },
            label = { Text(text = "Username") },
            modifier = Modifier.padding(10.dp)
        )

        TextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = { Text(text = "Password:") },
            modifier = Modifier.padding(10.dp)
        )

        // Submit Button
        Button(
            onClick = {
                if (username.text == "LordDregg"
                    && password.text == "littlelemon"
                ) {
                    Toast.makeText(
                        context,
                        "Welcome to Little Lemon!",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Invalid credentials."
                                + "Please try again.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Login")
        }

    }
}