package gdsc.knu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gdsc.knu.databinding.CategoryItemBinding
import gdsc.knu.model.Category
import java.util.ArrayList


class CategoryAdapter(private val list: ArrayList<Category>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position].displayName)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(private val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : String){
            binding.displayName.text = data
        }
    }
}