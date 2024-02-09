package org.OperatorFoundation.CanaryLibrary

import android.content.Context
import android.util.Log
import org.operatorfoundation.shadow.ShadowConfig
import org.operatorfoundation.shadow.ShadowSocket

import java.nio.charset.Charset

class TransportConnectionTest(private var transport: Transport)
{
    private val textBytes = httpRequestString.toByteArray()

    fun run(context: Context): Boolean
    {
        when (transport.transportType)
        {
            TransportType.Shadow -> return runShadow(context)
        }
    }

    private fun runShadow(context: Context): Boolean
    {
        val shadowConfig: ShadowConfig = transport.config as ShadowConfig

        try
        {
            val shadowSocket = ShadowSocket(shadowConfig, context)
            println("\n🧩 Launched ${transport.name}. 🧩")

            val shadowOutputStream = shadowSocket.outputStream
            val shadowInputStream = shadowSocket.inputStream
            println("\n📣 Running a shadow connection test.")
            // Send our http request
            shadowOutputStream.write(textBytes)
            shadowOutputStream.flush()
            println("Wrote ${textBytes.size} bytes.")

            // Try to read a response
            val buffer = ByteArray(235)
            val numberOfBytesRead = shadowInputStream.read(buffer)
            println("Read $numberOfBytesRead bytes.")

            if (numberOfBytesRead > 0)
            {
                val responseString = buffer.toString(Charset.defaultCharset())

                return if (responseString.contains(canaryString))
                {
                    println("\n💕 🐥 It works! 🐥 💕")
                    true
                }
                else {
                    println("\n🖤  We connected but the data did not match. 🖤")
                    println("\nHere's what we got back instead of what we expected: $responseString\n")
                    false
                }
            }
            else if (numberOfBytesRead == -1)
            {
                println("🚫 Test failed: Attempted to read from the network but received EOF 🚫\n")
                return false
            }
            else
            {
                println("🚫 Test failed: We did not receive a response from the server 🚫\n")
                return false
            }
        }
        catch (error: Exception)
        {
            println("--> Received an error while attempting to create a connection: $error")
            println("--> Server IP: ${transport.serverIP}")
            println("--> Server Port: ${transport.port}")
            println("--> Server PublicKey: ${shadowConfig.serverPublicKey}")
            return false
        }
    }

    fun runReplicant(): Boolean
    {
        println("Replicant is not currently supported.")
        return false
    }
}