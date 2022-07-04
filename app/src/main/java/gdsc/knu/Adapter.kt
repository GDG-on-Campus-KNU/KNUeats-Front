package gdsc.knu

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import gdsc.knu.R
import android.widget.TextView
import gdsc.knu.databinding.ListItemBinding
import java.util.ArrayList

class Adapter (private val list: ArrayList<String>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=ListItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: Adapter.ViewHolder, position: Int) {
       holder.bind(list.get(position))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : String){
            binding.textContent.text=data
        }
    }
}

