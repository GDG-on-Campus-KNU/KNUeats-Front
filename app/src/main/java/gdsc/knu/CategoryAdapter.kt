package gdsc.knu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gdsc.knu.databinding.CategoryItemBinding
import gdsc.knu.model.Category


class CategoryAdapter(
    private val list: ArrayList<Category>,
    private val itemClickListener: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var selectedPosition: Int = 0
    private var oldSelectedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Category, position: Int) {
            val btn = binding.btnContent
            btn.text = data.displayName
            btn.isActivated = position == selectedPosition

            btn.setOnClickListener {
                oldSelectedPosition = selectedPosition
                selectedPosition = position

                itemClickListener(data)

                notifyItemChanged(selectedPosition)
                notifyItemChanged(oldSelectedPosition)
            }
        }
    }
}