package com.example.chatclient.chat_manager

import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import com.example.chatclient.R
import kotlinx.android.synthetic.main.incoming_message.view.*

class IncomingMessageItem(val incomingMessage: IncomingMessage) : Item<ViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.incoming_message
    }


    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.incomingMessage.text = incomingMessage.message
        viewHolder.itemView.textView_userName_incomingMessage.text = incomingMessage.userName


    }


}

class IncomingMessage(val userName: String, val message: String)