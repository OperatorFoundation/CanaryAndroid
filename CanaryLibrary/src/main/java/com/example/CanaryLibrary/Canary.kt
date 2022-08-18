package org.OperatorFoundation.CanaryLibrary

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.File

class Canary(configDirectoryFile: File, timesToRun: Int = 1)
{
    private var chirp: CanaryTest

    init
    {
        chirp = CanaryTest(configDirectoryFile, timesToRun)
    }

    fun runTest()
    {
        // TODO: Better coroutines
        chirp.begin()
//        MainScope().launch {
//            chirp.begin()
//        }
    }
}