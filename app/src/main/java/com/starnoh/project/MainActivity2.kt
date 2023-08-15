package com.starnoh.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.starnoh.project.adapters.TaskAdapter
import com.starnoh.project.helpers.SQLiteTaskHelper

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val recycler = findViewById<RecyclerView>(R.id.recycler)

        val help = SQLiteTaskHelper(applicationContext)
        val theList = help.getAllItems()
        val taskAdapter = TaskAdapter(applicationContext,theList)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)

        recycler.adapter = taskAdapter

    }
}