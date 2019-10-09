package com.example.chatclient.account

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signup_and_signin.*
import com.example.chatclient.home_screen.HomeScreenActivity
import com.example.chatclient.R
import com.example.chatclient.network.SocketManager
import com.example.chatclient.user.UserManagement


class Credentials : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_and_signin)

        //sign up button operations
        buttonSignUp.setOnClickListener {
            signUp()
        }

        //sign in button operations
        buttonSignIn.setOnClickListener {
            signIn()
        }


        //cancel button, exit process
        buttonCancel.setOnClickListener {
            finish()
        }


    }


    private fun signUp() {
        val userEmail = email_EditText_SignUpActivity.text.toString()
        val userPassword = password_EditText_SignUpActivity.text.toString()
        val userName = userName_EditText_SignUpActivity.text.toString()

        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            Toast.makeText(this, "email or passowrd is empty", Toast.LENGTH_LONG).show()
        }

        Log.d("Credentials", "signUp() with Email, Password,Username : ($userEmail,$userPassword,$userName")


        //Firebase authentication to create a user with email and password
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    return@addOnCompleteListener
                } else {
                    UserManagement.setUsername(userName)

                    SocketManager.send(":user $userName")
                    Log.d("SignUp", "User registered to the server")
                    Log.d("Credentials", "successfully created user.")
                    Toast.makeText(this, "successfully registered", Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener {
                Log.d("Credentials", "Failed to create user: ${it.message}")
                Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_LONG).show()

            }
        Log.d("Credentials", "test signUp")
    }

    private fun signIn() {
        val userEmail = email_EditText_SignUpActivity.text.toString()
        val userPassword = password_EditText_SignUpActivity.text.toString()
        val userName = userName_EditText_SignUpActivity.text.toString()

        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            Toast.makeText(this, "email or passowrd is empty", Toast.LENGTH_LONG).show()
        }

        Log.d("Credentials", "signIn() with Email, Password : ($userEmail,$userPassword")


        //Firebase authentication to log in with email and password
        FirebaseAuth.getInstance().signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    return@addOnCompleteListener
                }

                //else if log in successful, going to PublicGroupChat
                else {
                    Log.d("Credentials", "successfully logged inn")

                    SocketManager.send(":user $userName")
                    Log.d("SignIn_Activity", "pass user name to server")

                    password_EditText_SignUpActivity.text.clear()


                    val intentHomeScreenActivity = Intent(this, HomeScreenActivity::class.java)
                    startActivity(intentHomeScreenActivity)

                    Log.d("Credentials", "going to HomeScreenActivity")
                }
            }
            .addOnFailureListener {
                Log.d("Credentials", "Failed to log in: ${it.message}")
                Toast.makeText(this, "Failed to log in: ${it.message}", Toast.LENGTH_LONG).show()

            }
        Log.d("Credentials", "test log in")
    }


}
