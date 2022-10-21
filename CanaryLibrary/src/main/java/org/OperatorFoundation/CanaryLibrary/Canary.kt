package org.OperatorFoundation.CanaryLibrary

import java.io.File

class Canary(configDirectory: File, timesToRun: Int = 1, saveDirectory: File)
{
    private var chirp: CanaryTest

    init
    {
        chirp = CanaryTest(configDirectory, timesToRun, saveDirectory)
    }

    fun runTest()
    {
        chirp.begin()
    }
}