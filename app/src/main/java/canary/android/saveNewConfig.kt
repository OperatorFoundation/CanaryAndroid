package canary.android

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
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
        //user input field
        val configFileName: EditText = findViewById(R.id.filename_input)

        //variables
        var jsonInputStream: InputStream? = null

        when (intent?.action)
        {
            Intent.ACTION_SEND ->
            {
                if ("application/json" == intent.type)
                {
                    //read file name and set that as hint.
                    //val uri = intent.getParcelableExtra<Parcelable>(Intent.EXTRA_COMPONENT_NAME)
                    nameOfLinkedFile = intent.dataString

                    if (nameOfLinkedFile != null)
                    {
                        val fileNameList = getAppFolder().list()

                        if (fileNameList != null)
                        {
                            if (fileNameList.contains(nameOfLinkedFile))
                            {
                                var count = 2
                                var newName = nameOfLinkedFile + count.toString()

                                while(!(fileNameList.contains(newName)))
                                {
                                    count += 1
                                    newName = nameOfLinkedFile + count.toString()
                                }

                                configFileName.setText(newName)
                                configFileName.hint = newName
                            }
                        }
                    }

                    //set up the json to be saved, but then wait for the user to press save&continue
                    jsonInputStream = receiveJsonFromExternalShare(intent)
                }
            }
        }

        cancelButton.setOnClickListener {
            userSubmittedFileName = null
            val homeEmptyHandedIntent = Intent(this, MainActivity::class.java)
            startActivity(homeEmptyHandedIntent)
        }

        saveContinueButton.setOnClickListener {
            if (configFileName.text != null)
            {
                //get user input
                userSubmittedFileName = configFileName.text.toString()

                //save the file
                saveJson(jsonInputStream)

                //go home
                val homeHappilyIntent = Intent(this, MainActivity::class.java)
                startActivity(homeHappilyIntent)
            }
        }

    }

    fun receiveJsonFromExternalShare(intent: Intent): InputStream?
    {
        val json = intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM)

        if (json != null)
        {
            (json as? Uri)?.let { jsonUri ->
                println(jsonUri)
                val appContext = applicationContext
                val contentResolved = appContext.contentResolver ?: return null
                val streamer = contentResolved.openInputStream(jsonUri) ?: return null

                var bufferedReader = streamer.bufferedReader()

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