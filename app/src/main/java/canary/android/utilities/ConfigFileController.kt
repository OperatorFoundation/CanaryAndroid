package canary.android.utilities

import android.content.Context
import java.io.File

class ConfigFileController {
   //companion object{}
   //
   //}
}

fun Context.getAppFolder(): File{
    return this.filesDir
}

