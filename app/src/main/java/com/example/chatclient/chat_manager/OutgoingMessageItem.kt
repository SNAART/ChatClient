package com.example.chatclient.chat_manager

import com.google.firebase.auth.FirebaseUser
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import com.example.chatclient.R
import kotlinx.android.synthetic.main.outgoing_message.view.*

class OutgoingMessageItem(private val outGoingMessage: OutGoingMessage): Item<ViewHolder>() {

override fun getLayout(): Int {
    return R.layout.outgoing_message
}

override fun bind(viewHolder: ViewHolder, position: Int) {
    //show message at the message text view

    viewHolder.itemView.outgoingMesssage.text = outGoingMessage.message
    viewHolder.itemView.textView_userName_outgoingMessage.text = outGoingMessage.user.displayName


}


}

class OutGoingMessage(val user: FirebaseUser, val message:String)