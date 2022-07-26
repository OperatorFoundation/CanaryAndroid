package com.example.canarylibrary

import java.net.URL
import kotlin.io.path.Path
import kotlin.io.path.exists

class CanaryTest(configDirectoryURL: URL, timesToRun: Int = 1, debugPrints: Boolean = false, runWebTests: Boolean = false)
{
//    var canaryTestQueue = DispatchQueue("CanaryTests")
    var savePath: String? = null
    var testCount: Int = 1
    var userInterfaceName: String? = null

    fun begin()
    {
        println("\n attempting to run tests...\n")

        // Make sure we have everything we need first
        if (checkSetup() == null)
        {
            return
        }

        var interfaceName: String

        if (userInterfaceName != null)
        {
            // Use the user provided interface name
            interfaceName = userInterfaceName!!
            println("Running tests using the user selected interface $interfaceName")
        }
        else
        {
            // Try to guess the interface, if we cannot then give up
            interfaceName = guessUserInterface() ?: return

            println("\nWe will try using the $interfaceName interface. " +
                    "If Canary fails to capture data, it may be because this is not the correct interface. " +
                    "Please try running the program again using the interface flag and one of the other listed interfaces.\n")
        }
    }

    fun guessUserInterface(): String?
    {

        return ""
    }

    fun checkSetup(): Boolean
    {
        if (savePath != null)
        {
            saveDirectoryPath = savePath!!

            // Does the save directory exist?
            // TODO: Access the File Manager to check if directory exists at specific path.
            var actualPath = Path(saveDirectoryPath)
            var saveDirectoryExists = actualPath.exists()
            if (!saveDirectoryExists)
            {
                println("Save Directory doesn't exist at: $actualPath")
                return false
            }
        }

        if (prepareTransports() == null)
        {
            return false
        }

        return true
    }

    fun prepareTransports(): Boolean
    {
        // TODO: Add Android equivalent of a security-scoped resource..

        // TODO: Check if file exists

        // var error:
        // TODO: FileCoordinator

        return true
    }
}