package canary.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import canary.android.utilities.showAlert
import org.w3c.dom.Text

class saveNewConfig : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_new_config)
        //buttons
        val saveContinueButton: Button = findViewById(R.id.save_new_config_button)
        val cancelButton: Button = findViewById(R.id.cancel_new_config_button)
        //text views
        //user input field
        val configFileName: EditText = findViewById(R.id.new_config_file_name)

        when{
            intent?.action == Intent.ACTION_SEND -> {
                if ("application/json" == intent.type) {
                    //do work of importing Json
                    //val message = receiveJsonFromExternalShare(intent)
                }
            }
        }

        if (nameOfLinkedFile != null){
            val fileNameList = applicationContext.getFilesDir().list()
            if (fileNameList.contains(nameOfLinkedFile)){
                var count = 2
                var newName = nameOfLinkedFile + count.toString()
                while(!(fileNameList.contains(newName))){
                    count += 1
                    newName = nameOfLinkedFile + count.toString()
                }
                configFileName.setText(newName)
            }
        }


        cancelButton.setOnClickListener(){
            userSubmittedFileName = null
            val homeEmptyHandedIntent = Intent(this, MainActivity::class.java)
            startActivity(homeEmptyHandedIntent)

        }
        saveContinueButton.setOnClickListener(){
            if (configFileName.text.toString() != null){
                userSubmittedFileName = configFileName.getText().toString()

            }
        }

    }

    fun suggestFileName(){

    }
}