package gdsc.knu

import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import gdsc.knu.databinding.ActivitySearchlistBinding
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject

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
        val search_item=intent.getStringExtra("search_item").toString()
        var list = dataAdd(search_item)
        val adapter = SearchAdapter(list)
        binding.searchRecycler.adapter=adapter
        binding.searchRecycler.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        binding.search.setOnKeyListener {view, keyCode, event ->
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                imm.hideSoftInputFromWindow(binding.search.windowToken, 0)

                val list=dataAdd(binding.search.text.toString())
                val adapter = SearchAdapter(list)
                binding.searchRecycler.adapter = adapter
         //       binding.searchRecycler.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
                true
            }
            false
        }
    }

    // 처음에 메인 화면에서 검색했던 값의 결과를 보여줌
    fun dataAdd(search_item : String) : ArrayList<SearchItem> {
        Log.d("print", search_item)
        var list = ArrayList<SearchItem>()
        try {
            val url="https://knueat.herokuapp.com/search?word="+search_item
            list = getData(url)

        } catch(e: Exception){
            e.printStackTrace()
        }

        return list
    }

    fun getData(url: String) : ArrayList<SearchItem> {
        val list = ArrayList<SearchItem>()
        // OkHttp 클라이언트 객체 생성
        val client = OkHttpClient()

        // GET 요청 객체 생성
        val builder = Request.Builder().url(url).get()
        builder.addHeader("Content-Type", "application/json; charset=utf-8");
        val request = builder.build()

        // OkHttp 클라이언트로 GET 요청 객체 전송
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            // 응답 받아서 처리
            val body = response.body()
            if (body != null) {
                val responseStr = body.string()
                val store_json = JSONArray(responseStr)

                Log.d("store", store_json.toString())
                for(i in 0 until store_json.length()){
                    val json_content : JSONObject = store_json.getJSONObject(i)
                    val search_result = SearchItem(
                        json_content.getLong("id"),
                        json_content.getString("name")
                    )
                    list.add(search_result)
                }

            }
        } else System.err.println("Error Occurred")

        return list
    }

    data class SearchItem(val id: Long, val name: String)

}