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
        val viewResultsButton: Button = findViewById(R.id.ViewResults)
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
                // TODO: Add try/catch block
                val resultURI = FileProvider.getUriForFile(applicationContext, "canary.android.fileprovider", userSelectedResult!!)
                println("Result URI is: ${resultURI.path}")
                shareResults(this, resultURI)
            }


        }

        viewResultsButton.setOnClickListener{
            openFile()
        }
    }

    private fun openFile()
    {
        if (userSelectedResult != null)
        {
            val packageName = applicationContext.packageName
            val resultURI = FileProvider.getUriForFile(applicationContext, "$packageName.fileprovider", userSelectedResult!!)

            val viewIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            {
                Intent(Intent.ACTION_OPEN_DOCUMENT)
            }
            else
            {
                TODO("VERSION.SDK_INT < KITKAT")
            }

            viewIntent.setDataAndType(resultURI, "text/csv")
            viewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            viewIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(viewIntent)
        }
    }

    fun getResultFiles(): ArrayList<File>
    {
        val fileList = ArrayList<File>()
        val resultsFiles: Array<out File>? = filesDir.listFiles()

        if (resultsFiles != null)
        {
            println("&&& Printing Results Files &&&")

            for (thisFile in resultsFiles)
            {
                println(thisFile.name)
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