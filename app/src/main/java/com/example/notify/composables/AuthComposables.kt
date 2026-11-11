package com.example.notify.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notify.R
import com.example.notify.ui.theme.fontFamily
import com.example.notify.viewModels.SignupViewModel

@Composable
fun NormalTextComponent(value : String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp),
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            fontFamily = fontFamily
        ),
        color = colorResource(id = R.color.white),
        textAlign = TextAlign.Center
    )

}

@Composable
fun HeadingTextComponent(value : String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 110.dp),
        style = TextStyle(
            fontSize = 48.sp,
            fontWeight = FontWeight.W200,
            fontStyle = FontStyle.Normal,
            fontFamily = fontFamily
        ),
        textAlign = TextAlign.Center
        ,color = colorResource(id = R.color.white)
    )
}
@Composable
fun UnInput(
    value: String,
    onChange : (String)->Unit,
    modifier: Modifier = Modifier,
    label : String = "Username",
    placeholder : String = "Enter Your Username"
){
    var username : String by remember{ mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Person,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }
    TextField(
        value = username,
        onValueChange = {username = it},
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(8.dp),
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = {focusManager.moveFocus(FocusDirection.Down)}
        ),
        placeholder = { Text(placeholder,
            fontFamily = fontFamily,
            fontWeight = FontWeight.W200)
        },
        label = {
            Text(label,
            fontFamily = fontFamily,
            fontWeight = FontWeight.W200)
        },
        maxLines = 1,
        visualTransformation = VisualTransformation.None
    )
}

@Composable
fun gmailInput(
    value: String,
    onChange : (String)->Unit,
    modifier: Modifier = Modifier,
    label : String = "Gmail",
    placeholder : String = "Enter Your Gmail"
){
    var email : String by remember{ mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Person,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }
    TextField(
        value = email,
        onValueChange = {email = it},
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(8.dp),
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = {focusManager.moveFocus(FocusDirection.Down)}
        ),
        placeholder = { Text(placeholder,
            fontFamily = fontFamily,
            fontWeight = FontWeight.W200)
        },
        label = {
            Text(label,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W200)
        },
        maxLines = 1,
        visualTransformation = VisualTransformation.None
    )
}
@Composable
fun PwInput(
    value: String,
    onChange : (String)->Unit,
    modifier: Modifier = Modifier,
    label : String = "Password",
    placeholder : String = "Enter Your Password"
){
    var password : String by remember{ mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Lock,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }
    TextField(
        value = password,
        onValueChange = {password = it},
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(8.dp),
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = {focusManager.moveFocus(FocusDirection.Down)}
        ),
        placeholder = { Text(placeholder,
            fontFamily = fontFamily,
            fontWeight = FontWeight.W200)
        },
        label = {
            Text(label,
            fontFamily = fontFamily,
            fontWeight = FontWeight.W200)
        },
        maxLines = 1,
        visualTransformation = VisualTransformation.None
    )
}

@Composable
fun ConfirmPwInput(
    value: String,
    onChange : (String)->Unit,
    modifier: Modifier = Modifier,
    label : String = "Password",
    placeholder : String = "Enter Your Password"
){
    var confirmPass : String by remember{ mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Lock,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }
    TextField(
        value = confirmPass,
        onValueChange = {confirmPass = it},
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(8.dp),
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = {focusManager.moveFocus(FocusDirection.Down)}
        ),
        placeholder = { Text(placeholder,
            fontFamily = fontFamily,
            fontWeight = FontWeight.W200)
        },
        label = {
            Text(label,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W200)
        },
        maxLines = 1,
        visualTransformation = VisualTransformation.None
    )
}