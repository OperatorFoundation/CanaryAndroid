package canary.android

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import canary.android.utilities.getAppFolder
import java.io.File
import java.io.InputStream

class SaveNewConfig : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_new_config)

        //buttons
        val saveContinueButton: Button = findViewById(R.id.save_new_config_button)
        val cancelButton: Button = findViewById(R.id.cancel_new_config_button)

        //text views
        val configFileName: EditText = findViewById(R.id.filename_input)

        //variables
        var jsonInputStream: InputStream? = null

        when (intent?.action)
        {
            Intent.ACTION_SEND ->
            {
                if ("application/json" == intent.type)
                {
                    //set up the json to be saved, but then wait for the user to press save&continue
                    jsonInputStream = receiveJsonFromExternalShare(intent)
                }
            }
        }

        cancelButton.setOnClickListener {
            userSubmittedFileName = null
            val cancel = Intent(this, MainActivity::class.java)
            startActivity(cancel)
        }

        saveContinueButton.setOnClickListener {
            if (configFileName.text != null)
            {
                println("Config file name is: ${configFileName.text}")

//                configFileName =
                //get user input
                userSubmittedFileName = configFileName.text.toString()

                //save the file
                saveJson(jsonInputStream)

                //go home
                val saveJson = Intent(this, MainActivity::class.java)
                startActivity(saveJson)
            }
        }

    }

    fun receiveJsonFromExternalShare(intent: Intent): InputStream?
    {
        val json = intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM)

        if (json != null)
        {
            (json as? Uri)?.let { jsonUri ->
                val appContext = applicationContext
                val contentResolved = appContext.contentResolver ?: return null
                val streamer = contentResolved.openInputStream(jsonUri) ?: return null

                return streamer
            }
        }

        return null
    }

    fun saveJson(streamer: InputStream?): String?
    {
        val filename = "$userSubmittedFileName.json"
        if (streamer == null) {
            return null
        }

        val inputAsString = streamer.bufferedReader().use { it.readText() }
        val saveFile = File(getAppFolder(), filename)
        if (saveFile.exists()){
            saveFile.delete()
        }

        val createFileSuccess = saveFile.createNewFile()
        if (createFileSuccess){
            saveFile.appendText(inputAsString)
        } else {
            println("\n create file did not succeed \n")
        }

        return filename
    }
}