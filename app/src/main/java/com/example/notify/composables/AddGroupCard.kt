package com.example.notify.composables


import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notify.R
import com.example.notify.navigation.Screens
import com.example.notify.ui.theme.fontFamily

@Composable
fun AddGroupCard(navController: NavController) {
    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Add Group",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.W200,
                        textAlign = TextAlign.Center
                    )
                }
            },
            onDismissRequest = {
                showDialog = false
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            navController.navigate(Screens.ScreenJoinGroupRoute.route)
                            showDialog = false
                        },
                    ) {
                        Text(
                            text = "Join Group",
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.W200,
                            textAlign = TextAlign.Center
                        )
                    }
                    Button(
                        onClick = {
                            navController.navigate(Screens.ScreenCreateGroupRoute.route)
                            showDialog = false
                        },
                    ) {
                        Text(
                            text = "Create Group",
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.W200,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        )
    }

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(6.dp)
        .border(
            width = 1.dp,
            color = colorResource(id = R.color.purpleMain),
            shape = RoundedCornerShape(12.dp)
        )
        .clickable(
            onClick = {
                showDialog = true
            }
        ),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ){
        Row(modifier = Modifier
            .padding(20.dp)
            .align(Alignment.CenterHorizontally)){
            Column(modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically))
            {
                Text(
                    text = "Add Group",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    color = colorResource(id = R.color.purpleMain),
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W200,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

