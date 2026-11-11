package com.example.notify.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notify.R
import com.example.notify.data.Alarms
import com.example.notify.navigation.Screens
import com.example.notify.ui.theme.fontFamily

@Composable
fun AlarmCard(alarms : Alarms, navController : NavController) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(6.dp)
        .border(
            width = 1.dp,
            color = colorResource(id = R.color.purpleMain),
            shape = RoundedCornerShape(12.dp) // Corner radius in dp
        ),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ){
        Row(modifier = Modifier
            .padding(20.dp)
            .align(Alignment.CenterHorizontally)){
            Column(modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .padding(top = 10.dp))
            {
                Text(
                    text = alarms.title,
                    modifier = Modifier
                        .padding(top = 2.dp)
                        .align(Alignment.CenterHorizontally),
                    color = colorResource(id = R.color.purpleMain),
                    fontSize = 15.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W200,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = alarms.groupDisp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 6.dp, bottom = 10.dp),
                    color = colorResource(id = R.color.purpleMain),
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W200,
                    fontSize = 10.sp
                )

            }
            Column(modifier = Modifier
                .weight(1.5f)
                .align(Alignment.CenterVertically)
                .padding(top = 10.dp)) {
                Text(
                    text = "${if (alarms.endHour > 12) alarms.endHour - 12 else if (alarms.endHour == 0) 12 else alarms.endHour}:${alarms.endMinute.toString().padStart(2, '0')} ${if (alarms.endHour >= 12) "PM" else "AM"}",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = colorResource(id = R.color.purpleMain),
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W200,
                    fontSize = 20.sp
                )
                Text(
                    text = alarms.endDateDisplay,
                    modifier = Modifier
                        .padding(top = 6.dp, bottom = 10.dp)
                        .align(Alignment.CenterHorizontally),
                    color = colorResource(id = R.color.purpleMain),
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W200,
                    fontSize = 10.sp
                )
            }
            Column(modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)) {
                IconButton(modifier = Modifier
                    .align(Alignment.End),
                    onClick = {
                        navController.navigate("${Screens.ScreenAlarmInfoRoute.route}/currentGroupId/${alarms.currentGroupId}/alarmId/${alarms.alarmId}")
                    })

                {
                    Icon(
                        modifier = Modifier
                            .size(60.dp),
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Back",
                        tint = colorResource(id = R.color.purpleMain))
                }

            }
        }
    }
}