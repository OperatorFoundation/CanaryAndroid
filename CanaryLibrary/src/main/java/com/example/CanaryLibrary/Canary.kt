package org.OperatorFoundation.CanaryLibrary

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.File

class Canary(configDirectoryFile: File, timesToRun: Int = 1, saveDirectory: File)
{
    private var chirp: CanaryTest

    init
    {
        chirp = CanaryTest(configDirectoryFile, timesToRun, saveDirectory)
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