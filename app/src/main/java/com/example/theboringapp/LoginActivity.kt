package com.example.theboringapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //If the user is logged in, just go to Main Activity
        if(ParseUser.getCurrentUser() != null)
        {
            goToMainActivity()
            finish()
        }

        findViewById<Button>(R.id.btnLogin).setOnClickListener{
            val username = findViewById<EditText>(R.id.etUsername).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()
            login(username, password)
        }


        findViewById<Button>(R.id.btnRegister).setOnClickListener{
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    //Logs user in in the background so UI doesn't freeze
    private fun login(username: String, password: String)
    {
        ParseUser.logInInBackground(username, password, ({ user, e ->
            if (user != null) {
                // Hooray!  The user is logged in.
                Toast.makeText(this, "Logged in!", Toast.LENGTH_SHORT).show()
                goToMainActivity()
            } else {
                // Signup failed.  Look at the ParseException to see what happened.
                e.printStackTrace()
                Toast.makeText(this, "Error logging in", Toast.LENGTH_SHORT).show()
            }})
        )
    }

    //Sends user to main activity and makes it so the baack button won't return to login activity
    private fun goToMainActivity()
    {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    companion object{
        val TAG = "LoginActivity"
    }
}