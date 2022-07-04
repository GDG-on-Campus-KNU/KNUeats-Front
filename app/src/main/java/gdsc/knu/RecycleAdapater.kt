package gdsc.knu


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gdsc.knu.databinding.ListItemBinding


class RecycleAdapter(val datalist: MutableList<Data>) : RecyclerView.Adapter<RecycleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleAdapter.ViewHolder {
        val view = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    //  val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return RecycleAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecycleAdapter.ViewHolder, position: Int) {
       // val binding = (holder as ViewHolder).binding
        Log.d("test", position.toString())
        Log.d("test", datalist[position].menu.toString())
        holder.binding.textContent.setText(datalist[position].menu)
    //binding.textContent.text="${datalist[position].menu}"
//        holder.bind(datalist[position])
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    class ViewHolder(val binding : ListItemBinding) : RecyclerView.ViewHolder(binding.root)

//    class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
//        val view: TextView = v.findViewById(R.id.textContent)
//        fun bind(data : Data){
//            view.text = data.menu
//        }
//    }


}
