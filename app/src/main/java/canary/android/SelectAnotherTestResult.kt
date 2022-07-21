package canary.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SelectAnotherTestResult : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var resultsList: ArrayList<ResultsData>
    private lateinit var resultsAdapter: TestResultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_another_test_result)

        resultsList = ArrayList()
        val fileNameList = applicationContext.getFilesDir().list()
        for (item in fileNameList) {
            if (item.contains(".json")){//FIXME need to change
                resultsList.add(ResultsData(item))
            }
        }

        recyclerView = findViewById(R.id.testResultsRecycler)
        resultsAdapter = TestResultsAdapter(resultsList)
        recyclerView.adapter = resultsAdapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}