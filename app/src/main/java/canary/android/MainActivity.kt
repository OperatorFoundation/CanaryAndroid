/**
 * This activity displays the Canary Test app and allows the user to run connectivity
 * tests, displaying the results on the screen in a scrollable format
 */

package canary.android

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

        var logs = "lorem ipsum blah bla blabber"

        runTestButton.setOnClickListener{
            popup.show()
            var testLogs: TextView = findViewById(R.id.logDisplayField)
            //populate logs here
            testLogs.text = logs
        }
        val browseButton: Button = findViewById(R.id.browseButton)
        browseButton.setOnClickListener {
            popup.show()
        }
    }
}