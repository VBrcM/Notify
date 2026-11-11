package com.example.notify.screens.dashboard

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notify.R
import com.example.notify.composables.DashHead
import com.example.notify.data.AlarmGroup
import com.example.notify.data.Alarms
import com.example.notify.data.generateUniqueId
import com.example.notify.ui.theme.fontFamily
import com.example.notify.uiState.GroupsResult
import com.example.notify.viewModels.AddAlarmViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAlarmScreen(
    navController: NavController,
    viewModel: AddAlarmViewModel = hiltViewModel(),
) {
    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

    val date = remember {
        Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
    }

    var titleText by remember { mutableStateOf("") }
    var descText by remember { mutableStateOf("") }
    var selectedGroup by remember { mutableStateOf(AlarmGroup(id = "", groupName = "")) }

    var selectedDate by remember { mutableStateOf(Calendar.getInstance().apply{
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month)
        set(Calendar.DAY_OF_MONTH, dayOfMonth)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }) }

    var selectedEndDate by remember { mutableStateOf(Calendar.getInstance().apply{
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month)
        set(Calendar.DAY_OF_MONTH, dayOfMonth)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }) }

    var intervalDays by remember { mutableIntStateOf(0) }
    var intervalHours by remember { mutableIntStateOf(0) }
    var intervalMinutes by remember { mutableIntStateOf(0) }
    var intervalInMillis by remember { mutableLongStateOf(0L) }


    fun calculateIntervalInMillis(days: Int, hours: Int, minutes: Int): Long {
        val daysInMillis = days * 24 * 60 * 60 * 1000L // Convert days to milliseconds
        val hoursInMillis = hours * 60 * 60 * 1000L // Convert hours to milliseconds
        val minutesInMillis = minutes * 60 * 1000L // Convert minutes to milliseconds

        return daysInMillis + hoursInMillis + minutesInMillis // Sum up to get the total interval in milliseconds
    }

    LaunchedEffect(key1 = true) {
        viewModel.fetchGroupsForUser()
    }

    fun formatDate(calendar: Calendar): String {
        val format = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
        return format.format(calendar.time)
    }

    val showDate = formatDate(selectedDate)
    val showEndDate = formatDate(selectedEndDate)

    val groupsResult by viewModel.groupsResult.observeAsState()

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = date.timeInMillis,
        yearRange = year..year + 10
    )
    var showDatePicker by remember { mutableStateOf(false) }

    val endDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = date.timeInMillis,
        yearRange = year..year + 10
    )

    var showEndDatePicker by remember { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState()
    val endTimePickerState = rememberTimePickerState()

    val context = LocalContext.current

    val dateInit = remember {
        Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }
    }
    var showTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }

    val selectedHour = timePickerState.hour
    val selectedMin = timePickerState.minute

    val selectedEndHour = endTimePickerState.hour
    val selectedEndMin = endTimePickerState.minute

    val selectedDateInMillis = selectedDate.timeInMillis
    val selectedEndDateInMillis = selectedEndDate.timeInMillis

    val startDateTimeInMillis = selectedDateInMillis + (selectedHour * 3600000) + (selectedMin * 60000)
    val endDateTimeInMillis = selectedEndDateInMillis + (selectedEndHour * 3600000) + (selectedEndMin * 60000)


    Surface(
        color = colorResource(id = R.color.purpleMain),
        modifier = Modifier.fillMaxSize())
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {


            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(
                            modifier = Modifier
                                .weight(1f),
                            onClick = {
                                val selectedMillis = datePickerState.selectedDateMillis
                                if (selectedMillis != null) {
                                    val selectedCalendar = Calendar.getInstance().apply {
                                        timeInMillis = selectedMillis
                                    }
                                    if (selectedCalendar.after(dateInit) || selectedCalendar == dateInit) {
                                        if (selectedCalendar == selectedEndDate || selectedCalendar.before(
                                                selectedEndDate
                                            )
                                        ) {
                                            Toast.makeText(
                                                context,
                                                "Selected date saved",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            selectedDate = selectedCalendar
                                            showDatePicker = false
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Selected date should not exceed target date.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Selected date should be on, or after today",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Please select a date",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        ) { Text("OK") }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDatePicker = false
                            }
                        ) { Text("Cancel") }
                    }
                )
                {
                    DatePicker(
                        state = datePickerState
                    )
                }
            }

            if (showEndDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showEndDatePicker = false },
                    confirmButton = {
                        TextButton(
                            modifier = Modifier
                                .weight(1f),
                            onClick = {
                                val selectedEndMillis = endDatePickerState.selectedDateMillis
                                if (selectedEndMillis != null) {
                                    val selectedEndCalendar = Calendar.getInstance().apply {
                                        timeInMillis = selectedEndMillis
                                    }
                                    if (selectedEndCalendar.after(selectedDate) || selectedEndCalendar == selectedDate) {
                                        Toast.makeText(
                                            context,
                                            "Selected date saved",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        selectedEndDate = selectedEndCalendar
                                        showEndDatePicker = false
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Selected date should be on, or after starting date.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Please select a date",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        ) { Text("OK") }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showEndDatePicker = false
                            }
                        ) { Text("Cancel") }
                    }
                )
                {
                    DatePicker(
                        state = endDatePickerState
                    )
                }
            }


            if (showTimePicker) {
                TimePickerDialog(
                    onDismissRequest = { showTimePicker = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showTimePicker = false
                            }
                        ) { Text("OK") }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showTimePicker = false
                            }
                        ) { Text("Cancel") }
                    }
                )
                {
                    TimePicker(state = timePickerState)
                }
            }

            if (showEndTimePicker) {
                TimePickerDialog(
                    onDismissRequest = { showEndTimePicker = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showEndTimePicker = false
                            }
                        ) { Text("OK") }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showEndTimePicker = false
                            }
                        ) { Text("Cancel") }
                    }
                )
                {
                    TimePicker(state = endTimePickerState)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(colorResource(id = R.color.purpleMain))
            )
            {
                DashHead(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    value = stringResource(R.string.add_reminder)
                )
            }

            Row(modifier = Modifier
                .weight(3f)
                .align(Alignment.CenterHorizontally)
                .background(Color.White)
                .padding(10.dp)
            ){
                Column(modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f))
                {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontSize = 12.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.W200,
                        text = "Start",
                        textAlign = TextAlign.Center)
                    TextButton(
                        onClick = { showTimePicker = true },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            fontSize = 40.sp,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.W200,
                            text = "${if (selectedHour > 12) selectedHour - 12 else if (selectedHour == 0) 12 else selectedHour}:${
                                selectedMin.toString().padStart(2, '0')
                            }"
                            ,textAlign = TextAlign.Center
                        )
                    }
                    TextButton(
                        onClick = {
                            showDatePicker = true
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.W200,
                            text = showDate)
                    }
                }

                Spacer(modifier = Modifier.weight(.2f))

                 Column(modifier = Modifier
                     .align(Alignment.CenterVertically)
                     .weight(1f))
                 {
                     Text(
                         modifier = Modifier.align(Alignment.CenterHorizontally),
                         fontSize = 12.sp,
                         fontFamily = fontFamily,
                         fontWeight = FontWeight.W200,
                         text = "End",
                         textAlign = TextAlign.Center)
                    TextButton(
                        modifier = Modifier
                            .background(Color.White)
                            .clip(RectangleShape)
                            .align(Alignment.CenterHorizontally),
                        onClick = { showEndTimePicker = true }
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            fontSize = 40.sp,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.W200,
                            text = "${if (selectedEndHour > 12) selectedEndHour - 12 else if (selectedEndHour == 0) 12 else selectedEndHour}:${
                                selectedEndMin.toString().padStart(2, '0')
                            }"
                        )
                    }

                    TextButton(
                        onClick = {
                            showEndDatePicker = true
                        },
                        modifier = Modifier
                            .background(Color.White)
                            .clip(RectangleShape)
                            .align(Alignment.CenterHorizontally),
                    ) {
                        Text(text = showEndDate,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.W200
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .weight(4f)
                    .align(Alignment.CenterHorizontally)
            ){
                Column{
                    OutlinedTextField(
                        value = titleText,
                        onValueChange = { titleText = it },
                        modifier = Modifier
                            .weight(2.5f)
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(4.dp),
                        placeholder = {
                            Text(text = "Title",
                                color = Color.Gray,
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.W200)
                        }
                    )


                    OutlinedTextField(
                        value = descText,
                        onValueChange = { descText = it },
                        textStyle = LocalTextStyle.current.copy(color = if (descText.isEmpty()) Color.Gray else Color.Black),
                        modifier = Modifier
                            .weight(4f)
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(8.dp),
                        placeholder = {
                            Text(text = "Desc", color = Color.Gray,
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.W200,
                                textAlign = TextAlign.Center)
                        }
                    )

                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontSize = 16.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.W200,
                        text = "Set Alarm Interval",
                        textAlign = TextAlign.Center
                    )

                    TimeIntervalInput(
                        days = intervalDays,
                        hours = intervalHours,
                        minutes = intervalMinutes,
                        onTimeIntervalChanged = { newDays, newHours, newMinutes ->
                            intervalDays = newDays
                            intervalHours = newHours
                            intervalMinutes = newMinutes

                            val sanitizedDays = if (newDays != 0) newDays else 0
                            val sanitizedHours = if (newHours != 0) newHours else 0
                            val sanitizedMinutes = if (newMinutes != 0) newMinutes else 0

                            intervalInMillis =
                                calculateIntervalInMillis(
                                    sanitizedDays,
                                    sanitizedHours,
                                    sanitizedMinutes
                                )
                        }
                    )
                }
            }

            Box(modifier = Modifier
                .weight(4f)
                .background(Color.White)){
                GroupSelectionScreen(
                    groupsResult = groupsResult,
                    selectedGroup = selectedGroup,
                    onGroupSelected = { group ->
                        selectedGroup = group
                    }

                )
            }
            // Button to share the alarm with selected groups
            Row(modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally)
                .background(colorResource(id = R.color.purpleMain))){
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {navController.popBackStack()}) {
                    Text( text = "Cancel",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.W200,
                        textAlign = TextAlign.Center)
                }
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (selectedGroup.id == "") {
                            Toast.makeText(context, "No group selected", Toast.LENGTH_SHORT).show()
                        } else {
                            val alarms = Alarms(
                                alarmId = generateUniqueId(),
                                title = titleText,
                                description = descText,
                                hour = selectedHour,
                                minute = selectedMin,
                                endHour = selectedEndHour,
                                endMinute = selectedEndMin,
                                dateDisplay = showDate,
                                endDateDisplay = showEndDate,
                                dInMillis = selectedDateInMillis,
                                endDInMillis = selectedEndDateInMillis,
                                groupDisp = selectedGroup.groupName,
                                currentGroupId = selectedGroup.id,
                                intervalDays = intervalDays,
                                intervalHours = intervalHours,
                                intervalMinutes = intervalMinutes,
                                intervalInMillis = intervalInMillis,
                                startTimeInMillis = startDateTimeInMillis,
                                endTimeInMillis = endDateTimeInMillis
                            )
                            viewModel.saveAlarm(alarms, selectedGroup)
                            navController.popBackStack()
                        }
                    }
                ) {
                    Text(
                        text = "Create",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.W200,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


@Composable
fun GroupSelectionScreen(
    groupsResult: GroupsResult?,
    selectedGroup: AlarmGroup?,
    onGroupSelected: (AlarmGroup) -> Unit
) {
        when (groupsResult) {
            is GroupsResult.Success -> {
                val groups = groupsResult.groups
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                ) {
                    items(groups) { group ->
                        GroupCheckListItem(
                            group = group,
                            isSelected = group == selectedGroup,
                            onGroupSelected = onGroupSelected
                        )
                    }
                }
            }

            is GroupsResult.Failure -> {
                Text(text = "Failed to load groups: ${groupsResult.error.message}")
            }
            else -> {
                // Handle other states if needed
            }
        }
}

@Composable
fun GroupCheckListItem(
    group: AlarmGroup,
    isSelected: Boolean,
    onGroupSelected: (AlarmGroup) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        RadioButton( // Change Checkbox to RadioButton
            selected = isSelected,
            onClick = { onGroupSelected(group) }
        )
        Text(
            text = group.groupName,
            modifier = Modifier.padding(start = 2.dp),
            fontFamily = fontFamily,
            fontWeight = FontWeight.W200,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TimeIntervalInput(
    days: Int,
    hours: Int,
    minutes: Int,
    onTimeIntervalChanged: (Int, Int, Int) -> Unit
) {
    Row(
        Modifier.background(Color.White)
    ){
        // Input for days
        OutlinedTextField(
            value = if (days == 0) "" else days.toString(),
            onValueChange = { newValue ->
                val newDays = newValue.toIntOrNull() ?: 0
                onTimeIntervalChanged(newDays, hours, minutes)
            },
            placeholder = {
                Text(text = "Days",
                    color = Color.Gray,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W200,
                    textAlign = TextAlign.Center)
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .weight(1f)
                .background(Color.White)
                .padding(8.dp)
        )

        OutlinedTextField(
            value = if (hours == 0) "" else hours.toString(),
            onValueChange = { newValue ->
                val newHours = newValue.takeIf { it.isNotBlank() }?.toIntOrNull()?.coerceIn(0, 23) ?: 0
                onTimeIntervalChanged(days, newHours, minutes)
            },
            placeholder = {
                Text(text = "Hours", color = Color.Gray,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W200,
                    textAlign = TextAlign.Center)
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .weight(1f)
                .background(Color.White)
                .padding(8.dp)
        )

        // Input for minutes
        OutlinedTextField(
            value = if (minutes == 0) "" else minutes.toString(),
            onValueChange = { newValue ->
                val newMinutes = newValue.takeIf { it.isNotBlank() }?.toIntOrNull()?.coerceIn(0, 59) ?: 0
                onTimeIntervalChanged(days, hours, newMinutes)
            },
            placeholder = {
                Text(text = "Mins", color = Color.Gray,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W200,
                    textAlign = TextAlign.Center)
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .weight(1f)
                .background(Color.White)
                .padding(8.dp)
        )
    }
}

@Composable
fun TimePickerDialog(
    title: String = "Select Time",
    onDismissRequest: () -> Unit,
    confirmButton: @Composable (() -> Unit),
    dismissButton: @Composable (() -> Unit)? = null,
    containerColor: Color = Color.White,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = containerColor
                ),
            color = containerColor
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W200,
                    textAlign = TextAlign.Center
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    dismissButton?.invoke()
                    confirmButton()
                }
            }
        }
    }
}
