package com.om.diucampusschedule

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.om.diucampusschedule.ui.theme.DIUCampusScheduleTheme
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun CollapsibleMiniCalendar(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val currentMonth = remember { mutableStateOf(YearMonth.now()) }
    val daysInMonth = currentMonth.value.lengthOfMonth()
    val firstDayOfMonth = currentMonth.value.atDay(1)
    val dayOfWeekOffset = firstDayOfMonth.dayOfWeek.value % 7
    val selectedDay = selectedDate.dayOfMonth

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clip(RoundedCornerShape(16.dp))
            .padding(top = 16.dp),
        /*elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),*/
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header with expand/collapse button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = currentMonth.value.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(8.dp)
                )
                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = if (isExpanded) "Collapse" else "Expand"
                    )
                }
            }

            if (isExpanded) {
                // Full Calendar View
                Spacer(modifier = Modifier.height(8.dp))

                // Month Navigation
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { currentMonth.value = currentMonth.value.minusMonths(1) }
                    ) {
                        Icon(Icons.Filled.KeyboardArrowLeft, "Previous Month")
                    }
                    Text(
                        text = currentMonth.value.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
                        style = MaterialTheme.typography.titleMedium
                    )
                    IconButton(
                        onClick = { currentMonth.value = currentMonth.value.plusMonths(1) }
                    ) {
                        Icon(Icons.Filled.KeyboardArrowRight, "Next Month")
                    }
                }

                // Weekday Headers
                WeekdayHeader()

                // Full Calendar Grid
                CalendarGrid(
                    currentMonth = currentMonth.value,
                    selectedDate = selectedDate,
                    onDateSelected = onDateSelected
                )
            } else {
                // Mini Calendar View (2 rows)
                Spacer(modifier = Modifier.height(8.dp))

                // Weekday Header
                WeekdayHeader()

                // Mini Calendar Grid (only show current week and next week)
                MiniCalendarGrid(
                    selectedDate = selectedDate,
                    onDateSelected = onDateSelected
                )
            }
        }
    }
}

@Composable
private fun WeekdayHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        listOf("M", "T", "W", "T", "F", "S", "S").forEach { day ->
            Text(
                text = day,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun MiniCalendarGrid(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val today = LocalDate.now()
    val currentWeekStart = today.minusDays(today.dayOfWeek.value.toLong() - 1)

    Column {
        // Current week
        CalendarWeekRow(
            weekStart = currentWeekStart,
            selectedDate = selectedDate,
            onDateSelected = onDateSelected
        )

        // Next week
        CalendarWeekRow(
            weekStart = currentWeekStart.plusWeeks(1),
            selectedDate = selectedDate,
            onDateSelected = onDateSelected
        )
    }
}

@Composable
private fun DayCell(
    date: LocalDate,
    isSelected: Boolean,
    onDateSelected: (LocalDate) -> Unit
) {
    val isToday = date == LocalDate.now()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(2.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(
                    when {
                        isSelected -> MaterialTheme.colorScheme.primary
                        isToday -> MaterialTheme.colorScheme.primaryContainer
                        else -> Color.Transparent
                    }
                )
                .clickable { onDateSelected(date) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                style = MaterialTheme.typography.bodySmall,
                color = when {
                    isSelected -> MaterialTheme.colorScheme.onPrimary
                    isToday -> MaterialTheme.colorScheme.onPrimaryContainer
                    else -> MaterialTheme.colorScheme.onSurface
                }
            )
        }
    }
}

@Composable
private fun CalendarWeekRow(
    weekStart: LocalDate,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (i in 0..6) {
            Box(modifier = Modifier.weight(1f)) {
                val date = weekStart.plusDays(i.toLong())
                DayCell(
                    date = date,
                    isSelected = date == selectedDate,
                    onDateSelected = onDateSelected
                )
            }
        }
    }
}

@Composable
private fun CalendarGrid(
    currentMonth: YearMonth,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val firstDayOfMonth = currentMonth.atDay(1)
    val dayOfWeekOffset = firstDayOfMonth.dayOfWeek.value % 7

    Column {
        for (week in 0..5) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (day in 1..7) {
                    Box(modifier = Modifier.weight(1f)) {
                        val dayOfMonth = week * 7 + day - dayOfWeekOffset
                        if (dayOfMonth in 1..currentMonth.lengthOfMonth()) {
                            val date = currentMonth.atDay(dayOfMonth)
                            DayCell(
                                date = date,
                                isSelected = date == selectedDate,
                                onDateSelected = onDateSelected
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CollapsibleMiniCalendarPreview() {
    DIUCampusScheduleTheme {
        CollapsibleMiniCalendar(
            selectedDate = LocalDate.now(),
            onDateSelected = {}
        )
    }
}