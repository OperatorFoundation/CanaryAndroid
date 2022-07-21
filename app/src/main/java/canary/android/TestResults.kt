package canary.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class TestResults : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_results)

        //buttons
        val homeButton: Button = findViewById(R.id.ReturnHomeFromResults)
        val shareButton: Button = findViewById(R.id.ShareResults)
        val browseResultsButton: Button = findViewById(R.id.BrowseOtherResults)

        //text views:
        val ResultsScreen: TextView = findViewById(R.id.ResultsView)

        homeButton.setOnClickListener {
            val homeIntent = Intent(this, MainActivity::class.java)
            startActivity(homeIntent)
        }

        shareButton.setOnClickListener{

        }

        browseResultsButton.setOnClickListener{
            val selectPreviousTestResultIntent = Intent( this, SelectAnotherTestResult::class.java )
            startActivity(selectPreviousTestResultIntent)

        }
    }
}