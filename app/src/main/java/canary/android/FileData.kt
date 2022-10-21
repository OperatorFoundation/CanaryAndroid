package canary.android

import java.io.File

//variables for passing file locations in and out of views
var userSelectedConfig: String = "sample"
var userSubmittedFileName: String? = "sample"
var nameOfLinkedFile: String? = "sample"
var userSelectedResult: File? = null
var numberTimesRunTest = 1
val userSelectedConfigList = arrayListOf<String>()

data class ConfigFileData(val filename:String)

