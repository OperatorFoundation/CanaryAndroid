/**
 * This activity displays the Canary Test app and allows the user to run connectivity
 * tests, displaying the results on the screen in a scrollable format
 */

package canary.android

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import org.OperatorFoundation.CanaryLibrary.Canary
import java.io.File
import java.io.InputStreamReader
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity()
{
    val CONFIG_DIRECTORY_REQUEST = 9756
    var canary: Canary? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //textViews
        val numberTestsLabel: TextView = findViewById(R.id.numberOfTestsDisplay)
        val configName: TextView = findViewById(R.id.SelectedDirectoryName)

        //buttons:
        val runTestButton: Button = findViewById(R.id.runButton)
        val browseButton: Button = findViewById(R.id.SelectConfigButton)
        val testMoreButton: Button = findViewById(R.id.testMore)
        val testLessButton: Button = findViewById(R.id.testLess)
        val showResultsButton: Button = findViewById(R.id.testResultsButton)

        browseButton.setOnClickListener {
            getConfigDirectory()
        }

        runTestButton.setOnClickListener{
            thread(start = true)
            {
                runTests()
                runOnUiThread {
                    val resultsIntent = Intent(this, TestResultsActivity::class.java)
                    startActivity(resultsIntent)
                }
            }
        }

        testMoreButton.setOnClickListener{
            numberTimesRunTest += 1
            numberTestsLabel.text = "$numberTimesRunTest times"
        }

        testLessButton.setOnClickListener{
            if(numberTimesRunTest != 1)
            {
                numberTimesRunTest -= 1
            }

            numberTestsLabel.text = "$numberTimesRunTest times"
        }

        showResultsButton.setOnClickListener {
            val resultsIntent = Intent(this, TestResultsActivity::class.java)
            startActivity(resultsIntent)
        }
    }

    private fun getConfigDirectory()
    {
        println("getConfigDirectory tapped.")

        val getConfigDirectoryIntent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        startActivityForResult(getConfigDirectoryIntent, CONFIG_DIRECTORY_REQUEST)
    }

    fun loadTransportConfigs(context: Context, directory: Uri)
    {
        println("Config directory: $directory")
        val directoryDocumentFile = DocumentFile.fromTreeUri(context, directory)
        if (directoryDocumentFile == null)
        {
            println("directoryDocumentFile was null.")
            return
        }

        canary = Canary(directoryDocumentFile, numberTimesRunTest, this.filesDir, this)
    }

    fun runTests()
    {
        if (canary != null)
        {
            canary?.runTest()
        }
        else
        {
            println("Cannot run tests, config files not loaded.")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CONFIG_DIRECTORY_REQUEST && resultCode == Activity.RESULT_OK)
        {
            // The result data contains a URI for the document or directory that
            // the user selected.
            data?.data?.also { uri ->
                // Perform operations on the document using its URI.
                loadTransportConfigs(this, uri)
            }
        }
    }
}

