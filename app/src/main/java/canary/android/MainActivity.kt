/**
 * This activity displays the Canary Test app and allows the user to run connectivity
 * tests, displaying the results on the screen in a scrollable format
 */

package canary.android

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import canary.android.utilities.getAppFolder
import canary.android.utilities.showAlert
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.OperatorFoundation.CanaryLibrary.Canary
import org.operatorfoundation.shadowkotlin.ShadowConfig
import java.io.File
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //textViews
        val numberTestsLabel: TextView = findViewById(R.id.numberOfTestsDisplay)
        val configName: TextView = findViewById(R.id.SelectedConfigName)

        //buttons:
        val runTestButton: Button = findViewById(R.id.runButton)
        val browseButton: Button = findViewById(R.id.SelectConfigButton)
        val testMoreButton: Button = findViewById(R.id.testMore)
        val testLessButton: Button = findViewById(R.id.testLess)
        val showResultsButton: Button = findViewById(R.id.testResultsButton)

        //fill the config label or lock tests if no config selected.
        if(userSelectedConfigList.isEmpty())
        {
            configName.text = "please select a config"
        }
        else
        {
            configName.text = userSelectedConfig
        }

        runTestButton.setOnClickListener{
            //create temporary file for configs.
            val tempConfigFolder = makeTempFolder()
            if (userSelectedConfigList.size > 0)
            {
                thread(start = true)
                {
                    runTests(tempConfigFolder)
                    runOnUiThread {
                        val resultsIntent = Intent(this, TestResultsActivity::class.java)
                        startActivity(resultsIntent)
                    }
                }
            }
            else
            {
                showAlert("Please select at least one config to test.")
            }
        }

        browseButton.setOnClickListener {
            val browseIntent = Intent(this, ConfigFilesActivity::class.java)
            startActivity(browseIntent)
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

    // function to make or re-make and fill a temporary folder with selected configs
    private fun makeTempFolder(): File
    {
        val tempConfigFolder = File(getAppFolder(), "tempConfigFolder")
        if (tempConfigFolder.exists())
        {
            tempConfigFolder.deleteRecursively()
        }

        tempConfigFolder.mkdir()

        for (configName in userSelectedConfigList)
        {
            val configFile = File(getAppFolder(), configName)
            val newFilePrototype = File(tempConfigFolder,  configName)
            configFile.copyTo(newFilePrototype)
        }

        return tempConfigFolder
    }

    fun runTests(canaryConfigDirectory: File)
    {
        val canaryInstance = Canary(
            configDirectory = canaryConfigDirectory,
            timesToRun = numberTimesRunTest,
            saveDirectory = getAppFolder()
        )
        canaryInstance.runTest()
    }
}

