package gdsc.knu

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import gdsc.knu.api.searchRestaurants
import gdsc.knu.databinding.ActivitySearchlistBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchlistActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySearchlistBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        intent.getStringExtra("search_item")?.let { Log.e(TAG, it) }
        val searchItem = intent.getStringExtra("search_item").toString()

        loadRestaurants(searchItem)

        binding.searchRecycler.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        binding.search.text = Editable.Factory.getInstance().newEditable(searchItem)
        binding.search.setOnKeyListener {view, keyCode, event ->
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                imm.hideSoftInputFromWindow(binding.search.windowToken, 0)

                loadRestaurants(binding.search.text.toString())

                true
            }
            else {
                false
            }
        }
    }

    private fun loadRestaurants(search_keyword : String) {
        CoroutineScope(Dispatchers.Main).launch {
            val response =
                CoroutineScope(Dispatchers.IO).async {
                    return@async searchRestaurants(search_keyword)
                }

            val searchItems = response.await()

            binding.searchRecycler.adapter = SearchAdapter(searchItems)
        }
    }

}