package canary.android

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import canary.android.utilities.showAlert


class FileBrowser() : AppCompatActivity() {
    var contentUri:Uri? = null
    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        contentUri = uri
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_browser)

//        when{ //presumably, we want the intent to show up here.
//            intent?.action == Intent.ACTION_SEND -> {
//                if ("application/json" == intent.type) {
//                    val message = receiveJsonFromExternalShare(intent)
//                    val configSettingsList = message.split(",")
//                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
//                }
//            }
//        }

        val homeButton: Button = findViewById(R.id.homeButton)
        homeButton.setOnClickListener {
            val homeIntent = Intent(this, MainActivity::class.java)
            startActivity(homeIntent)
        }

        val selectConfigButton: Button = findViewById(R.id.selectConfig)
        selectConfigButton.setOnClickListener {
            getContent.launch("application/json")

            if (contentUri.toString() == null){
                showAlert("get content does not seem to return anything")
            }
            showAlert(contentUri.toString())
        }
    }
}

private fun receiveJsonFromExternalShare(intent: Intent): String {


    return "mistakes were made"
}