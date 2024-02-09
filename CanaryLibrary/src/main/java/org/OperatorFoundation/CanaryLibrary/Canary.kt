package org.OperatorFoundation.CanaryLibrary

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import kotlinx.serialization.json.Json
import org.operatorfoundation.shadow.ShadowConfig
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

class Canary(configDirectory: DocumentFile, timesToRun: Int = 1, saveDirectory: File, context: Context)
{
    private var chirp: CanaryTest

    init
    {
        chirp = CanaryTest(configDirectory, timesToRun, saveDirectory, context)
    }

    fun runTest()
    {
        chirp.begin()
    }
}