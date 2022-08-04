package canary.android

import kotlinx.serialization.Serializable
import java.io.File

//variables for passing file locations in and out of views
var userSelectedConfig: String = "sample"
var userSubmittedFileName: String? = "sample"
var nameOfLinkedFile: String? = "sample"
var userSelectedResult: String? = "sample"
var numberTimesRunTest = 1
val userSelectedConfigList = arrayListOf<String>()

data class FileData(val filename:String){
    val selected = false
}

data class ResultsData(val filename:String){

}

@Serializable
class JsonConfig<out T: Any>(
    val password: String,
    val cipherName: String,
    val serverIP: String,
    val port: Int
    )

