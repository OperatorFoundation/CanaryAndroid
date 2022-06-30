package canary.android

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class FileBrowser() : AppCompatActivity() {
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
    }
}

private fun receiveJsonFromExternalShare(intent: Intent): String {


    return "mistakes were made"
}