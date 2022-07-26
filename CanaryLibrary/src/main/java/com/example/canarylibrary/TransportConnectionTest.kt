package com.example.canarylibrary

import android.telecom.Connection
import java.nio.channels.CompletionHandler

class TransportConnectionTest(transportConnection: Connection, canaryString: String?)
{
//    var readBuffer = ByteArray()

    fun send(): Error?
    {
        return null
    }

    fun read(): ByteArray
    {
        return ByteArray(0)
    }

    fun run(): Boolean
    {
        val maybeError = send()
        //

        val response = read()

        //

        return true
    }
}