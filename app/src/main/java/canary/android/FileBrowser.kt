package canary.android

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import canary.android.utilities.showAlert
import java.io.File
import kotlin.coroutines.suspendCoroutine


class FileBrowser() : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fileList: ArrayList<File>
    private lateinit var fileAdapter: FileAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_browser)

        recyclerView = findViewById(R.id.fileRecyler)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fileList = ArrayList()
        val fileNameList = applicationContext.getFilesDir().list()
        for (item in fileNameList) fileList.add(File(item))


        val homeButton: Button = findViewById(R.id.homeButton)
        homeButton.setOnClickListener {
            val homeIntent = Intent(this, MainActivity::class.java)
            startActivity(homeIntent)
        }


    }
}



private fun receiveJsonFromExternalShare(intent: Intent): String {


    return "mistakes were made"
}