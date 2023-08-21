package com.starnoh.project


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = AddTaskFragment()
        val viewTaskFragment = ViewTaskFragment()

        changeFragment(homeFragment)

        var bottom_nav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottom_nav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> changeFragment(homeFragment)
                R.id.task -> changeFragment(viewTaskFragment)
            }
            true
        }






    }

    private fun changeFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer,fragment).commit()
    }


}