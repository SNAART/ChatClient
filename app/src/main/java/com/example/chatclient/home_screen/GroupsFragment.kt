package com.example.chatclient.home_screen


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.chatclient.R
import com.example.chatclient.chat_manager.IncomingMessageListener
import com.example.chatclient.chat_manager.PrivateGroupChat
import com.example.chatclient.chat_manager.PublicGroupChat
import com.example.chatclient.network.SocketManager


/**
 * A simple [Fragment] subclass.
 *
 */
class GroupsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_groups, container, false)
        // Inflate the layout for this fragment

        val groupsListView: ListView = view.findViewById(R.id.listView_FragmentGroups)

        val messages = mutableListOf("Available Groups: ", "Public Group")

        val adapter = ArrayAdapter<String>(activity, R.layout.adapter_text_view, messages)

        groupsListView.adapter = adapter

        SocketManager.send(":groups")

        val listener = object : IncomingMessageListener {
            override fun incomingMessage(text: String) {
                adapter.add(text) //add it to the adapter so that it is shown in the screen (ListView)
            }
        }
        SocketManager.addMessageListener(listener)

        groupsListView.setOnItemClickListener{ adapterView: AdapterView<*>, view1: View, position: Int, l: Long ->

            // go to the group chat of the clicked group

            if (position == 1){
               goToPublicGroup()
           }else{
               val groupName = adapter.getItem(position).toString()
                val intentNewGroupChat = Intent(activity, PrivateGroupChat::class.java)
               intentNewGroupChat.putExtra("GROUP NAME", groupName)
                startActivity(intentNewGroupChat)
           }

        }


        return view
    }

    fun goToPublicGroup(){
        val intentPublicGroupChat = Intent(activity, PublicGroupChat::class.java)
        startActivity(intentPublicGroupChat)
    }


}
