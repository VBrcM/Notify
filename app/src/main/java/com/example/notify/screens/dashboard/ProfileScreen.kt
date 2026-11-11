package com.example.notify.screens.dashboard

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notify.navigation.Screens
import com.example.notify.ui.theme.fontFamily
import com.example.notify.viewModels.ProfileViewModel

@Composable
fun ProfileScreen(navController: NavController,
                modifier: Modifier = Modifier,
                viewModel: ProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val text by remember { mutableStateOf("") }

    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                viewModel.logoutUser(
                    onComplete = {
                        navController.navigate(Screens.ScreenLoginRoute.route)
                    },
                    onError = { errorMessage ->
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                )
            }) {
                Text("Logout",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W200,
                    textAlign = TextAlign.Center)
            }
            Text(text = text)

            Column {
            }
        }
    }
}