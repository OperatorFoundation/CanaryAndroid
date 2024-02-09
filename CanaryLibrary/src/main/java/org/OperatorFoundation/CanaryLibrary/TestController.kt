package org.OperatorFoundation.CanaryLibrary

import android.content.Context
import android.os.Environment
import android.os.Environment.MEDIA_MOUNTED
import android.os.Environment.MEDIA_MOUNTED_READ_ONLY
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class TestController(private val saveDirectory: File)
{
    fun runTransportTest(transport: Transport, context: Context): TestResult
    {
        val hostString = transport.serverIP + ":${transport.port}"

        if (transport.serverIP.isEmpty())
        {
            val invalidTestName = "invalid_${transport.name}"
            val invalidTestResponse = TestResult(hostString, Date(), invalidTestName, false)
            save(invalidTestResponse, invalidTestName)

            return invalidTestResponse
        }
        else
        {
            // Connection test
            val connectionTest = TransportConnectionTest(transport)
            val success = connectionTest.run(context)

            // Save the result to a file
            val result = TestResult(hostString, Date(), transport.name, success)
            save(result, transport.name)

            return result
        }
    }

    // Saves the provided test results to a csv file with a filename that contains a timestamp.
    // If a file with this name already exists it will append the results to the end of the file.
    // - Parameter result: The test result information to be saved. The type is a TestResult struct.
    // - Returns: A boolean value indicating whether or not the results were saved successfully.
    private fun save(result: TestResult, testName: String): Boolean
    {
        if (!saveDirectory.isDirectory)
        {
            println("Unable to save the results file: the selected directory is not a directory or doesn't exist: $saveDirectory")
            return false
        }

        if (Environment.getExternalStorageState() != MEDIA_MOUNTED)
        {
            println("Unable to save the results file: external storage is not available for reading/writing")
            return false
        }

        val currentDate = Calendar.getInstance()
        val day = SimpleDateFormat("dd")
        val month = SimpleDateFormat("MM")
        val year = SimpleDateFormat("yyyy")
        val dayString = day.format(currentDate.time).toString()
        val monthString = month.format(currentDate.time).toString()
        val yearString = year.format(currentDate.time).toString()
        val dateString = yearString + "_" + monthString + "_" + dayString
        val fileNameWithDate = resultsFileName + dateString + resultsFileExtension
        val saveFile = File(saveDirectory, fileNameWithDate)

        println("\n**attempting to save results to $saveDirectory**\n")
        println("\n**results file name is: $fileNameWithDate**\n")

        if (!saveFile.exists())
        {
            // Make a new csv file for our test results
            saveFile.createNewFile()

            // The first row should be our labels
            val labelRow = "TestDate, ServerIP, Transport, Success\n"
            saveFile.appendText(labelRow)
            saveFile.appendText("/n")
        }

        // Add our newest test results to the file
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

    fun test(transport: Transport, context: Context) {

        println("Testing ${transport.name} transport...")

        val transportTestResult = runTransportTest(transport, context)
        println("${transport.name} test complete. Result: $transportTestResult.")
    }
}