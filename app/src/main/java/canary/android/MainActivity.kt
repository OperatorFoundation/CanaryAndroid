/**
 * This activity displays the Canary Test app and allows the user to run connectivity
 * tests, displaying the results on the screen in a scrollable format
 */

package canary.android

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.room.*
import canary.android.utilities.showAlert
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
    var numberTimesRunTest = 1

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

        //textViews
        var testLogs: TextView = findViewById(R.id.logDisplayField)
        var numberTestsLabel: TextView = findViewById(R.id.numberOfTestsDisplay)
        var configName: TextView = findViewById(R.id.SelectedConfigName)

        //buttons:
        val runTestButton: Button = findViewById(R.id.runButton)
        val browseButton: Button = findViewById(R.id.SelectConfigButton)
        val testMoreButton: Button = findViewById(R.id.testMore)
        val testLessButton: Button = findViewById(R.id.testLess)
        val selectConfigButton: Button = findViewById(R.id.SelectConfigButton)

        //variables and things

        var logs = "lorem ipsum blah bla blabber"
        var contentUri:Uri? = null
        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            contentUri = uri
        }

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
                    val path = applicationContext.getFilesDir()
                    var lsstring = path.list().joinToString(" ")

                    showAlert(lsstring)

//                    var canaryDirectory = File(path, "Let")
//                    canaryDirectory.mkdirs()
//                    val configFile = File(canaryDirectory, "config_list.txt")
//                    if (configFile.readLines().contains(parsedJson.toString())){
//                        showAlert("this config is already in the list")
//                        return
//                    }
//                    val newConfig = parsedJson.toString() + "\n"
//                    configFile.appendText(newConfig)
//                    showAlert("Config Saved to List")

                    val configObject = Config(
                        1,
                        "configtest1",
                        parsedJson.password,
                        parsedJson.cipherName,
                        parsedJson.serverIP,
                        parsedJson.port
                    )
                }
            }
        }

        runTestButton.setOnClickListener{
            showAlert("performing tests")
            //Canary Library Functionality here. 
            testLogs.text = logs
        }

        browseButton.setOnClickListener {
            val browseIntent = Intent(this, FileBrowser::class.java)
            startActivity(browseIntent)
        }

        testMoreButton.setOnClickListener{
            numberTimesRunTest += 1
            numberTestsLabel.text = numberTimesRunTest.toString()+" times"
        }

        testLessButton.setOnClickListener{
            if(numberTimesRunTest != 1){
                numberTimesRunTest -= 1
            }

            numberTestsLabel.text = numberTimesRunTest.toString()+" times"
            showAlert(numberTimesRunTest.toString())
        }

        selectConfigButton.setOnClickListener {
            getContent.launch("application/json")
            var config = contentUri.toString()
            if (config == null){
                showAlert("get content does not seem to return anything")
            }
            showAlert(config)
            configName.text = config
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
                val filename = UUID.randomUUID().toString() + ".json"
                streamer = contentResolved.openInputStream(jsonUri)
                if (streamer == null){
                    return null
                }
                bufferedReader = streamer.bufferedReader()
                //File(filename).writeText(jsonString)
                applicationContext.openFileOutput(filename, Context.MODE_PRIVATE).use { file ->
                    while(true) {
                        try {
                            val b = bufferedReader.read()
                            file.write(b)
                            if (b == 125){
                                file.close()
                                bufferedReader.close()
                                streamer.close()
                                break
                            }
                        } catch (e:IOException){
                            file.close()
                            bufferedReader.close()
                            streamer.close()
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

