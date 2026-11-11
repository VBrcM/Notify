package com.example.notify.screens.group

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notify.R
import com.example.notify.composables.DashHead
import com.example.notify.navigation.Screens
import com.example.notify.ui.theme.fontFamily
import com.example.notify.uiState.GroupCreateState
import com.example.notify.viewModels.CreateGroupViewModel

@Composable
fun CreateGroupScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: CreateGroupViewModel = hiltViewModel()
) {
    var groupName by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val createGroupState by viewModel.createGroupState.collectAsState()

    LaunchedEffect(createGroupState) {
        when (val state = createGroupState) {
            is GroupCreateState.Success -> {
                navController.navigate(Screens.ScreenGroupRoute.route)
            }
            is GroupCreateState.Error -> {
                Toast.makeText(context, "Error: ${state.errorMessage}", Toast.LENGTH_LONG).show()
            }
            null -> {
                // Handle the case when createGroupState is null
            }
            is GroupCreateState.Loading -> {
                // Handle loading state if needed
            }
        }
    }

    Surface(
        color = colorResource(id = R.color.purpleMain),
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(20.dp)
            ) {
                DashHead(
                    value = "Create Group",
                    modifier = Modifier.weight(1f)
                )
            }
            Column(
                modifier = Modifier
                    .weight(6f)
                    .padding(30.dp)
            ) {
                TextField(
                    value = groupName,
                    onValueChange = { groupName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    placeholder = {
                        Text(
                            "Enter Group Name",
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.W200
                        )
                    },
                    label = {
                        Text(
                            "Group Name",
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.W200
                        )
                    },
                    maxLines = 1,
                    visualTransformation = VisualTransformation.None
                )
                Spacer(modifier = Modifier.weight(6f))
                Button(
                    onClick = {
                        viewModel.createGroup(groupName)
                        viewModel.getGroupsForCurrentUser()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.purpleMain)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            colorResource(id = R.color.white),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .weight(1f)
                        .padding(vertical = 6.dp)
                ) {
                    Text(
                        text = "Create",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.W200
                    )
                }
            }
        }
    }
}