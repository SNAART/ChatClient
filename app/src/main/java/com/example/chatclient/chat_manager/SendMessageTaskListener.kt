package com.example.chatclient.chat_manager

interface SendMessageTaskListener {

    fun successfullySentMessage()

    fun failedToSendMessage()

}