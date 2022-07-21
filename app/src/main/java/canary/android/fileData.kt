package canary.android

//variables for passing file locations in and out of views
var userSelectedConfig: String? = null
var userSubmittedFileName: String? = null
var nameOfLinkedFile: String? = null
var userSelectedResult: String? = null

data class FileData(val filename:String){

}

data class ResultsData(val filename:String){

}

class JsonConfig(
    val password: String,
    val cipherName: String,
    val serverIP: String,
    val port: Int
)
