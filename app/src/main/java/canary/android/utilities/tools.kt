package canary.android.utilities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import java.net.URI

fun Context.showAlert(message: String, length: Int = Toast.LENGTH_LONG)
{
    Toast.makeText(this, message, length).show()
}

fun shareResults(context: Context, resultsFile: Uri){
    try
    {
        val sendIntent = Intent(Intent.ACTION_SEND).apply{
            type = "text/csv"
            putExtra(Intent.EXTRA_STREAM, resultsFile)
        }

        val shareIntent = Intent.createChooser(sendIntent, "Share Results")
        context.startActivity(shareIntent)
    }
    catch (exception: Exception)
    {
        context.showAlert("Share results failed. Error: $exception")
        return
    }
}