package com.example.CanaryLibrary

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.operatorfoundation.shapeshifter.shadow.kotlin.ShadowConfig
import java.io.File

class CanaryTest(val configDirectory: File, val timesToRun: Int = 1, var saveDirectory: File? = null)
{
    suspend fun begin()
    {
        println("\n attempting to run tests...\n")

        // Make sure we have everything we need first
        if (!checkSetup())
        {
            return
        }

        runAllTests()
    }

    private suspend fun runAllTests()
    {
        val testController = TestController()

        for (i in 1..timesToRun)
        {
            println("\n***************************\nRunning test batch $i of $timesToRun\n***************************\n")

            for (transport in testingTransports)
            {
                println("\n 🧪 Starting test for ${transport.name} 🧪")
                testController.test(transport)
            }
        }
    }

    fun checkSetup(): Boolean
    {
        if (saveDirectory != null)
        {
            // Does the save directory exist?
            if (!saveDirectory!!.exists())
            {
                println("\n‼️ The selected save directory does not exist at ${saveDirectory!!.path}.\n")
                return false
            }
            else if (!saveDirectory!!.isDirectory)
            {
                println("\n‼️ The selected save directory is not a directory. Please select a directory for saving your results. \nSelected path: ${saveDirectory!!.path}.\n")
                return false
            }

            println("\n✔️ User selected save directory: ${saveDirectory!!.path}\n")
        }

        // Does the Config Directory Exist?
        if (!configDirectory.exists())
        {
            println("\n‼️ The selected config directory does not exist at ${configDirectory.path}.\n")
            return false
        }
        else if (!configDirectory.isDirectory)
        {
            println("\n‼️ The selected config directory is not a directory. Please select the directory where your transport config files are located. \nSelected path: ${configDirectory.path}.\n")
            return false
        }

        println("\n✔️ Config directory: ${configDirectory.path}\n")

        if (!prepareTransports())
        {
            return false
        }

        println("✔️ Check setup complete")
        return true
    }

    private fun prepareTransports(): Boolean
    {
        // Get a list of all of the files in the config directory
        // return false if we are unable to retrieve a list of files
        val configFiles = configDirectory.listFiles() ?: return false

        if (configFiles.isEmpty())
        {
            println("\n ‼️ There are no config files in the selected directory: ${configDirectory.path}")
            return false
        }

        configFiles.forEach { configFile ->
            for (transportType in possibleTransportTypes)
            {
                // Check each file name to see if it contains the name of a supported transport
                if (configFile.name.contains(transportType.name, true))
                {
                    val configString = configFile.readText()
                    val canaryConfig: CanaryConfig<ShadowConfig> = Json.decodeFromString(configString)

                    val maybeNewTransport = Transport(configFile.name, transportType, canaryConfig)
                    testingTransports += maybeNewTransport
                    println("\n✔️ ${maybeNewTransport.name} test is ready\n")
                }
            }
        }

        if (testingTransports.isEmpty())
        {
            println("‼️ There were no valid transport configs in the provided directory. Ending test.\nConfig Directory: $configDirectory.path")
            return false
        }

        return true
    }
}