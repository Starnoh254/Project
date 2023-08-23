package com.starnoh.project

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class Settings : Fragment() {

    private lateinit var  editor: SharedPreferences.Editor
    private lateinit var selectedTheme: String
    private var selectedThemeIndex: Int = 0
    private val themes = arrayOf("System Default", "Dark", "Light")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val themePreference = requireContext().getSharedPreferences("ThemePreference", AppCompatActivity.MODE_PRIVATE)
        editor = themePreference.edit()
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val clicked = view.findViewById<LinearLayout>(R.id.clickableUi)

        clicked.setOnClickListener {
            showDialogueWithRadioButtons()
        }



        return view
    }

    private fun showDialogueWithRadioButtons() {
        selectedTheme = themes[selectedThemeIndex]
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Theme")
            .setSingleChoiceItems(themes,selectedThemeIndex){ dialog_ ,which ->
                selectedThemeIndex = which
                selectedTheme = themes[which]
            }
            .setPositiveButton("Ok"){dialog,which ->
                when(selectedTheme){
                    "System Default" -> {
                        var theme = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                        editor.putInt("selectedTheme",theme)
                        editor.apply()
                        AppCompatDelegate.setDefaultNightMode(theme)
                        Toast.makeText(requireContext(), "system default", Toast.LENGTH_SHORT).show()
                    }
                    "Dark" -> {
                        var theme = AppCompatDelegate.MODE_NIGHT_YES
                        editor.putInt("selectedTheme",theme)
                        editor.apply()
                        AppCompatDelegate.setDefaultNightMode(theme)
                    }
                    else -> {
                        var theme = AppCompatDelegate.MODE_NIGHT_NO
                        editor.putInt("selectedTheme",theme)
                        editor.apply()
                        AppCompatDelegate.setDefaultNightMode(theme)
                    }
                }
            }
            .setNegativeButton("Cancel"){ dialog, which ->
                dialog.dismiss()
            }
            .show()

    }
}