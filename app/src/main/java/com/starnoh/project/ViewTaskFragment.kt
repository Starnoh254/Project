package com.starnoh.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.starnoh.project.adapters.TaskAdapter
import com.starnoh.project.helpers.SQLiteTaskHelper
import com.starnoh.project.models.Tasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

import java.util.ArrayList


class ViewTaskFragment : Fragment() {

    private val scope = MainScope()
    private lateinit var theList: ArrayList<Tasks>
    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_view_task, container, false)
        loadDataFromDatabase()
        return view
    }

    private fun loadDataFromDatabase(){
        scope.launch(Dispatchers.Main){
            val help = SQLiteTaskHelper.getInstance(requireContext())
            theList = help.getAllItems()
            recycler = requireView().findViewById(R.id.recycler)


            val taskAdapter = TaskAdapter(requireContext(),theList)

            recycler.layoutManager = LinearLayoutManager(requireContext())
            recycler.setHasFixedSize(true)

            recycler.adapter = taskAdapter
        }
    }

}