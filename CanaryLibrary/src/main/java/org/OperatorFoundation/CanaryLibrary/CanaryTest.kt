package org.OperatorFoundation.CanaryLibrary

import android.content.Context
import androidx.documentfile.provider.DocumentFile
import java.io.File

class CanaryTest(private val configDirectory: DocumentFile, private val timesToRun: Int = 1, private var saveDirectory: File, val context: Context)
{
    fun begin()
    {
        println("\nAttempting to run tests...\n")

        // Make sure we have everything we need first
        if (checkSetup())
        {
            println("✔️ Canary setup complete")
            runAllTests()
        }
        else
        {
            println("\n‼️ Canary setup failed \n")
            return
        }
    }

    private fun runAllTests()
    {
        val testController = TestController(saveDirectory)

        for (i in 1..timesToRun)
        {
            println("\n***************************\nRunning test batch $i of $timesToRun\n***************************\n")

            for (transport in testingTransports)
            {
                println("\n 🧪 Starting test for ${transport.name} 🧪")
                testController.test(transport, context)
            }
        }
    }

    fun checkSetup(): Boolean
    {
        // Does the save directory exist?
        if (!saveDirectory.exists())
        {
            println("\n‼️ The selected save directory does not exist at ${saveDirectory.path}.\n")
            return false
        }
        else if (!saveDirectory.isDirectory)
        {
            println("\n‼️ The selected save directory is not a directory. Please select a directory for saving your results. \nSelected path: ${saveDirectory.path}.\n")
            return false
        }

        println("\n✔️ User selected save directory: ${saveDirectory.path}\n")

        // Does the Config Directory Exist?
        if (!configDirectory.exists())
        {
            println("\n‼️ The selected config directory does not exist at ${configDirectory.name}.\n")
            return false
        }
        else if (!configDirectory.isDirectory)
        {
            println("\n‼️ The selected config directory is not a directory. Please select the directory where your transport config files are located. \nSelected path: ${configDirectory.name}.\n")
            return false
        }

        return prepareTransports()
    }

    private fun prepareTransports(): Boolean
    {
        // Get a list of all of the files in the config directory
        // return false if we are unable to retrieve a list of files
        val configFiles = configDirectory.listFiles() ?: return false

        if (configFiles.isEmpty())
        {
            println("\n‼️ There are no config files in the selected directory: ${configDirectory.name}")
            return false
        }

        configFiles.forEach { configFile ->
            for (transportType in possibleTransportTypes)
            {
                // Check each file name to see if it contains the name of a supported transport
                val filename = configFile.name
                if (filename != null)
                {
                    if (filename.contains(transportType.name, true))
                    {
                        try
                        {
                            val maybeNewTransport = Transport(filename, transportType, configFile, context)
                            testingTransports += maybeNewTransport
                            println("\n✔️ ${maybeNewTransport.name} test is ready\n")
                        }
                        catch (error: Exception)
                        {
                            println("Error creating transport from ${configFile.name}. Error: $error")
                        }
                    }
                }
            }
        }

        if (testingTransports.isEmpty())
        {
            println("‼️ There were no valid transport configs in the provided directory. Ending test.\nConfig Directory: $configDirectory.path")
            return false
        }

        println("\n End prepareTransports \n")
        return true
    }
}