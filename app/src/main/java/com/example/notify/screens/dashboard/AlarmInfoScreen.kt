package com.example.notify.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notify.R
import com.example.notify.composables.DashHead
import com.example.notify.data.Alarms
import com.example.notify.ui.theme.fontFamily
import com.example.notify.viewModels.AlarmInfoViewModel

@Composable
fun AlarmInfoScreen(navController: NavController,
                    modifier: Modifier = Modifier,
                    viewModel: AlarmInfoViewModel = hiltViewModel(),
                    currentGroupId: String,
                    alarmId: String){

    val alarm by viewModel.alarm.observeAsState()
    val isAdmin by viewModel.isAdmin.observeAsState(false)

    LaunchedEffect(Unit) {
        viewModel.getAlarmById(currentGroupId, alarmId)
    }

    Surface(
        color = colorResource(id = R.color.purpleMain),
        modifier = Modifier.fillMaxSize()
    ) {
        alarm?.let { alarm ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.white)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .background(colorResource(id = R.color.purpleMain))
                )
                {
                    DashHead(
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        value = "Group : ${ alarm.groupDisp }"
                    )
                }
                Column(modifier = Modifier
                    .weight(12f)
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.white))){
                    AlarmDetails(alarm)
                }
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .background(colorResource(id = R.color.purpleMain))
                    ) {
                        if (isAdmin)
                        {
                            Button(
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    viewModel.deleteAlarm(alarmId, currentGroupId)
                                    viewModel.fetchAlarms()
                                    navController.popBackStack()
                                }
                            )
                        {
                            Text(
                                text = "Delete",
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.W200,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AlarmDetails(alarm: Alarms) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {

        Text(
            text = alarm.title,
            modifier = Modifier
                .padding(top = 4.dp)
                .align(Alignment.CenterHorizontally),
            color = colorResource(id = R.color.purpleMain),
            fontSize = 40.sp,
            fontFamily = fontFamily,
            fontWeight = FontWeight.W200,
            textAlign = TextAlign.Left
        )
        Text(
            text = "Starting Date :\n ${alarm.dateDisplay}  ${if (alarm.hour > 12) alarm.hour - 12 else if (alarm.hour == 0) 12 else alarm.hour}:${
                alarm.minute.toString().padStart(2, '0')
            } ${if (alarm.hour >= 12) "PM" else "AM"}",
            modifier = Modifier
                .padding(top = 4.dp)
                .align(Alignment.CenterHorizontally),
            color = colorResource(id = R.color.purpleMain),
            fontSize = 20.sp,
            fontFamily = fontFamily,
            fontWeight = FontWeight.W200,
            textAlign = TextAlign.Left
        )
        Text(
            text = "Ending Date :\n ${alarm.endDateDisplay}  ${if (alarm.endHour > 12) alarm.endHour - 12 else if (alarm.endHour == 0) 12 else alarm.endHour}:${
                alarm.endMinute.toString().padStart(2, '0')
            } ${if (alarm.endHour >= 12) "PM" else "AM"}",
            modifier = Modifier
                .padding(top = 4.dp)
                .align(Alignment.CenterHorizontally),
            color = colorResource(id = R.color.purpleMain),
            fontSize = 20.sp,
            fontFamily = fontFamily,
            fontWeight = FontWeight.W200,
            textAlign = TextAlign.Left
        )
        Spacer(modifier = Modifier.size(30.dp))
        Text(
            text = "Description :\n ${ alarm.description }",
            modifier = Modifier
                .padding(top = 2.dp)
                .align(Alignment.CenterHorizontally),
            color = colorResource(id = R.color.purpleMain),
            fontSize = 20.sp,
            fontFamily = fontFamily,
            fontWeight = FontWeight.W200,
            textAlign = TextAlign.Left
        )
    }
}