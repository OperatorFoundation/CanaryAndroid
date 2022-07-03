/**
 * This activity displays the Canary Test app and allows the user to run connectivity
 * tests, displaying the results on the screen in a scrollable format
 */

package canary.android

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.opengl.ETC1.decodeImage
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.room.*
import com.beust.klaxon.Klaxon
import java.io.File
import java.io.IOException
import java.util.*

@Entity(tableName = "SavedConfigs")
data class Config(
    @PrimaryKey(autoGenerate = true)
    var configId: Long = 0L,
    @ColumnInfo(name = "configLabel")
    var configLabel: String,
    @ColumnInfo(name = "password")
    var password: String,
    @ColumnInfo(name = "cipherName")
    var cipherName: String,
    @ColumnInfo(name = "serverIP")
    var serverIP: String,
    @ColumnInfo(name = "port")
    var port: Int
)

class JsonConfig(
    val password: String,
    val cipherName: String,
    val serverIP: String,
    val port: Int
)


@Dao
interface ConfigDatabaseDao {
    @Insert
    suspend fun insert(config: Config)
    @Update
    suspend fun update(config: Config)
    @Query("SELECT * from SavedConfigs WHERE configId = :key")
    suspend fun get(key: Long): Config?
    @Query("DELETE FROM SavedConfigs")
    suspend fun clear()
    @Query("SELECT * FROM SavedConfigs ORDER BY configId DESC LIMIT 1")
    suspend fun getTopConfig(): Config?
    @Query("SELECT * FROM SavedConfigs ORDER BY configId DESC")
    fun getAllConfigs(): LiveData<List<Config>>
}

@Database(entities = [Config::class], version = 1, exportSchema = false)
abstract class CanaryConfigDatabase : RoomDatabase() {
    abstract val configDatabaseDao: ConfigDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE: CanaryConfigDatabase? = null
        fun getInstance(context: Context): CanaryConfigDatabase{
            synchronized(this){ //makes sure the database isnt built more than once by different threads
                var instance = INSTANCE //handle for our current instance of what?
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CanaryConfigDatabase::class.java,
                        "Config Database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }


    }
}


class MainActivity : AppCompatActivity() {

    //should give us a text input popup, cannae get it to proc
    fun showConfigLabelPopup(): String {
        val builder: AlertDialog.Builder = androidx.appcompat.app.AlertDialog.Builder(this)
        val input = EditText(this)
        var configLabel = "cancel"

        input.setHint("what do you want to call this config?")
        builder.setView(input)
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            // Here you get get input text from the Edittext
            configLabel = input.text.toString()
        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        builder.show()
        return configLabel
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val popup = Toast.makeText(this, "HELLO", Toast.LENGTH_SHORT)
        val runTestButton: Button = findViewById(R.id.runButton)
        val browseButton: Button = findViewById(R.id.browseButton)

        var logs = "lorem ipsum blah bla blabber"

        when{
            intent?.action == Intent.ACTION_SEND -> {
                if ("text/plain" == intent.type) {
                    val message = receiveTextFromExternalShare(intent)
                    Toast.makeText(this,"got message from text/plain", Toast.LENGTH_SHORT).show()

                } else if ("application/json" == intent.type){
                    val message = receiveJsonFromExternalShare(intent)
                    if (message == null){
                        return
                    }
                    val parsedJson = Klaxon()
                        .parse<JsonConfig>(message)
                    if (parsedJson == null) {
                        return
                    }


                    Toast.makeText(this, parsedJson.serverIP, Toast.LENGTH_LONG).show()
                    //val configLabel = showConfigLabelPopup()

                    val configObject = Config(
                        1,
                        "configtest1",
                        parsedJson.password,
                        parsedJson.cipherName,
                        parsedJson.serverIP,
                        parsedJson.port
                    )


                    val db = Room.databaseBuilder(
                        applicationContext,
                        CanaryConfigDatabase::class.java, "ConfigDatabase"
                    ).build()


                    //ConfigDatabaseDao.insert(configObject)


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

    fun receiveJsonFromExternalShare(intent: Intent): String? {
        val json = intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM)
        if (json != null)
        {
            (json as? Uri)?.let {jsonUri ->
                println(jsonUri)
                val appContext = applicationContext
                val contentResolved = appContext.contentResolver
                if (contentResolved == null){
                    return null
                }
                var streamer = contentResolved.openInputStream(jsonUri)
                if (streamer == null){
                    return null
                }
                var bufferedReader = streamer.bufferedReader()
                val jsonString = bufferedReader.use{ it.readText() }
                val filename = UUID.randomUUID().toString()
                streamer = contentResolved.openInputStream(jsonUri)
                if (streamer == null){
                    return null
                }
                bufferedReader = streamer.bufferedReader()
                applicationContext.openFileOutput(filename, Context.MODE_PRIVATE).use { file ->
                    while(true) {
                        try {
                            val b = bufferedReader.read()
                            file.write(b)
                        } catch (e:IOException){
                            file.close()
                            bufferedReader.close()
                            break
                        }
                    }
                }

                return jsonString
            }
        }

        return null
    }

//    fun showConfigLabelPopup(): String {
//        val builder: AlertDialog.Builder = androidx.appcompat.app.AlertDialog.Builder(this)
//        val input = EditText(this)
//        var configLabel = "cancel"
//
//        input.setHint("what do you want to call this config?")
//        builder.setView(input)
//        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
//            // Here you get get input text from the Edittext
//            configLabel = input.text.toString()
//        })
//        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
//        builder.show()
//        return configLabel
//    }
}


private fun receiveTextFromExternalShare(intent: Intent): String {
     intent.getStringExtra(Intent.EXTRA_TEXT)?.let{
         println(it)
         val message = it
         return message
     }
    return "nope"
}

