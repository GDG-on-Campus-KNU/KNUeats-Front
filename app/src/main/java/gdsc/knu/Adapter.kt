package gdsc.knu

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import gdsc.knu.databinding.ListItemBinding
import gdsc.knu.model.Menu
import kotlin.collections.ArrayList

class Adapter(private val list: ArrayList<Menu>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
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
        fun bind(data: Menu){
            binding.textContent.text=data.name
            binding.textPrice.text=data.price
        }
    }
}

