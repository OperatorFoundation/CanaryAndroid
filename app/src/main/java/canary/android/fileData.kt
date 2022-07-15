package canary.android


var userSelectedConfig: String? = null
var userSubmittedFileName: String? = null
var nameOfLinkedFile: String? = null

data class FileData(val filename:String){

}

class JsonConfig(
    val password: String,
    val cipherName: String,
    val serverIP: String,
    val port: Int
)
