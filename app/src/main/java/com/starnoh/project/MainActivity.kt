package com.starnoh.project

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.starnoh.project.adapters.TaskAdapter
import com.starnoh.project.helpers.SQLiteTaskHelper
import java.util.Calendar

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
        dueDate.setOnClickListener {
            showDatePickerDialog()
        }
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

    private fun showDatePickerDialog(){
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)
        
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view : DatePicker?, selectedYear: Int, selectedMonth:Int, selectedDay : Int ->
                var selectedDate = "$selectedYear - ${selectedMonth + 1} - $selectedDay"
                dueDate.setText(selectedDate)

            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }
}