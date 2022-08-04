/**
 * This activity displays the Canary Test app and allows the user to run connectivity
 * tests, displaying the results on the screen in a scrollable format
 */

package canary.android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import canary.android.utilities.showAlert
import org.OperatorFoundation.CanaryLibrary.Canary
import java.io.File
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity()
{
    lateinit var logTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //textViews
        logTextView = findViewById(R.id.logDisplayField)
        val numberTestsLabel: TextView = findViewById(R.id.numberOfTestsDisplay)
        val configName: TextView = findViewById(R.id.SelectedConfigName)

        //buttons:
        val runTestButton: Button = findViewById(R.id.runButton)
        val browseButton: Button = findViewById(R.id.SelectConfigButton)
        val testMoreButton: Button = findViewById(R.id.testMore)
        val testLessButton: Button = findViewById(R.id.testLess)
        val sampleConfigButton: Button = findViewById(R.id.SampleConfigButton)
        val showResultsButton: Button = findViewById(R.id.testResultsButton)

        //variables and things

        //fill the config label or lock tests if no config selected.
        if(userSelectedConfig == null){
            configName.text = "please select a config"
        } else {
            configName.text = userSelectedConfig
            //fill our json object from the selected thing.
        }

        runTestButton.setOnClickListener{
            runTests()
        }

        browseButton.setOnClickListener {
            val browseIntent = Intent(this, FileBrowser::class.java)
            startActivity(browseIntent)
        }

        testMoreButton.setOnClickListener{
            numberTimesRunTest += 1
            numberTestsLabel.text = numberTimesRunTest.toString()+" times"
        }

        testLessButton.setOnClickListener{
            if(numberTimesRunTest != 1){
                numberTimesRunTest -= 1
            }

            numberTestsLabel.text = numberTimesRunTest.toString()+" times"
            showAlert(numberTimesRunTest.toString())
        }

        sampleConfigButton.setOnClickListener {
            saveSampleConfigToFile()
        }

        showResultsButton.setOnClickListener{}
        val resultsIntent = Intent(this, TestResults::class.java)
    }

    fun runTests()
    {
        logTextView.text = "performing tests..."

        //Canary Library Functionality here.
        val canaryInstance = Canary(configDirectoryFile =applicationContext.getFilesDir() , timesToRun = numberTimesRunTest)
        //canary.runTest()
        val resultsIntent = Intent(this,TestResults::class.java )
        startActivity(resultsIntent)
    }

    fun saveSampleConfigToFile()
    {
        // TODO: Request permissions

        val jsonString = """{"password": "password", "cipherName": "DarkStar", 
                    "serverIP": "0.0.0.0", "port": 1234}
                    """
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED)
        {
            println("Unable to save the config file: external storage is not available for reading/writing")
        }
        val filename = "canaryShadowConfig.json"
        val extDir = applicationContext.getExternalFilesDir(null) //Environment.DIRECTORY_DOCUMENTS
        val saveFile = File(extDir, "canaryShadowConfig.json")
        if (extDir != null){
            println("starting the try in sample config")
            try
            {
                // Make sure the Documents directory exists.
                extDir.mkdirs()
                println("made dir")

                if (saveFile.exists())
                {
                    println("deleted safeFile")
                    saveFile.delete()
                }

                // Make a new csv file for our test results
                saveFile.createNewFile()

                // The first row should be our labels

                //write sample config to file
                applicationContext.openFileOutput(filename, Context.MODE_PRIVATE).use { file ->
                    for(char in jsonString){
                        try {
                            val b = char.code
                            file.write(b)
                        } catch (e: IOException) {
                            file.close()
                            break
                        }
                    }
                }

                if (saveFile.exists())
                {
                    println("confirmed that saveFile exists")
                    showAlert("A sample Canary config has been saved to your phone.")
                }
                else
                {
                    println("did not find the saveFile")
                    showAlert("We were unable to save a sample Canary config to your phone.")
                }
            }
            catch (error: Exception)
            {
                showAlert("We were unable to save a sample Canary config. Error ${error.message}")
                error.printStackTrace()
            }
        }
    }
}

