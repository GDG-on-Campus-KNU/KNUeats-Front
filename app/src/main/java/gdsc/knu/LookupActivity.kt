package gdsc.knu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import gdsc.knu.api.getRestaurant
import gdsc.knu.databinding.ActivityRestaurantLookupBinding
import gdsc.knu.model.MenuItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LookupActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRestaurantLookupBinding.inflate(layoutInflater) }
    private val menuList = arrayListOf<MenuItem>()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val id = intent.getLongExtra("store_id", 1)

        loadRestaurant(id)
    }

    private fun loadRestaurant(id: Long) {
        CoroutineScope(Dispatchers.Main).launch {
            val result =
                CoroutineScope(Dispatchers.IO).async {
                    return@async getRestaurant(id)
                }

            val restaurant = result.await()

            binding.title.text = restaurant.name
            binding.description.text = restaurant.description
            binding.tel.text = restaurant.tel
            binding.address.text = restaurant.address
            binding.tel.text = restaurant.tel
            binding.category.text = restaurant.category.displayName
            addData(restaurant.menu)
            binding.mRecyclerView.adapter = MenuAdapter(menuList)
        }
    }

    private fun addData(menus: List<MenuItem>){
        for(menu in menus){
            menuList.add(menu)
        }
    }
}