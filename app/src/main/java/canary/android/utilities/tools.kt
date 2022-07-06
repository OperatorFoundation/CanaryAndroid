package canary.android.utilities

import android.content.Context
import android.widget.Toast

fun Context.showAlert(message: String, length: Int = Toast.LENGTH_LONG)
{
    Toast.makeText(this, message, length).show()
}