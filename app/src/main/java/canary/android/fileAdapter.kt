package canary.android

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import canary.android.utilities.showAlert
import com.beust.klaxon.Klaxon
import java.io.File


class FileAdapter(private val fileList:ArrayList<FileData>)
    :RecyclerView.Adapter<FileAdapter.FileViewHolder>(){

    class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val textView: TextView = itemView.findViewById(R.id.config_menu_object)
        private var view: View = itemView

        init{
            itemView.setOnClickListener(this)
        }


        override fun onClick(v: View?) {
            //return to main activity with the data from the json selected
            val selectedColor = ContextCompat.getColor(this.textView.context, R.color.purple_200)
            val unselectedColor = ContextCompat.getColor(this.textView.context, androidx.appcompat.R.color.abc_background_cache_hint_selector_material_dark)
            //this.textView.setBackgroundColor(selectedColor)

            userSelectedConfig = this.textView.text.toString()
            if (userSelectedConfigList.contains(userSelectedConfig)){
                this.view.setBackgroundColor(unselectedColor)
                //this.textView.setBackgroundColor((unselectedColor))
                userSelectedConfigList.remove(userSelectedConfig)
            } else {
                val selectedColor = ContextCompat.getColor(this.textView.context, R.color.purple_200)
                this.view.setBackgroundColor(selectedColor)
                userSelectedConfigList.add(userSelectedConfig)

            }
            //val homeIntent = Intent(this.view.context, MainActivity::class.java)
            //this.view.context.startActivity(homeIntent)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_custom_row, parent, false)
        return FileViewHolder(view)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val file = fileList[position]
        holder.textView.text = file.filename
    }

    override fun getItemCount(): Int {
        return fileList.size
    }
}

class TestResultsAdapter(private val fileList:ArrayList<ResultsData>)
    :RecyclerView.Adapter<TestResultsAdapter.FileViewHolder>(){

    class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val textView: TextView = itemView.findViewById(R.id.config_menu_object)
        private var view: View = itemView

        init{
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            //return to main activity with the data from the json selected
            userSelectedResult = this.textView.text.toString()
            val homeIntent = Intent(this.view.context, MainActivity::class.java)
            this.view.context.startActivity(homeIntent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_custom_row, parent, false)
        return FileViewHolder(view)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val file = fileList[position]
        holder.textView.text = file.filename
    }

    override fun getItemCount(): Int {
        return fileList.size
    }
}