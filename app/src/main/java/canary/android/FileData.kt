package canary.android

//variables for passing file locations in and out of views
var userSelectedConfig: String = "sample"
var userSubmittedFileName: String? = "sample"
var nameOfLinkedFile: String? = "sample"
var userSelectedResult: String = ""
var numberTimesRunTest = 1
val userSelectedConfigList = arrayListOf<String>()

data class ConfigFileData(val filename:String){
    val selected = false
}

data class ResultsData(val filename:String){

}

