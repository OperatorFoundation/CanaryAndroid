package canary.android

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import canary.android.utilities.showAlert
import kotlin.coroutines.suspendCoroutine


class FileBrowser() : AppCompatActivity() {
    var contentUri:Uri? = null
    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        contentUri = uri
    }

    private fun getConfig(context: Context):Uri? {
        var contentUri:Uri? = null
        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            contentUri = uri
        }
        return contentUri
    }

    private suspend fun getConfigSuspend(activity: MainActivity) {
        val uri = getConfig(applicationContext)
        contentUri = uri
        getContent.launch("application/json")
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_browser)

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