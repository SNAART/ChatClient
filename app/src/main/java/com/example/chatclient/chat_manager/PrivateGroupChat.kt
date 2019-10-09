package com.example.chatclient.chat_manager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_new_group_chat.*
import com.example.chatclient.R
import com.example.chatclient.network.SocketManager

class PrivateGroupChat : AppCompatActivity() {
    private lateinit var userMessage_EditText: EditText
    lateinit var adapter: GroupAdapter<ViewHolder>




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_group_chat)

        val groupName = intent.getStringExtra("GROUP NAME")

        supportActionBar?.title = "Group $groupName"

        adapter = GroupAdapter()


        listenToIncomingMessagesFromChatServer()

        recyclerViewNewGroupChat.adapter = adapter



        userMessage_EditText = findViewById(R.id.userMessageNewGroupChat)

        buttonSendNewGroupChat.setOnClickListener{
            Log.d("PrivateGroupChat", "Click button to send message")

            sendMessage()
        }
    }



    private fun sendMessage() {
        val message = userMessage_EditText.text.toString()
        val groupName = intent.getStringExtra("GROUP NAME")

        val currentUser = FirebaseAuth.getInstance().currentUser

        val outGoingMessage = OutGoingMessage(currentUser!!,message)

        adapter.add(OutgoingMessageItem(outGoingMessage))



        if (message.isNotBlank()){
            println(message)
            SocketManager.send("@$groupName " + message.trim())
            userMessage_EditText.text.clear()
        }

    }


    private fun listenToIncomingMessagesFromChatServer() {
        val listener = object : IncomingMessageListener {
            override fun incomingMessage(text: String) {
                val userNameFrom = text.substringAfterLast("from ")

                val currentUser = FirebaseAuth.getInstance().currentUser
                val groupName = intent.getStringExtra("GROUP NAME")


                if (userNameFrom == currentUser!!.displayName){
                    //do not show message sent by self
                }else if(text.contains("@$groupName")){  //add messages to this group pnly
                    val messageFrom = text.substringAfter(":").substringBeforeLast(" from")
                    val incomingMessage = IncomingMessage(userNameFrom, messageFrom)
                    adapter.add(IncomingMessageItem(incomingMessage))
                }
            }
        }
        SocketManager.addMessageListener(listener)
    }





}
