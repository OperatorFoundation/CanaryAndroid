package org.OperatorFoundation.CanaryLibrary

import android.os.Environment
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.operatorfoundation.shadowkotlin.ShadowConfig
import java.io.File

class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("CanaryLibrary.test", appContext.packageName)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun createCanaryInstance() = runTest {
        // ***** This is the actual API for the Canary Library ******
        val configDirectory = Environment.getStorageDirectory()
        val canary = Canary(configDirectory)
        assertNotNull(canary)
        canary.runTest()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun createCanaryTest() = runTest {
        val configDirectory = Environment.getStorageDirectory()
        val chirp = CanaryTest(configDirectory, 1)

        chirp.begin()
    }

    @Test
    fun checkSetupEmptyConfigDir()
    {
        val configDirectory = Environment.getStorageDirectory()
        val chirp = CanaryTest(configDirectory, 1)

        assertFalse(chirp.checkSetup())
    }



    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testController() = runTest {
        val placeholderDirectory = File("NotARealDirectory")
        val testController = TestController(placeholderDirectory)
        val shadowConfig = ShadowConfig("", "DarkStar")
        val canaryConfig = CanaryConfig<ShadowConfig>("", 1234, shadowConfig)
        val transport = Transport("ShadowExample", TransportType.shadow, canaryConfig)

        val result = testController.runTransportTest(transport)
        assertNotNull(result)
    }
}