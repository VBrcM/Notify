
package com.example.notify.screens.authScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notify.R
import com.example.notify.composables.HeadingTextComponent
import com.example.notify.navigation.Screens
import com.example.notify.ui.theme.fontFamily
import com.example.notify.viewModels.SignupViewModel
import kotlinx.coroutines.launch


@Composable
fun SignupScreen(viewModel: SignupViewModel = hiltViewModel(),
                 navController : NavController
) {
    var email by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPass by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.signupState.collectAsState(initial = null)
    val focusManager = LocalFocusManager.current

    Surface(
        color = colorResource(id = R.color.purpleMain),
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.purpleMain))
            .padding(50.dp)

    ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.purpleMain)),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Row(modifier = Modifier
                    .weight(2f))
                {
                    HeadingTextComponent(value = stringResource(id = R.string.SignupHead))
                }

                Row(
                    modifier = Modifier
                        .weight(5f)
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ){
                        TextField(
                            value = username,
                            onValueChange = { username = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1.5f),
                            shape = RoundedCornerShape(8.dp),
                            leadingIcon = userIcon,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            ),
                            placeholder = {
                                Text(
                                    "Enter Username",
                                    fontFamily = fontFamily,
                                    fontWeight = FontWeight.W200
                                )
                            },
                            label = {
                                Text(
                                    "Username",
                                    fontFamily = fontFamily,
                                    fontWeight = FontWeight.W200
                                )
                            },
                            maxLines = 1,
                            visualTransformation = VisualTransformation.None
                        )

                        Spacer(modifier = Modifier.weight(.25f))

                        TextField(
                            value = email,
                            onValueChange = { email = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1.5f),
                            shape = RoundedCornerShape(8.dp),
                            leadingIcon = mailIcon,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            ),
                            placeholder = {
                                Text(
                                    "Enter Email",
                                    fontFamily = fontFamily,
                                    fontWeight = FontWeight.W200
                                )
                            },
                            label = {
                                Text(
                                    "Email",
                                    fontFamily = fontFamily,
                                    fontWeight = FontWeight.W200
                                )
                            },
                            maxLines = 1,
                            visualTransformation = VisualTransformation.None
                        )

                        Spacer(modifier = Modifier.weight(.25f))

                        TextField(
                            value = password,
                            onValueChange = { password = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1.5f),
                            shape = RoundedCornerShape(8.dp),
                            leadingIcon = lockIcon,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            ),
                            placeholder = {
                                Text(
                                    "Enter Password",
                                    fontFamily = fontFamily,
                                    fontWeight = FontWeight.W200
                                )
                            },
                            label = {
                                Text(
                                    "Password",
                                    fontFamily = fontFamily,
                                    fontWeight = FontWeight.W200
                                )
                            },
                            maxLines = 1,
                            visualTransformation = VisualTransformation.None
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(.25f))

                Button(
                    onClick = {
                        scope.launch {
                            viewModel.signupUser( email, password, username)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.purpleMain)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)
                        .padding(vertical = 8.dp)
                        .border(1.dp, Color.White, RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Text(
                        text = stringResource(id = R.string.SignupHead),
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.W200
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.White.copy(alpha = 0.3f)
                )

                Row(modifier = Modifier
                    .weight(2f),
                    verticalAlignment = Alignment.CenterVertically)
                {
                    Column(modifier = Modifier
                        .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Don't Have An Account?",
                            color = colorResource(id = R.color.white),
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.W200,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        TextButton(onClick = {
                            navController.navigate(Screens.ScreenLoginRoute.route)
                        }) {
                            Text(
                                text = stringResource(id = R.string.LoginHead),
                                color = colorResource(id = R.color.teal_200),
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.W200,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
    LaunchedEffect(key1 = state.value?.isSuccess) {
        scope.launch {
            if (state.value?.isSuccess?.isNotEmpty() == true) {
                val success = state.value?.isSuccess
                Toast.makeText(context, "$success", Toast.LENGTH_LONG).show()
                navController.navigate(Screens.ScreenLoginRoute.route)
            }
        }
    }
    LaunchedEffect(key1 = state.value?.isError) {
        scope.launch {
            if (state.value?.isError?.isNotBlank() == true) {
                val error = state.value?.isError
                Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}