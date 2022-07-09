package gdsc.knu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import gdsc.knu.api.getRestaurant
import gdsc.knu.databinding.ActivityStoreLookupBinding
import gdsc.knu.model.MenuItem

class LookupActivity : AppCompatActivity() {
    private var mBinding: ActivityStoreLookupBinding? = null
    private val binding get() = mBinding!!
    private val menuList = arrayListOf<MenuItem>()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        mBinding = ActivityStoreLookupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getLongExtra("store_id", 1)
        val restaurant = getRestaurant(id)

        binding.title.text = restaurant.name
        binding.description.text = restaurant.description
        binding.tel.text = restaurant.tel
        binding.address.text = restaurant.address
        binding.tel.text = restaurant.tel
        binding.category.text = restaurant.category
        addData(restaurant.menu)
        binding.mRecyclerView.adapter = MenuAdapter(menuList)
    }

    override fun onDestroy(){
        mBinding = null
        super.onDestroy()
    }

    private fun addData(menus: List<String>){
        for(i in menus){
            menuList.add(MenuItem(i))
        }
    }
}