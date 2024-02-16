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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import org.OperatorFoundation.CanaryLibrary.Canary
import java.io.File
import java.io.InputStreamReader
import java.time.Duration
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity()
{
    lateinit var selectedDirectory: TextView
    val CONFIG_DIRECTORY_REQUEST = 9756
    var canary: Canary? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //textViews
        val numberTestsLabel: TextView = findViewById(R.id.numberOfTestsDisplay)
        selectedDirectory = findViewById(R.id.SelectedDirectoryName)

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
        val getConfigDirectoryIntent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        startActivityForResult(getConfigDirectoryIntent, CONFIG_DIRECTORY_REQUEST)
    }

    fun loadTransportConfigs(context: Context, directory: Uri)
    {
        val directoryDocumentFile = DocumentFile.fromTreeUri(context, directory)
        if (directoryDocumentFile == null)
        {
            runOnUiThread {
                Toast.makeText(this, "directoryDocumentFile was null.", Toast.LENGTH_SHORT).show()
            }
            return
        }

        selectedDirectory.text = directoryDocumentFile.name

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
            runOnUiThread {
                Toast.makeText(this.applicationContext, "Cannot run tests, config files not loaded.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CONFIG_DIRECTORY_REQUEST && resultCode == Activity.RESULT_OK)
        {
            data?.data?.also { uri ->
                loadTransportConfigs(this, uri)
            }
        }
    }
}

