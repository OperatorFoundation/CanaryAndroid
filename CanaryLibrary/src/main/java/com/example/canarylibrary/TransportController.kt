package com.example.canarylibrary

import android.telecom.Connection
import java.nio.channels.CompletionHandler

class TransportController(transport: Transport)
{
//    val transportQueue
    var connectionCompletion: Connection? = null
    var connection: Connection? = null


//    fun startTransport(completionHandler: (Connection) -> Unit)
//    {
//        ///
//    }

//    fun handleStateUpdate(newState: NWConnection.State)
//    {
////        val completion = connectionCompletion ?: return
//    }

    fun launchShadow()
    {
        //
    }

    fun launchReplicant()
    {
        println("Replicant is not currently supported.")
    }
}