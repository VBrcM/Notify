package com.example.notify.screens.dashboard


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notify.R
import com.example.notify.composables.AlarmCard
import com.example.notify.composables.DashHead
import com.example.notify.composables.NavigationBar
import com.example.notify.navigation.Screens
import com.example.notify.viewModels.DashboardViewModel

@Composable
fun Dashboard(navController: NavController,
              viewModel : DashboardViewModel = hiltViewModel(),
) {
    val alarms by viewModel.alarms.observeAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchAlarms()
    }

    Surface(
        color = colorResource(id = R.color.purpleMain),
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(modifier = Modifier
                .weight(1f))
            {
                DashHead(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    value = "Notify"
                )
                Spacer(
                    modifier = Modifier.weight(4f)
                )
                IconButton(
                    onClick = {
                        navController.navigate(Screens.ScreenProfileRoute.route)
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .border(
                            1.dp,
                            colorResource(id = R.color.purpleMain),
                            RoundedCornerShape(8.dp)
                        )
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(8.dp))
                        .align(Alignment.CenterVertically),

                    ) {
                    Icon(
                        Icons.Default.AccountCircle,
                        contentDescription = "",
                        tint = colorResource(id = R.color.white),
                        modifier = Modifier
                            .fillMaxHeight()
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .weight(12f)
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.white))
            ) {
                items(alarms) { alarm ->
                    AlarmCard(alarms = alarm, navController = navController)
                }
            }
            NavigationBar(navController = navController, modifier = Modifier.weight(1f))
        }
    }
}