package com.starnoh.project.models

import android.app.ActivityManager.TaskDescription

class Tasks(
    var taskId: Int = 0,
    var taskTitle: String = "",
    var taskDescription: String = "",
    var dueDate : String = ""
)