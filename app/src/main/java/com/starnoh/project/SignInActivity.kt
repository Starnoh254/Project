package com.starnoh.project


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class SignInActivity : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 123
    }

    private lateinit var button: MaterialButton
    private lateinit var defaultTheme: MaterialButton
    private lateinit var dark: MaterialButton
    private lateinit var light: MaterialButton
    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val themePreference2 = getSharedPreferences("ThemePreference", MODE_PRIVATE)
        val selectedTheme = themePreference2.getInt("selectedTheme", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        AppCompatDelegate.setDefaultNightMode(selectedTheme)

        setContentView(R.layout.activity_sign_in)

        // initialize mAuth
        mAuth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this,gso)

        val themePreference = getSharedPreferences("ThemePreference", MODE_PRIVATE)
        val editor = themePreference.edit()

        defaultTheme = findViewById(R.id.defaultTheme)
        defaultTheme.setOnClickListener {
            val theme = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            editor.putInt("selectedTheme",theme)
            editor.apply()
            AppCompatDelegate.setDefaultNightMode(theme)

        }

        dark = findViewById(R.id.Dark)
        dark.setOnClickListener {
            val theme = AppCompatDelegate.MODE_NIGHT_YES
            editor.putInt("selectedTheme",theme)
            editor.apply()
            AppCompatDelegate.setDefaultNightMode(theme)
        }
        light = findViewById(R.id.light)
        light.setOnClickListener {
            val theme = AppCompatDelegate.MODE_NIGHT_NO
            editor.putInt("selectedTheme",theme)
            editor.apply()
            AppCompatDelegate.setDefaultNightMode(theme)
        }




        button = findViewById(R.id.button)
        button.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun signInWithGoogle(){
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken)
            }
            catch (e:ApiException){

            }
        }

    }

    private fun firebaseAuthWithGoogle(idToken:String?){
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this){ authresult ->
                if (authresult.isSuccessful){
                    Log.d("check","amefika hapa")

                    startActivity(Intent(this,MainActivity::class.java))
                    Log.d("check2","amefika hapa")
                }
                else{
                    Log.d("nnnn","reached here")
                    Toast.makeText(this, "Signin failed", Toast.LENGTH_SHORT).show()
                }

        }
    }
}
