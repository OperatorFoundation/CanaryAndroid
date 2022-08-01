package canary.android.utilities

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity

fun Context.showAlert(message: String, length: Int = Toast.LENGTH_LONG)
{
    Toast.makeText(this, message, length).show()
}

fun shareResults(context: Context, shareThis: String){
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareThis)
        type = "text/plain" //Fixme format for the type of the results.
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}