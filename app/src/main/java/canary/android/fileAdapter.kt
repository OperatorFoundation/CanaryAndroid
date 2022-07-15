package canary.android

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
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
            userSelectedConfig = this.textView.text.toString()
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

//    fun getFileData(file: File){
//        val filecontents =
//        val parsedJson = Klaxon()
//            .parse<JsonConfig>(filecontents)
//        if (parsedJson == null) {
//            return
//        }
//    }

}