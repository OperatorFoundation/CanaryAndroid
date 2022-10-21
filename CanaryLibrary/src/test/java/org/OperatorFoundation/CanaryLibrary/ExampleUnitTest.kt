package org.OperatorFoundation.CanaryLibrary

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Test
import org.operatorfoundation.shadowkotlin.ShadowConfig

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest
{
    @Test
    fun serializeCanaryConfig()
    {
        val shadowConfig = ShadowConfig("password", "DarkStar")
        val shadowConfigString = Json.encodeToString(shadowConfig)
        println("\n*** Example Shadow Config JSON ***")
        println("$shadowConfigString\n")

        val configFromJson: ShadowConfig = Json.decodeFromString(shadowConfigString)
        assert(configFromJson.password == shadowConfig.password)
    }
}