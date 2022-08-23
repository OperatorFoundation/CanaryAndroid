package org.OperatorFoundation.CanaryLibrary

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test
import org.operatorfoundation.shadowkotlin.ShadowConfig

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun serializeCanaryConfig()
    {
        val shadowConfig = ShadowConfig("password", "DarkStar")
        val shadowConfigString = Json.encodeToString(shadowConfig)
        println("\n*** Example Shadow Config JSON ***")
        println("$shadowConfigString\n")

        val canaryConfig: CanaryConfig<ShadowConfig> = CanaryConfig("0.0.0.0", 1234, shadowConfig)
        val canaryConfigString = Json.encodeToString(canaryConfig)
        println("*** Example Canary Config JSON ***")
        println("$canaryConfigString\n")

        val configFromJson: CanaryConfig<ShadowConfig> = Json.decodeFromString(canaryConfigString)
        assert(configFromJson.transportConfig.password == canaryConfig.transportConfig.password)
    }
}