package org.OperatorFoundation.CanaryLibrary

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.operatorfoundation.shadowkotlin.ShadowConfig
import java.io.File

class Transport(val name: String, val transportType: TransportType, val configFile: File)
{
    val serverIP: String
    val port: Int
    val config: Any

    init
    {
        when (transportType)
        {
            TransportType.Shadow ->
            {
                val configString = configFile.readText()
                val shadowConfig: ShadowConfig = Json.decodeFromString(configString)

                config = shadowConfig

                requireNotNull(shadowConfig.serverIP)
                serverIP = shadowConfig.serverIP!!

                requireNotNull(shadowConfig.port)
                port = shadowConfig.port!!
            }
        }
    }
}

enum class TransportType
{
    //Replicant,
    Shadow
}