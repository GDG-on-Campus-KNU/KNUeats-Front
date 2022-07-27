package gdsc.knu


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import gdsc.knu.databinding.SearchItemBinding
import gdsc.knu.model.Restaurant
import java.util.ArrayList

class SearchAdapter(private val list: ArrayList<SearchlistActivity.SearchItem>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
        val data = list[position]
        val listener = View.OnClickListener { it ->
            val intent= Intent(holder.itemView?.context, LookupActivity::class.java)
            intent.putExtra("store_id", data.id)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
        holder.apply {
            bind(listener, data)
            itemView.tag=data
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(private val binding: SearchItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private var view : View = binding.root
        fun bind(listener: View.OnClickListener, data: SearchlistActivity.SearchItem){
            binding.textName.text=data.name
            view.setOnClickListener(listener)
        }
    }

//    data class SearchItem(val id: Long, val name: String)
}

