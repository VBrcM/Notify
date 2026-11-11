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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notify.R
import com.example.notify.data.Groups

@Composable
fun GroupCard(groups: Groups, navController: NavController) {
    val creatorUsername = groups.creatorUsername
    val groupName = groups.groupName
    val memberCount =groups.memberCount

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.purpleMain),
                shape = RoundedCornerShape(12.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Column(modifier = Modifier.weight(1f).align(Alignment.CenterVertically)) {
                Text(
                    text = groupName,
                    modifier = Modifier.align(Alignment.Start),
                    color = colorResource(id = R.color.purpleMain),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "$memberCount members",
                    modifier = Modifier.align(Alignment.Start),
                    color = colorResource(id = R.color.purpleMain),
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Made by: $creatorUsername",
                    modifier = Modifier.align(Alignment.Start),
                    color = colorResource(id = R.color.purpleMain),
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center
                )
            }
            Column(modifier = Modifier.weight(1f).align(Alignment.CenterVertically)) {
                IconButton(
                    modifier = Modifier.align(Alignment.End),
                    onClick = {
                        navController.navigate("groupInfoScreen/${groups.groupId}")
                        }
                ) {
                    Icon(
                        modifier = Modifier.size(60.dp),
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Navigate to group details",
                        tint = colorResource(id = R.color.purpleMain)
                    )
                }
            }
        }
    }
}