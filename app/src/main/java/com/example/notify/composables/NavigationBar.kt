package com.example.notify.composables


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.notify.R
import com.example.notify.navigation.Screens

@Composable
fun NavigationBar(navController : NavController, modifier: Modifier = Modifier){

    Row(modifier = Modifier
    .fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceEvenly){
        Button(/*Refresh*/
            onClick = {
                      navController.navigate(Screens.ScreenDashRoute.route)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.purpleMain)
            ),
            modifier = Modifier
                .padding(8.dp)
                .border(
                    1.dp,
                    colorResource(id = R.color.purpleMain),
                    RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
        ) {
            Icon(
                Icons.Default.Home,
                contentDescription = "",
                tint = colorResource(id = R.color.white),
                modifier = Modifier.size(30.dp)
            )
        }
        Button(/*add*/
            onClick = {
                navController.navigate(Screens.ScreenAddAlarmRoute.route)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.purpleMain)
            ),
            modifier = Modifier
                .padding(8.dp)
                .border(
                    1.dp,
                    colorResource(id = R.color.purpleMain),
                    RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "",
                tint = colorResource(id = R.color.white),
                modifier = Modifier.size(30.dp)
            )
        }
        Button(/*Group*/
            onClick = {
                navController.navigate(Screens.ScreenGroupRoute.route)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.purpleMain)
            ),
            modifier = Modifier
                .padding(8.dp)
                .border(
                    1.dp,
                    colorResource(id = R.color.purpleMain),
                    RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = "",
                tint = colorResource(id = R.color.white),
                modifier = Modifier
                    .size(30.dp)
            )
        }
    }
}