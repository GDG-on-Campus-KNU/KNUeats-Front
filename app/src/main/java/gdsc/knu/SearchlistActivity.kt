package gdsc.knu

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import gdsc.knu.databinding.ActivitySearchlistBinding
import gdsc.knu.model.Restaurant

class SearchlistActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySearchlistBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        intent.getStringExtra("search_item")?.let { Log.e(TAG, it) }
        val search_item=intent.getStringExtra("search_item").toString()
        var list = dataAdd(search_item)
        val adapter = SearchAdapter(list)
        binding.searchRecycler.adapter=adapter

        binding.search.setOnKeyListener {view, keyCode, event ->
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                imm.hideSoftInputFromWindow(binding.search.windowToken, 0)

                val list=search_dataAdd(binding.search.text.toString())
                val adapter = SearchAdapter(list)
                binding.searchRecycler.adapter = adapter
                true
            }
            false
        }
    }

    // 처음에 메인 화면에서 검색했던 값의 결과를 보여줌
    fun dataAdd(search_item : String) : ArrayList<Restaurant> {
        var list = ArrayList<Restaurant>()
        list.add(Restaurant(1, "yahho", "매일","010+456+789",
            "주소~~",1234.0,234.1, listOf("menu", "menu2"), "한식"))

        return list
    }

    // search 화면에서 검색한 값의 결과를 보여줌
    fun search_dataAdd(search: String) : ArrayList<Restaurant>{
        var list=ArrayList<Restaurant>()
        list.add(Restaurant(1, "테스트1", "매일","010+456+789",
        "주소~~",1234.0,234.1, listOf("menu", "menu2"), "한식"))
        list.add(Restaurant(2, "테스트2", "매일22","010+456+2789",
            "주소~~2",1234.02,234.12, listOf("men22u", "menu2"), "양식"))
        list.add(Restaurant(3, "테스트3", "매일4","010+456+7839",
            "주소~~3",1234.40,2344.1, listOf("me4nu", "menu2"), "일식"))


        return list
    }


}