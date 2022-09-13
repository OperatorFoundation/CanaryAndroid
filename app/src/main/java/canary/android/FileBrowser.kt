package canary.android

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import canary.android.utilities.getAppFolder
import canary.android.utilities.showAlert
import java.io.File
import kotlin.coroutines.suspendCoroutine


class FileBrowser() : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fileList: ArrayList<FileData>
    private lateinit var fileAdapter: FileAdapter

    private val tag = "file browser"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_browser)
        Log.i(tag,"file Browser view oncreate")
        //buttons
        val homeButton: Button = findViewById(R.id.homeButton)

        var contentUri:Uri? = null
        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            contentUri = uri
        }

        fileList = ArrayList()
        val fileNameList = getAppFolder().list()
        for (item in fileNameList) {
            if (item.contains(".json")){
                fileList.add(FileData(item))
            }
        }

        recyclerView = findViewById(R.id.fileRecyler)
        fileAdapter = FileAdapter(fileList)
        recyclerView.adapter = fileAdapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        homeButton.setOnClickListener {
            if(userSelectedConfigList.count() < 1){
                showAlert("Please select at least one config")
            }
            //return the user home even if they did not select, otherwise they can get stuck here.
            val homeIntent = Intent(this, MainActivity::class.java)
            startActivity(homeIntent)
        }
    }
}