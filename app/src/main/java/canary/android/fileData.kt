package canary.android

data class FileData(val filename:String){

}

var userSelectedConfig: String? = null

class JsonConfig(
    val password: String,
    val cipherName: String,
    val serverIP: String,
    val port: Int
)
