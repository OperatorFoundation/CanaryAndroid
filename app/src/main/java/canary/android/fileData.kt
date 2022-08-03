package canary.android

import kotlinx.serialization.Serializable

//variables for passing file locations in and out of views
var userSelectedConfig: String? = "sample"
var userSubmittedFileName: String? = "sample"
var nameOfLinkedFile: String? = "sample"
var userSelectedResult: String? = "sample"
var numberTimesRunTest = 1


data class FileData(val filename:String){

}

data class ResultsData(val filename:String){

}

@Serializable
class JsonConfig(
    val password: String,
    val cipherName: String,
    val serverIP: String,
    val port: Int
    )

