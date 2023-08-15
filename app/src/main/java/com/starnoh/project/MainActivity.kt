package com.starnoh.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.starnoh.project.adapters.TaskAdapter
import com.starnoh.project.helpers.SQLiteTaskHelper

class MainActivity : AppCompatActivity() {

    private lateinit var taskTitle: EditText
    private lateinit var taskDescription: EditText
    private lateinit var dueDate: EditText
    private lateinit var addTask: MaterialButton
    private lateinit var viewTask: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





        taskTitle = findViewById(R.id.taskTitle)
        taskDescription = findViewById(R.id.taskDescription)
        dueDate = findViewById(R.id.dueDate)
        addTask = findViewById(R.id.addTask)
        viewTask = findViewById(R.id.viewTask)

        addTask.setOnClickListener {
            val helper = SQLiteTaskHelper(applicationContext)
            if(taskTitle.text.toString().isEmpty() || taskDescription.text.toString().isEmpty() || dueDate.text.toString().isEmpty()){
                Toast.makeText(applicationContext, "Please add values to the fields ", Toast.LENGTH_SHORT).show()
            }else{
                helper.addTask(taskTitle.text.toString(),taskDescription.text.toString(),dueDate.text.toString())
            }

        }
        viewTask.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity2::class.java)
            startActivity(intent)
        }


    }
}