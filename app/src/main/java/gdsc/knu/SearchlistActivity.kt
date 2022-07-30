package gdsc.knu

import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.text.Editable
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import gdsc.knu.api.searchRestaurants
import gdsc.knu.databinding.ActivitySearchlistBinding
import gdsc.knu.model.SearchItem

class SearchlistActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySearchlistBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        intent.getStringExtra("search_item")?.let { Log.e(TAG, it) }
        val search_item = intent.getStringExtra("search_item").toString()
        val list = dataAdd(search_item)
        val adapter = SearchAdapter(list)
        binding.searchRecycler.adapter=adapter
        binding.searchRecycler.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        binding.search.text = Editable.Factory.getInstance().newEditable(search_item)
        binding.search.setOnKeyListener {view, keyCode, event ->
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                imm.hideSoftInputFromWindow(binding.search.windowToken, 0)

                val searchResult = dataAdd(binding.search.text.toString())
                binding.searchRecycler.adapter = SearchAdapter(searchResult)

                true
            }
            else {
                false
            }
        }
    }

    // 처음에 메인 화면에서 검색했던 값의 결과를 보여줌
    fun dataAdd(search_item : String) : List<SearchItem> {
        var list = emptyList<SearchItem>()
        try {
            list = getData(search_item)
        } catch(e: Exception){
            e.printStackTrace()
        }

        return list
    }

    fun getData(search_item: String) : List<SearchItem> {
        return searchRestaurants(search_item)
    }

}