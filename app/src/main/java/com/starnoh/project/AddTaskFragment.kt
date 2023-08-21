package com.starnoh.project

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.starnoh.project.helpers.SQLiteTaskHelper
import java.util.*


class AddTaskFragment : Fragment() {


    private lateinit var taskTitle: EditText
    private lateinit var taskDescription: EditText
    private lateinit var dueDate: EditText
    private lateinit var addTask: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_task, container, false)

        taskTitle = view.findViewById(R.id.taskTitle)
        taskDescription = view.findViewById(R.id.taskDescription)
        dueDate = view.findViewById(R.id.dueDate)
        dueDate.setOnClickListener {
            showDatePickerDialog()
        }
        addTask = view.findViewById(R.id.addTask)

        addTask.setOnClickListener {
            val helper = SQLiteTaskHelper.getInstance(requireContext())
            if(taskTitle.text.toString().isEmpty() || taskDescription.text.toString().isEmpty() || dueDate.text.toString().isEmpty()){
                Toast.makeText(requireContext(), "Please add values to the fields ", Toast.LENGTH_SHORT).show()
            }else{
                helper.addTask(taskTitle.text.toString(),taskDescription.text.toString(),dueDate.text.toString())
            }

        }


        val button2 = view.findViewById<MaterialButton>(R.id.button2)
        button2.setOnClickListener {
            signOut()
            val intent = Intent(requireContext() , SignInActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun showDatePickerDialog(){
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { view : DatePicker?, selectedYear: Int, selectedMonth:Int, selectedDay : Int ->
                var selectedDate = "$selectedYear - ${selectedMonth + 1} - $selectedDay"
                dueDate.setText(selectedDate)

            },
            year,
            month,
            day
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }
    private fun signOut(){
        Firebase.auth.signOut()
    }


}