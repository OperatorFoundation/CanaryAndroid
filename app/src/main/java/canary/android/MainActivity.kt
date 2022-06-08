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

        runTestButton.setOnClickListener{
            popup.show()
            var testLogs: TextView = findViewById(R.id.logDisplayField)
            testLogs.text = "lorem ipsum blah bla blabber"
        }
        val browseButton: Button = findViewById(R.id.browseButton)
        browseButton.setOnClickListener {
            popup.show()
        }
    }
}