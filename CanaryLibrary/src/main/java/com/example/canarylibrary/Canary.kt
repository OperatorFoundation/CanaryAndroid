package com.example.canarylibrary

import java.net.URL

class Canary(configDirectoryURL: URL, timesToRun: Int = 1, debugPrints: Boolean = false, runWebTests: Boolean = false)
{
    private var chirp: CanaryTest

    init
    {
        chirp = CanaryTest(configDirectoryURL, timesToRun, debugPrints, runWebTests)
    }

    fun runTest()
    {
        chirp.begin()
    }
}