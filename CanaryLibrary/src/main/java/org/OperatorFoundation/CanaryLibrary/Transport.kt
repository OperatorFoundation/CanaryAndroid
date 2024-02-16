package org.OperatorFoundation.CanaryLibrary

import android.content.Context
import androidx.documentfile.provider.DocumentFile
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.operatorfoundation.shadow.ShadowConfig
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

class Transport(val name: String, val transportType: TransportType, configFile: DocumentFile, context: Context)
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
                val inputStream = context.contentResolver.openInputStream(configFile.uri)
                val reader = BufferedReader(InputStreamReader(inputStream))
                val configString = reader.readText()
                val shadowConfig: ShadowConfig = Json.decodeFromString(configString)

                config = shadowConfig

                requireNotNull(shadowConfig.serverIP)
                serverIP = shadowConfig.serverIP

                requireNotNull(shadowConfig.port)
                port = shadowConfig.port
            }
        }
    }
}

enum class TransportType
{
    Shadow
}