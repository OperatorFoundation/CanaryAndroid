package canary.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import canary.android.utilities.getAppFolder
import canary.android.utilities.shareResults
import canary.android.utilities.showAlert
import java.io.File
import kotlin.collections.ArrayList

class TestResultsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fileList: ArrayList<ResultsData>
    private lateinit var fileAdapter: TestResultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_results)

        //buttons
        val homeButton: Button = findViewById(R.id.ReturnHomeFromResults)
        val shareButton: Button = findViewById(R.id.ShareResults)
        val viewResultsButton: Button = findViewById(R.id.ViewResults)

        fileList = getResultFileNames()
        recyclerView = findViewById(R.id.resultsRecyclerView)
        fileAdapter = TestResultsAdapter(fileList)
        recyclerView.adapter = fileAdapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        homeButton.setOnClickListener {
            val homeIntent = Intent(this, MainActivity::class.java)
            startActivity(homeIntent)
        }

        shareButton.setOnClickListener{
            if (userSelectedResult.isNotEmpty())
            {
                // TODO: Add try/catch block
                val resultFile = File(getAppFolder(), userSelectedResult)
                if (resultFile.exists())
                {
                    // TODO: Find out how to get the entire contents of file.
                    val resultString = resultFile.readText()
                    shareResults(this, resultString)
                }
            }


        }

        viewResultsButton.setOnClickListener{
            val resultsViewerIntent = Intent(this, ResultsViewer::class.java)
            startActivity(resultsViewerIntent)
        }
    }

    fun getResultFileNames(): ArrayList<ResultsData>
    {
        val fileList = ArrayList<ResultsData>()
        val appFolder = getAppFolder()
        val fileNameList = appFolder.list()
        println("** Checking App folder for files: $appFolder **")
        for (item in fileNameList) {
            if (item.contains(".csv")){
                val thisResult = ResultsData(item)
                fileList.add(thisResult)
            }
        }

        return fileList
    }
}