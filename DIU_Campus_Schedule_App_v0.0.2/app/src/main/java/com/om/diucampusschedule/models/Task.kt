package com.om.diucampusschedule.models

import java.util.UUID

data class Task(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val description: String,
    val date: String,
    val time: String,
    val isCompleted: Boolean
)
