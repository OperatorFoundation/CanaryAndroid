package com.example.canarylibrary

import java.net.InterfaceAddress
import java.net.URL

class CanaryTest(configDirectoryURL: URL, timesToRun: Int = 1, debugPrints: Boolean = false, runWebTests: Boolean = false)
{
//    var canaryTestQueue = DispatchQueue("CanaryTests")
    var savePath: String? = null
    var testCount: Int = 1
//    var userInterface: String? = null

    fun begin()
    {
        println("\n attempting to run tests...\n")

        // Make sure we have everything we need first
        if (checkSetup() == null)
        {
            return
        }

//        var interfaceName: String
//
//        if (userInterface != null)
//        {
//            // Use the user provided interface name
//            interfaceName = userInterface!!
//            println("Running tests using the user selected interface $interfaceName")
//        }
//        else
//        {
//            // Try to guess the interface, if we cannot then give up
//            interfaceName = guessUserInterface() ?: return
//
//            println("\nWe will try using the $interfaceName interface. " +
//                    "If Canary fails to capture data, it may be because this is not the correct interface. " +
//                    "Please try running the program again using the interface flag and one of the other listed interfaces.\n")
//        }
    }

//    fun guessUserInterface(): String?
//    {
//        //
//    }

    fun checkSetup(): Boolean
    {

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