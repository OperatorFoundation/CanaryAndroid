package org.OperatorFoundation.CanaryLibrary

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test

class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("org.OperatorFoundation.CanaryLibrary.test", appContext.packageName)
    }

//    @Test
//    fun createCanaryInstance() = runTest {
//        // ***** This is the actual API for the Canary Library ******
//        val storageDirectory = Environment.getStorageDirectory()
//        val canary = Canary(configDirectory = storageDirectory, saveDirectory = storageDirectory)
//        assertNotNull(canary)
//        canary.runTest()
//    }

}