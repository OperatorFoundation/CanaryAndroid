package canary.android

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import canary.android.utilities.showAlert
import java.io.File

class TestResultsAdapter(val fileList:ArrayList<File>): RecyclerView.Adapter<TestResultsAdapter.FileViewHolder>()
{
    class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener
    {
        val textView: TextView = itemView.findViewById(R.id.config_menu_object)
        private var view: View = itemView
        var resultFile: File? = null

        init
        {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?)
        {
            val selectedColor = ContextCompat.getColor(this.textView.context, R.color.purple_200)
            val unselectedColor = ContextCompat.getColor(this.textView.context, androidx.appcompat.R.color.abc_background_cache_hint_selector_material_dark)

            if (userSelectedResult != null && this.textView.text.toString() == userSelectedResult!!.name)
            {
                this.view.setBackgroundColor(unselectedColor)
                this.textView.setBackgroundColor(unselectedColor)
                userSelectedResult = null
            }
            else
            {
                this.view.setBackgroundColor(selectedColor)
                userSelectedResult = resultFile
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_custom_row, parent, false)
        return FileViewHolder(view)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val file = fileList[position]
        holder.textView.text = file.name
        holder.resultFile = file
    }

    override fun getItemCount(): Int {
        return fileList.size
    }
}