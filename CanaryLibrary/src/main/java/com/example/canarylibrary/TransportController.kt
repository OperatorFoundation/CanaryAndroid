package com.example.canarylibrary

import android.telecom.Connection
import java.nio.channels.CompletionHandler

class TransportController(transport: Transport)
{
    var connection: Connection? = null


    fun startTransport(): Connection
    {
        return connection!!
    }

    fun launchShadow()
    {
        //
    }

    fun launchReplicant()
    {
        println("Replicant is not currently supported.")
    }
}