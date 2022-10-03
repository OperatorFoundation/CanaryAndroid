package org.OperatorFoundation.CanaryLibrary

import android.content.Context
import android.os.Environment
import android.os.Environment.MEDIA_MOUNTED
import android.os.Environment.MEDIA_MOUNTED_READ_ONLY
import android.util.Log
import org.operatorfoundation.shadowkotlin.ShadowSocket
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*


class TestController(val configDirectory: File, val saveDirectory: File)
{
    fun runTransportTest(transport: Transport): TestResult?
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
        //Todo find the correct way to get the application local file directory.

           // Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val saveFile = File(saveDirectory, resultsFileName)
        println("\n**attempting to save results to $saveDirectory**\n")
        println("\n**results file name is: $resultsFileName**\n")

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

        if (Environment.getExternalStorageState() == MEDIA_MOUNTED)
        {
            FileOutputStream(saveFile).use { output ->
                output.write(resultString.toByteArray())
                println("Added result to: $saveFile")
            }
        }

        if (Environment.getExternalStorageState() in setOf(MEDIA_MOUNTED, MEDIA_MOUNTED_READ_ONLY))
        {
            FileInputStream(saveFile).use { stream ->
                val text = stream.bufferedReader().use {
                    it.readText()
                }
                Log.d("TAG", "Loaded: $text")
            }
        }

        return saveFile.exists()
    }

    fun test(transport: Transport) {

        println("Testing ${transport.name} transport...")

        val transportTestResult = runTransportTest(transport)

        if (transportTestResult == null)
        {
            println("\n Received a null result when testing ${transport.name} transport. \n")
        }
    }
}