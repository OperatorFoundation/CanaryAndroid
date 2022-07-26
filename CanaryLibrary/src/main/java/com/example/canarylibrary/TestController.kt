package com.example.canarylibrary

import java.sql.Connection
import java.util.*


class TestController()
{
    // ignore shared instance from Swift for now..

    fun runSwiftTransportTest(transport: Transport): TestResult?
    {
        val transportController = TransportController(transport)

        val connection = transportController.startTransport()
//
//        // Connection test
//        val connectionTest = TransportConnectionTest(connection, canaryString)
//        val success = connectionTest.run()
//
//        // Save the result to a file
//        val hostString = transport.serverIP + ":${transport.port}"
//        val result = TestResult(hostString, Date(), transport.name, success)
//        save(result, transport.name)

        Thread.sleep(1_000)
        return null
    }

    fun runWebTest(webTest: WebTest): TestResult?
    {
        var result: TestResult?

        // Connection Test
        val connectionTest = ConnectionTest(webTest.website, null)
        val success = connectionTest.run()

        result = TestResult(webTest.website, Date(), webTest.name, success)

        // Save this result to a file
        save(result!!, webTest.name)

        Thread.sleep(2_000)
        return result
    }

    // Saves the provided test results to a csv file with a filename that contains a timestamp.
    // If a file with this name already exists it will append the results to the end of the file.
    // - Parameter result: The test result information to be saved. The type is a TestResult struct.
    // - Returns: A boolean value indicating whether or not the results were saved successfully.
    fun save(result: TestResult, testName: String): Boolean
    {
//        val resultString = "${result.testDate}, ${result.hostString}, $testName, ${result.success}\n"
//
//        val resultData = resultString.toByte() ?: return false
//
//        do
//        {
//          //
//        }

        return true
    }

    fun test(transport: Transport, userInterface: String?, debugPrints: Boolean = false)
    {
        println("Testing ${transport.name} transport...")

        val transportTestResult = this.runSwiftTransportTest(transport)

        if (transportTestResult == null)
        {
            println("\n Received a null result when testing ${transport.name} transport. \n")
        }
    }

    fun test(webTest: WebTest, userInterface: String?, debugPrints: Boolean = false)
    {
        println("Testing web address $webTest...")

        val webTestResult = this.runWebTest(webTest)

        if (webTestResult == null)
        {
            println("\n Received a null when result when testing ${webTest.name} web address.")
        }
    }

    fun getNowAsString(): String
    {
        // TODO: Figure out Kotlin equivalent of Swift ISO8601DateFormatter()

        return ""
    }
}