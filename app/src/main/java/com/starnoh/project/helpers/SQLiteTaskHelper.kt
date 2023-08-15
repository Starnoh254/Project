package com.starnoh.project.helpers
// jj
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.starnoh.project.MainActivity
import com.starnoh.project.models.Tasks

class SQLiteTaskHelper( var context: Context): SQLiteOpenHelper(context,"task.db",null,3) {



    override fun onCreate(sql: SQLiteDatabase?) {
        // create your database table
        sql?.execSQL("CREATE TABLE IF NOT EXISTS task1(task_id integer primary key autoincrement, task_title text, task_description text, due_date text)")
    }

    override fun onUpgrade(sql: SQLiteDatabase?, p1: Int, p2: Int) {
        sql?.execSQL("DROP TABLE IF EXISTS task1")
    }
    private fun restartActivity(){
        val intent = Intent(context,MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    fun addTask ( task_title : String, task_description: String, due_date: String){


        val values = ContentValues()
        values.put("task_description",task_description)
        values.put("task_title",task_title)
        values.put("due_date",due_date)

        // here , we use the .use function to efficiently manage resources ( database connection ),
        // so the connection will be terminated if no longer need it !!
        val db = this.writableDatabase
        val result : Long = db.insert("task",null,values)
        // Check if there is result , -1 means an error occurred

        if (result <= 0){
            Toast.makeText(context, "Failed to add task", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Task added successfully", Toast.LENGTH_SHORT).show()
            // after the task is added successfully , reload the activity
            restartActivity()
        }

    }
    @SuppressLint("Range")
    fun getTaskByIdDescription(task_description: String): Int?{
        val query = "select task_id from task1 where task_description = ? "
        val cursor: Cursor = readableDatabase.rawQuery(query, arrayOf(task_description))

        var taskId : Int? = null
        if (cursor.moveToFirst()){
            taskId = cursor.getInt(cursor.getColumnIndex("task_id"))
        }
        cursor.close()
        return taskId
    }

    // we need to implement a function that enables a user to delete a task by their id
    // the requirements are the task id
    fun deleteTaskById(task_description: String){

        val task_id = getTaskByIdDescription(task_description)
        val result: Int = writableDatabase.use { db ->
            db.delete("task","task_id = ?", arrayOf(task_id.toString()))
        }
        if (result == 0){
            Toast.makeText(context, "The deletion was unsuccessful ", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context, " The deletion was successful", Toast.LENGTH_SHORT).show()
        }
    }


    @SuppressLint("Range")
    fun getAllItems () : ArrayList<Tasks>{
        val tasks  = ArrayList<Tasks>()
        val db = this.readableDatabase
        val query = "select * from task"
        val cursor: Cursor = db.rawQuery(query,null)

       while (cursor.moveToFirst()){
            val model = Tasks()
           model.taskId = cursor.getInt(cursor.getColumnIndex("task_id"))
           model.taskDescription = cursor.getString(cursor.getColumnIndex("task_description"))
           model.taskTitle = cursor.getString(cursor.getColumnIndex("task_title"))
           model.dueDate = cursor.getString(cursor.getColumnIndex("due_date"))

           tasks.add(model)
       }

        cursor.close()
        db.close()

        return tasks
    }


}