/**
 * This activity displays the Canary Test app and allows the user to run connectivity
 * tests, displaying the results on the screen in a scrollable format
 */

package canary.android

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import canary.android.utilities.getAppFolder
import canary.android.utilities.showAlert
import org.OperatorFoundation.CanaryLibrary.Canary
import java.io.BufferedInputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity()
{
    lateinit var logTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //textViews
        logTextView = findViewById(R.id.logDisplayField)
        logTextView.movementMethod = ScrollingMovementMethod()
        val numberTestsLabel: TextView = findViewById(R.id.numberOfTestsDisplay)
        val configName: TextView = findViewById(R.id.SelectedConfigName)

        //Image Views
        val internetTest: ImageView = findViewById(R.id.imageView)

        //buttons:
        val runTestButton: Button = findViewById(R.id.runButton)
        val browseButton: Button = findViewById(R.id.SelectConfigButton)
        val testMoreButton: Button = findViewById(R.id.testMore)
        val testLessButton: Button = findViewById(R.id.testLess)
        val sampleConfigButton: Button = findViewById(R.id.SampleConfigButton)
        val showResultsButton: Button = findViewById(R.id.testResultsButton)

        //variables and things
        var internetTestImage: Bitmap?
        val mWebPath = "https://media.npr.org/assets/img/2022/07/12/southern-ring-nebula-2-_custom-60c7d16d9c36f085646be2dad4585892c783952d-s2600-c85.webp"

        //fill the config label or lock tests if no config selected.
        if(userSelectedConfigList.count() == 0){
            configName.text = "please select a config"
        } else {
            //parse the names from the config-list and make them pretty for the user
            var prettyConfigString = ""
            for (names in userSelectedConfigList){
                prettyConfigString += names.replace(".json", ", ")
            }
            configName.text = prettyConfigString
        }

        runTestButton.setOnClickListener{
            //create temporary file for configs.
            val tempConfigFolder =  makeTempFolder()
            logTextView.text = "performing tests..."

            //thread the internet connection so app doesn't stop it.
            thread(start = true) {
                //internetTest.setImageBitmap(internetTestImage)
                runTests(tempConfigFolder)
            }
            if (userSelectedConfigList.size > 0) {
                val resultsIntent = Intent(this, TestResults::class.java)
                startActivity(resultsIntent)
            } else {
                showAlert("Please select at least one Config to test.")
            }
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
        }

        sampleConfigButton.setOnClickListener {
            saveSampleConfigToFile()
        }

        showResultsButton.setOnClickListener {
            val resultsIntent = Intent(this, TestResults::class.java)
            startActivity(resultsIntent)
        }
    }

    // Function to establish connection and load image
    private fun mLoad(string: String): Bitmap? {
        val url: URL = mStringToURL(string)!!
        val connection: HttpURLConnection?
        try {
            connection = url.openConnection() as HttpURLConnection
            connection.connect()
            val inputStream: InputStream = connection.inputStream
            val bufferedInputStream = BufferedInputStream(inputStream)
            return BitmapFactory.decodeStream(bufferedInputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
        }
        return null
    }

    // Function to convert string to URL
    private fun mStringToURL(string: String): URL? {
        try {
            return URL(string)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return null
    }

    //function to make or re-make and fill a temporary folder with selected configs
    private fun makeTempFolder(): File{
        val tempConfigFolder = File(getAppFolder(), "tempConfigFolder")
        if (tempConfigFolder.exists()){
            tempConfigFolder.deleteRecursively()
        }
        tempConfigFolder.mkdir()
        for (configName in userSelectedConfigList){
            val configFile = File(getAppFolder(),configName)

            val newFilePrototype = File(tempConfigFolder,  configName)
            configFile.copyTo(newFilePrototype)
        }
        return tempConfigFolder
    }

    fun runTests(canaryConfigDirectory: File) {
        val canaryInstance = Canary(
            configDirectoryFile = canaryConfigDirectory,
            timesToRun = numberTimesRunTest
        )
        canaryInstance.runTest()
    }


    fun saveSampleConfigToFile()
    {
        // TODO: Remove IP and Password from sample config before pushing any changes.
        val jsonString = """{"serverIP":"0.0.0.0","serverPort":5678,"transportConfig":{"password":"password","cipherName":"DarkStar","cipherMode":"DarkStar"}}
            """
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED)
        {
            println("Unable to save the config file: external storage is not available for reading/writing")
        }
        val filename = "SampleCanaryShadowConfig.json"
        val configDir = getAppFolder()
        val saveFile = File(configDir, "canaryShadowConfig.json")

        println("starting the try in sample config")
        try
        {
            if (saveFile.exists())
            {
                saveFile.delete()
            }

            // Make a file for our sample config
            saveFile.createNewFile()
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
                showAlert("A sample Canary config has been saved to your phone.")
            }
            else
            {
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

