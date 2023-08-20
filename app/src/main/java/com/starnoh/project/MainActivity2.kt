package com.starnoh.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.starnoh.project.adapters.TaskAdapter
import com.starnoh.project.helpers.SQLiteTaskHelper
import com.starnoh.project.models.Tasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.ArrayList

class MainActivity2 : AppCompatActivity() {

    private val scope = MainScope()
    private lateinit var theList: ArrayList<Tasks>
    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        loadDataFromDatabase()
    }

    private fun loadDataFromDatabase(){
        scope.launch(Dispatchers.Main){
            val help = SQLiteTaskHelper.getInstance(applicationContext)
            theList = help.getAllItems()
            recycler = findViewById(R.id.recycler)


            val taskAdapter = TaskAdapter(this@MainActivity2,theList)

            recycler.layoutManager = LinearLayoutManager(this@MainActivity2)
            recycler.setHasFixedSize(true)

            recycler.adapter = taskAdapter
        }
    }
}