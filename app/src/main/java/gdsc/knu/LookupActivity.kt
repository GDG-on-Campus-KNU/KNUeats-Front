package gdsc.knu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import gdsc.knu.api.getRestaurant
import gdsc.knu.databinding.StoreLookupBinding
import gdsc.knu.model.MenuItem

class LookupActivity : AppCompatActivity() {
    private var mBinding: StoreLookupBinding? = null
    private val binding get() = mBinding!!
    var menuList = arrayListOf<MenuItem>()
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        mBinding = StoreLookupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val store = getRestaurant(1)

        binding.title.text = store.name
        binding.description.text = store.description
        binding.tel.text = store.tel
        binding.address.text = store.address
        binding.tel.text = store.tel
        binding.category.text = store.category
        addData(store.menu)
        binding.mRecyclerView.adapter = MenuAdapter(menuList)
    }
    override fun onDestroy(){
        mBinding = null
        super.onDestroy()
    }
    fun addData(mlist:List<String>){
        for(i in mlist){
            menuList.add(MenuItem(i))
        }
    }
}