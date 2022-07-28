package gdsc.knu

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import gdsc.knu.api.getRestaurant
import gdsc.knu.api.putReview
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

        binding.inputRatingBar.setOnRatingBarChangeListener{_, rating, _ ->
            binding.inputRatingBar.rating=rating
        }

        binding.ratingBtn.setOnClickListener{
            Log.d("print", "${binding.inputRatingBar.rating}");
            CoroutineScope(Dispatchers.Main).launch {
                val result =
                    CoroutineScope(Dispatchers.IO).async {
                        return@async putReview(id, binding.inputRatingBar.rating)
                    }

                result.await()

                loadRestaurant(id)
                }
            }
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
            if (restaurant.review == 0) {
                binding.scoreText.text = "-"
                binding.ratingBar.rating = 0.0f
            }
            else {
                binding.ratingBar.rating = (restaurant.score/restaurant.review).toFloat()
                binding.scoreText.text = getString(R.string.restaurant_score_text, restaurant.score/restaurant.review)
            }
        }
    }

    private fun addData(menus: List<MenuItem>){
        for(menu in menus){
            menuList.add(menu)
        }
    }

    private fun addReview() {

    }
}