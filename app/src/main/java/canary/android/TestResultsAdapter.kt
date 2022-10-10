package canary.android

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import canary.android.utilities.showAlert

class TestResultsAdapter(private val fileList:ArrayList<ResultsData>): RecyclerView.Adapter<TestResultsAdapter.FileViewHolder>()
{
    class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener
    {
        val textView: TextView = itemView.findViewById(R.id.config_menu_object)
        private var view: View = itemView

        init
        {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?)
        {
            val selectedColor = ContextCompat.getColor(this.textView.context, R.color.purple_200)
            val unselectedColor = ContextCompat.getColor(this.textView.context, androidx.appcompat.R.color.abc_background_cache_hint_selector_material_dark)

            if (this.textView.text.toString() == userSelectedResult)
            {
                this.view.setBackgroundColor(unselectedColor)
                this.textView.setBackgroundColor(unselectedColor)
                userSelectedResult = ""
            }
            else
            {
                this.view.setBackgroundColor(selectedColor)
                userSelectedResult = this.textView.text.toString()
            }
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