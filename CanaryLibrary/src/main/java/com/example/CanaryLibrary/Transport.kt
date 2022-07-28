package com.example.CanaryLibrary

class Transport(
    val name: String,
    val transportType: TransportType,
    val config: CanaryConfig<Any>
)
{
    val serverIP: String
    val port: Int

    init
    {
        when (transportType)
        {
            TransportType.shadow ->
            {
                serverIP = config.serverIP
                port = config.serverPort
            }
        }
    }
}

enum class TransportType
{
    //replicant,
    shadow
}