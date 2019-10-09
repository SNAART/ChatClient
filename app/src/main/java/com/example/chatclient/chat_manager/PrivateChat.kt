package com.example.chatclient.chat_manager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_private_chat_log.*
import com.example.chatclient.R
import com.example.chatclient.network.SocketManager
import com.example.chatclient.user.User

class PrivateChat : AppCompatActivity() {
    lateinit var usernameOfDestination: String
    lateinit var adapter: GroupAdapter<ViewHolder>
    private lateinit var userMessagePrivateChatEditText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_private_chat_log)

        //get the user name passed from SelectContactForNewChatActivity
        val userSelectedForNewChat = intent.getParcelableExtra<User>(SelectContactForNewChat.INTENT_EXTRA_KEY)
        usernameOfDestination = userSelectedForNewChat.userName
        supportActionBar?.title = usernameOfDestination

        userMessagePrivateChatEditText = findViewById(R.id.userMessagePrivateChat)

        adapter = GroupAdapter()


        listenToIncomingMessagesFromChatServer()


        //click button to send message to selected user
        buttonSendPrivateChat.setOnClickListener {
            Log.d("PrivateChat", "Click button to send message")

            sendMessage()

        }


        recyclerViewPrivateChat.adapter = adapter


    }


    private fun sendMessage() {
        val message = userMessagePrivateChatEditText.text.toString()

        //pass the selected user name to server for new one-to-one chat, and send message
        SocketManager.send("@$usernameOfDestination " + message.trim())

        val currentUser = FirebaseAuth.getInstance().currentUser

        val outGoingMessage = OutGoingMessage(currentUser!!, message)

        adapter.add(OutgoingMessageItem(outGoingMessage))

        userMessagePrivateChatEditText.text.clear()


        if (message.isNotBlank()) {
            object : SendMessageTaskListener {
                override fun successfullySentMessage() {
                    userMessagePrivateChatEditText.text.clear()
                }

                override fun failedToSendMessage() {
                    Toast.makeText(this@PrivateChat, "This is my Toast message!", Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    private fun listenToIncomingMessagesFromChatServer() {
        //Each time we are notified about a new message coming from chat server we add it to the adapter so that it becomes visible in the view
        val listener = object : IncomingMessageListener {
            override fun incomingMessage(text: String) {

                val currentUser = FirebaseAuth.getInstance().currentUser
                val userSelectedForNewChat = intent.getParcelableExtra<User>(SelectContactForNewChat.INTENT_EXTRA_KEY)
                usernameOfDestination = userSelectedForNewChat.userName
                val myName = currentUser!!.displayName.toString()

                val userNameFrom = text.substringAfterLast("from ")

                if (userNameFrom == usernameOfDestination && text.contains("@$myName")) {

                    val messageFrom = text.substringAfter("@$myName ").substringBeforeLast(" from")
                    val incomingMessage = IncomingMessage(userNameFrom, messageFrom)


                    adapter.add(IncomingMessageItem(incomingMessage))


                    //add messages destined to current user only
                    //add it to the adapter so that it is shown in the screen
                }
            }
        }
        SocketManager.addMessageListener(listener)
    }


}

