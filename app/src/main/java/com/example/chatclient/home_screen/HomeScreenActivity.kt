package com.example.chatclient.home_screen

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.example.chatclient.R
import com.example.chatclient.chat_manager.*
import com.example.chatclient.account.AccountInfo
import com.example.chatclient.account.Credentials

class HomeScreenActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

    }


    //bottom navigation bar on click listener
    val onNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.chatlogs_bottom_nav -> {
                    val fragmentChats = ChatsFragment()
                    supportFragmentManager.beginTransaction().add(R.id.fragment_holder_frameLayout, fragmentChats)
                        .commit()
                    return true
                }
                R.id.contacts_bottom_nav -> {
                    val fragmentContacts = ContactsFragment()
                    supportFragmentManager.beginTransaction().add(R.id.fragment_holder_frameLayout, fragmentContacts)
                        .commit()
                    return true
                }
                R.id.groups_bottom_nav -> {
                    val fragmentGroups = GroupsFragment()
                    supportFragmentManager.beginTransaction().add(
                        R.id.fragment_holder_frameLayout, fragmentGroups
                    ).commit()
                    return true
                }
            }
            return false
        }

    }


    //create menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation_drawer, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //navigation menu
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_public_group -> {
                val intentPublicGroupChat = Intent(this, PublicGroupChat::class.java)
                startActivity(intentPublicGroupChat)
            }

            R.id.newGroup -> {
                val intentSetGroupName = Intent(this, SetGroupName::class.java)
                startActivity(intentSetGroupName)

            }

            R.id.menu_sign_out -> { //sign out menu: go to Credentials, to register or log in
                FirebaseAuth.getInstance().signOut()
                val intentRegisterAndLoginActivity = Intent(this, Credentials::class.java)

                intentRegisterAndLoginActivity.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)

                startActivity(intentRegisterAndLoginActivity)
            }

            R.id.account -> {
                val intentAccountInfo = Intent(this, AccountInfo::class.java)

                intentAccountInfo.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)

                startActivity(intentAccountInfo)
            }

        }
        return super.onOptionsItemSelected(item)
    }

}
