package com.example.notify.composables

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notify.R
import com.example.notify.ui.theme.fontFamily

@Composable
fun DashHead(value: String, modifier: Modifier) {
    Text(
        text = value,
        modifier = modifier.padding(10.dp), // Apply modifier to the Text composable
        style = TextStyle(
            fontSize = 25.sp,
            fontWeight = FontWeight.W200,
            fontStyle = FontStyle.Normal,
            fontFamily = fontFamily
        ),
        color = colorResource(id = R.color.white)
    )
}
