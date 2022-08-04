package org.OperatorFoundation.CanaryLibrary

import org.operatorfoundation.shadowkotlin.ShadowConfig
import org.operatorfoundation.shadowkotlin.ShadowSocket
import java.nio.charset.Charset

class TransportConnectionTest(var transport: Transport)
{
    val textBytes = httpRequestString.toByteArray()

    suspend fun run(): Boolean
    {
        when (transport.transportType)
        {
            TransportType.shadow -> return runShadow()
        }
    }

    suspend fun runShadow(): Boolean
    {
        val shadowConfig: ShadowConfig = transport.config.transportConfig as ShadowConfig
        val shadowSocket = ShadowSocket(shadowConfig, transport.serverIP, transport.port)
        println("\n🧩 Launched ${transport.name}. 🧩")

        val shadowOutputStream = shadowSocket.outputStream
        val shadowInputStream = shadowSocket.inputStream

        try
        {
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
                if (responseString.contains(canaryString))
                {
                    println("\n💕 🐥 It works! 🐥 💕")
                    return true
                }
                else
                {
                    println("\n🖤  We connected but the data did not match. 🖤")
                    println("\nHere's what we got back instead of what we expected: $responseString\n")
                    return false
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
            return false
        }
    }

    fun runReplicant(): Boolean
    {
        println("Replicant is not currently supported.")
        return false
    }
}