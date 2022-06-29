/**
 * This activity displays the Canary Test app and allows the user to run connectivity
 * tests, displaying the results on the screen in a scrollable format
 */

package canary.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val popup = Toast.makeText(this, "HELLO", Toast.LENGTH_SHORT)
        val runTestButton: Button = findViewById(R.id.runButton)
        val browseButton: Button = findViewById(R.id.browseButton)

        var logs = "lorem ipsum blah bla blabber"

        when {
            intent?.action == Intent.ACTION_SEND -> {
                if ("text/json" == intent.type) {
                    val message = receiveTextFromExternalShare(intent) // Handle text being sent
                    Toast.makeText(this,message, Toast.LENGTH_SHORT).show()

                }
            }
        }

        runTestButton.setOnClickListener{
            popup.show()
            var testLogs: TextView = findViewById(R.id.logDisplayField)
            //Canary Library Functionality here. 
            testLogs.text = logs
        }

        browseButton.setOnClickListener {
            val browseIntent = Intent(this, FileBrowser::class.java)
            startActivity(browseIntent)
        }
    }
}

private fun receiveTextFromExternalShare(intent: Intent): String {
     intent.getStringExtra(Intent.EXTRA_TEXT)?.let{
         println(it)
         val message = it
         return message
     }
    return "nope"
}
