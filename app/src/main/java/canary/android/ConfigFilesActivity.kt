package canary.android

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import canary.android.utilities.getAppFolder
import canary.android.utilities.showAlert
import java.io.File


class ConfigFilesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fileList: ArrayList<ConfigFileData>
    private lateinit var fileAdapter: ConfigFileAdapter

    private val tag = "config file browser"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_browser)
        Log.i(tag,"file Browser view onCreate")
        //buttons
        val homeButton: Button = findViewById(R.id.homeButton)
        val deleteConfigButton: Button = findViewById(R.id.DeleteConfigs)

        var contentUri:Uri? = null
        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            contentUri = uri
        }

        fileList = ArrayList()
        val fileNameList = getAppFolder().list()
        for (item in fileNameList) {
            if (item.contains(".json")){
                fileList.add(ConfigFileData(item))
            }
        }

        recyclerView = findViewById(R.id.fileRecyler)
        fileAdapter = ConfigFileAdapter(fileList)
        recyclerView.adapter = fileAdapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        homeButton.setOnClickListener {
            if(userSelectedConfigList.isEmpty())
            {
                showAlert("Please select at least one config")
            }

            //return the user home even if they did not select, otherwise they can get stuck here.
            val homeIntent = Intent(this, MainActivity::class.java)
            startActivity(homeIntent)
        }

        deleteConfigButton.setOnClickListener{
            if(userSelectedConfigList.count() < 1){
                showAlert("Please select at least one config")
            }
            for (config in userSelectedConfigList){
                val deleteThis = File(getAppFolder(), config)
                deleteThis.delete()
            }
            finish()
            startActivity(getIntent())
        }
    }
}