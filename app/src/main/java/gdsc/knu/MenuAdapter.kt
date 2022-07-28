package gdsc.knu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gdsc.knu.databinding.MenuItemBinding
import gdsc.knu.model.MenuItem

class MenuAdapter (private val menuList: ArrayList<MenuItem>): RecyclerView.Adapter<MenuAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = menuList[position]
        holder.bind(item)

    }
    class ViewHolder(private val binding: MenuItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(item: MenuItem){
            binding.nameTv.text = item.name
            binding.priceTv.text = item.price
        }
    }
}