package com.example.theboringapp

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseFile
import com.parse.ParseUser
import java.io.ByteArrayOutputStream

class RegisterActivity : AppCompatActivity() {
    private val pickImage = 100
    //Shrek Image for now, feel free to upload a new default
    private var imageUri : Uri? = Uri.parse("android.resource://" + "com.example.theboringapp"+ "/" + R.drawable.shrek);
    lateinit var bitmap: Bitmap
    lateinit var imageview: ImageView
    lateinit var signup: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        fun Uri.toBitmap(context: Context?): Bitmap {
            return MediaStore.Images.Media.getBitmap(context?.contentResolver, this)
        }

        bitmap = imageUri!!.toBitmap(this)
        imageview = findViewById(R.id.ivRegisterProfileImage)
        signup = findViewById(R.id.btnRegisterSignUp)

        imageview.setOnClickListener{
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        signup.setOnClickListener{
            val username = findViewById<EditText>(R.id.etRegisterUsername).text.toString()
            val password = findViewById<EditText>(R.id.etRegisterPassword).text.toString()

            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG ,100, stream)
            val bitmapBytes: ByteArray = stream.toByteArray()

            val image : ParseFile = ParseFile("profileImage.png", bitmapBytes)
            //I TRIED SAVE IN BACKGROUND A LOT OF TIMES BUT IT NEVER WORKED, SOMETHING ABOUT ABIGUOUS OVERLOAD
            image.save()
            signup(username, password, image)



        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {

            fun Uri.toBitmap(context: Context?): Bitmap {
                return MediaStore.Images.Media.getBitmap(context?.contentResolver, this)
            }
            imageUri = data?.data

            bitmap = imageUri!!.toBitmap(this)

            imageview.setImageURI(imageUri)
        }
    }

    private fun signup(username:String, password:String, profilePic: ParseFile)
    {
        // Create the ParseUser
        val user = ParseUser()

        // Set fields for the user to be created
        user.setUsername(username)
        user.setPassword(password)
        user.put("profilePic", profilePic)
        user.signUpInBackground { e ->
            if (e == null) {
                // Hooray! Let them use the app now.
                Toast.makeText(this, "Signed up",Toast.LENGTH_SHORT).show()
                goToLoginActivity()

            } else {
                // Sign up didn't succeed. Look at the ParseException
                // to figure out what went wrong
                e.printStackTrace()
                Toast.makeText(this, "Couldn't sign up",Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Sends user to main activity and makes it so the baack button won't return to login activity
    private fun goToLoginActivity()
    {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}