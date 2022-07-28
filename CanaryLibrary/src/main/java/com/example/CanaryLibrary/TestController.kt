package com.example.CanaryLibrary

import android.os.Environment
import android.os.Environment.MEDIA_MOUNTED
import java.io.File
import java.util.*


class TestController()
{
    suspend fun runTransportTest(transport: Transport): TestResult?
    {
        // Connection test
        val connectionTest = TransportConnectionTest(transport)
        val success = connectionTest.run()

        // Save the result to a file
        val hostString = transport.serverIP + ":${transport.port}"
        val result = TestResult(hostString, Date(), transport.name, success)
        save(result, transport.name)

        return null
    }

    // Saves the provided test results to a csv file with a filename that contains a timestamp.
    // If a file with this name already exists it will append the results to the end of the file.
    // - Parameter result: The test result information to be saved. The type is a TestResult struct.
    // - Returns: A boolean value indicating whether or not the results were saved successfully.
    fun save(result: TestResult, testName: String): Boolean
    {
        if (Environment.getExternalStorageState() != MEDIA_MOUNTED)
        {
            println("Unable to save the results file: external storage is not available for reading/writing")
            return false
        }

        val extDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val saveFile = File(extDir, resultsFileName)

        // Make sure the Documents directory exists.
        extDir.mkdirs()

        if (!saveFile.exists())
        {
            // Make a new csv file for our test results
            saveFile.createNewFile()

            // The first row should be our labels
            val labelRow = "TestDate, ServerIP, Transport, Success\n"
            saveFile.appendText(labelRow)
        }

        // Add out newest test results to the file
        val resultString = "${result.testDate}, ${result.hostString}, $testName, ${result.success}\n"
        saveFile.appendText(resultString)

        return saveFile.exists()
    }

    suspend fun test(transport: Transport) {
        println("Testing ${transport.name} transport...")

        val transportTestResult = runTransportTest(transport)

        if (transportTestResult == null)
        {
            println("\n Received a null result when testing ${transport.name} transport. \n")
        }
    }
}