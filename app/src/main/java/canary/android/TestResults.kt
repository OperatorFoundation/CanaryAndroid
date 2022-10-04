package canary.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import canary.android.utilities.getAppFolder
import canary.android.utilities.shareResults
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class TestResults : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_results)

        //buttons
        val homeButton: Button = findViewById(R.id.ReturnHomeFromResults)
        val shareButton: Button = findViewById(R.id.ShareResults)
        val browseResultsButton: Button = findViewById(R.id.BrowseOtherResults)

        //text views:
        val resultsTextView: TextView = findViewById(R.id.ResultsView)
        val resultFileNames = getResultFileNames()
        var resultString = ""

        println("** Attempting to print result file names **")

        resultFileNames.forEach { name ->
            println(name)
            resultString += "$name "
        }

        resultsTextView.text = resultString

        homeButton.setOnClickListener {
            val homeIntent = Intent(this, MainActivity::class.java)
            startActivity(homeIntent)
        }

        shareButton.setOnClickListener{
            shareResults(this, "FIXME, I need results") //FIXME, put results here
        }

        browseResultsButton.setOnClickListener{
            val selectPreviousTestResultIntent = Intent( this, SelectAnotherTestResult::class.java )
            startActivity(selectPreviousTestResultIntent)
        }
    }

    fun getResultFileNames(): ArrayList<String>
    {
        val fileList = ArrayList<String>()
        val appFolder = getAppFolder()
        val fileNameList = appFolder.list()
        val currentDate = Calendar.getInstance().time
        println("** Checking App folder for files: $appFolder **")
        for (item in fileNameList) {
            if (item.contains(".csv")){
                fileList.add("$item - $currentDate")
            }
        }

        return fileList
    }
}