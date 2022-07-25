package gdsc.knu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import gdsc.knu.api.getRestaurant
import gdsc.knu.databinding.ActivityRestaurantLookupBinding
import gdsc.knu.model.MenuItem

class LookupActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRestaurantLookupBinding.inflate(layoutInflater) }
    private val menuList = arrayListOf<MenuItem>()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val id = intent.getLongExtra("store_id", 1)
        val restaurant = getRestaurant(id)

        binding.title.text = restaurant.name
        binding.description.text = restaurant.description
        binding.tel.text = restaurant.tel
        binding.address.text = restaurant.address
        binding.tel.text = restaurant.tel
        binding.category.text = restaurant.category.displayName
        addData(restaurant.menu)
        binding.mRecyclerView.adapter = MenuAdapter(menuList)
    }

    private fun addData(menus: List<MenuItem>){
        for(menu in menus){
            menuList.add(menu)
        }
    }
}