package canary.android

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import canary.android.utilities.shareResults
import java.io.File

class TestResultsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fileAdapter: TestResultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_results)

        //buttons
        val homeButton: Button = findViewById(R.id.ReturnHomeFromResults)
        val shareButton: Button = findViewById(R.id.ShareResults)
        val fileList: ArrayList<File> = getResultFiles()
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
            if (userSelectedResult != null)
            {
                try
                {
                    val resultURI = FileProvider.getUriForFile(applicationContext, "canary.android.fileprovider", userSelectedResult!!)
                    shareResults(this, resultURI)
                }
                catch (error: Exception)
                {
                   println("Error sharing the file ${userSelectedResult.toString()}. Error: $error")
                }
            }
        }
    }

    fun getResultFiles(): ArrayList<File>
    {
        val fileList = ArrayList<File>()
        val resultsFiles: Array<out File>? = filesDir.listFiles()

        if (resultsFiles != null)
        {
            for (thisFile in resultsFiles)
            {
                if (thisFile.name.contains(".csv"))
                {
                    fileList.add(thisFile)
                }
            }
        }
        else
        {
            println("No Result files were found in $filesDir")
        }

        return fileList
    }
}