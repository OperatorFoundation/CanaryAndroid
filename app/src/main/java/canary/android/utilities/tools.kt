package canary.android.utilities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

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
            setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        val shareIntent = Intent.createChooser(sendIntent, "Share Results")
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(shareIntent)
    }
    catch (exception: Exception)
    {
        context.showAlert("Share results failed. Error: $exception")
        return
    }
}