package com.starnoh.project.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.starnoh.project.R
import com.starnoh.project.ViewTaskFragment
import com.starnoh.project.helpers.SQLiteTaskHelper
import com.starnoh.project.models.Tasks

class TaskAdapter(val context: Context, var itemList: List<Tasks>) : RecyclerView.Adapter<TaskAdapter.ViewHolder>(){

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var title = itemView.findViewById<MaterialTextView>(R.id.heading)
        var body = itemView.findViewById<MaterialTextView>(R.id.body)
        var deleteById = itemView.findViewById<ImageView>(R.id.deleteById)
        var root = itemView.findViewById<CardView>(R.id.cover)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_task,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.title.text = item.taskTitle
        holder.body.text = item.taskDescription
        holder.deleteById.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete")
            builder.setMessage("Are you sure you want to delete the task ?")
            builder.setPositiveButton("Yes"){dialog,which ->
                val helper = SQLiteTaskHelper.getInstance(context = context)
                helper.deleteTaskById(holder.body.text.toString())
                holder.root.visibility = View.GONE
            }

            builder.setNegativeButton("NO"){dialog,which ->

            }
            builder.setCancelable(false)
            builder.show()

        }
    }
}