package canary.android.utilities

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity

fun Context.showAlert(message: String, length: Int = Toast.LENGTH_LONG)
{
    Toast.makeText(this, message, length).show()
}

fun shareResults(context: Context, testResults: String){
    try
    {
        val sendIntent = Intent(Intent.ACTION_SEND).apply{
            type = "text/csv"
            putExtra(Intent.EXTRA_TEXT, testResults)
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }
    catch (exception: Exception)
    {
        context.showAlert("Share results failed. Error: $exception")
        return
    }
}