package org.OperatorFoundation.CanaryLibrary

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
                port = config.port
            }
        }
    }
}

enum class TransportType
{
    //replicant,
    shadow
}