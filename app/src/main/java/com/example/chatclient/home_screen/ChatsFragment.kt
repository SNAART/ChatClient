package com.example.chatclient.home_screen


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.chatclient.R
import com.example.chatclient.chat_manager.IncomingMessageListener
import com.example.chatclient.network.SocketManager


/**
 * A simple [Fragment] subclass.
 *
 */
class ChatsFragment : Fragment() {
    lateinit var messages:MutableList<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_chats, container, false)

        val messagesView: ListView = view.findViewById(R.id.listView_FragmentChats)

         messages = mutableListOf("History messages:")

        val adapter = ArrayAdapter<String>(activity, R.layout.adapter_text_view, messages)

        messagesView.adapter = adapter

        listenForIncomingMessage(adapter)

        SocketManager.send(":messages")


        showChatHistory(view)

        // Inflate the layout for this fragment
        return view

    }


    private fun showChatHistory(view: View) {
        val messagesView: ListView = view.findViewById(R.id.listView_FragmentChats)

        val messages = mutableListOf("History messages:")

        val adapter = ArrayAdapter<String>(activity, R.layout.adapter_text_view, messages)

        messagesView.adapter = adapter

        listenForIncomingMessage(adapter)

        SocketManager.send(":messages")

    }


    private fun listenForIncomingMessage(adapter: ArrayAdapter<String>) {
        val listener = object : IncomingMessageListener {
            override fun incomingMessage(text: String) {
                //add it to the adapter so that it is shown in the screen (ListView)

                adapter.add(text)
            }
        }
        SocketManager.addMessageListener(listener)
    }


}
