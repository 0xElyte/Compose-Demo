package com.example.composedemo

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TopAppBar(drawerState: DrawerState, scope: CoroutineScope) {
    val context = LocalContext.current

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(top = 50.dp, start = 6.dp, end = 6.dp, bottom = 6.dp)
    ) {
        IconButton(onClick = { scope.launch { drawerState.open() } }) {
            Icon(Icons.Default.Menu, contentDescription = "Open Drawer")
        }

        Image(
            painter = painterResource(R.drawable.littlelemontopbarlogo),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier.fillMaxWidth(0.5f)
        )

        IconButton(onClick = { Toast.makeText(context, "Cart Clicked", Toast.LENGTH_SHORT).show() }) {
            Icon(Icons.Default.ShoppingCart, contentDescription = "Open Cart")
        }
    }
}