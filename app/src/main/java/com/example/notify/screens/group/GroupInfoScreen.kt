package com.example.notify.screens.group


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notify.data.Groups
import com.example.notify.ui.theme.fontFamily
import com.example.notify.uiState.LeaveGroupState
import com.example.notify.viewModels.GroupInfoViewModel

@Composable
fun GroupInfoScreen(
    groupId: String,
    navController: NavController,
    viewModel: GroupInfoViewModel = hiltViewModel()
) {
    val groupDetails by viewModel.groupDetails.observeAsState()

    val leaveGroupState by viewModel.leaveGroupState.observeAsState(LeaveGroupState.Idle)

    LaunchedEffect(Unit) {
        viewModel.getGroupDetails(groupId)
    }

    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (groupDetails == null) {
                CircularProgressIndicator()
            } else {
                GroupDetails(groupDetails!!)
                when (leaveGroupState) {
                    is LeaveGroupState.Loading -> CircularProgressIndicator()
                    is LeaveGroupState.Idle -> {
                        Button(
                            onClick = { viewModel.leaveGroup(groupId, navController = navController) },
                            modifier = Modifier.padding(16.dp),
                            enabled = leaveGroupState !is LeaveGroupState.Loading
                        ) {
                            Text(text = "Leave Group",
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.W200,
                                textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GroupDetails(group: Groups) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = "Group Name: ${group.groupName}",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp),
            fontFamily = fontFamily,
            fontWeight = FontWeight.W200,
            textAlign = TextAlign.Left
        )
        SelectionContainer{
            Text(
                text = "UID: ${group.groupId}",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        Text(
            text = "Creator: ${group.creatorUsername}",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp),
            fontFamily = fontFamily,
            fontWeight = FontWeight.W200,
            textAlign = TextAlign.Left
        )
        Text(
            text = "Members:",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 4.dp),
            fontFamily = fontFamily,
            fontWeight = FontWeight.W200,
            textAlign = TextAlign.Left
        )
        group.members.forEach { member ->
            Text(
                text = member,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 16.dp),
                fontFamily = fontFamily,
                fontWeight = FontWeight.W200,
                textAlign = TextAlign.Left
            )
        }
    }
}