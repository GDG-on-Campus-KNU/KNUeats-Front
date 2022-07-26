package gdsc.knu

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.okhttp.*
import gdsc.knu.databinding.ActivityRegisterBinding
import gdsc.knu.model.Menu
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        // 메뉴 추가
        val list = ArrayList<Menu>()

        binding.addMenu.setOnClickListener {
            list.add(Menu(binding.inputMenu.text.toString(), binding.inputMenuPrice.text.toString()))

            Log.d("test log", list.toString())

            binding.recyclerView.layoutManager=LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.recyclerView.adapter=Adapter(list)
           // binding.recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            binding.inputMenu.setText("")
            binding.inputMenuPrice.setText("")
        }


        // 스피너
        var selected_category = "한식"
        val itemList = listOf("한식", "분식", "양식", "일식", "카페", "중식", "아시안", "술집", "기타")
        val adapter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, itemList)
        binding.inputCategory.adapter = adapter
        binding.inputCategory.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)  {
                selected_category = binding.inputCategory.selectedItem.toString()
            //    binding.temp.setText(selected_category)
            }

            override fun onNothingSelected(parent: AdapterView<*>?){
            }

            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long)  {
                TODO("Not yet implemented")
            }
        }

        var selected_door = "북문"
        val doorList = listOf("북문", "동문", "쪽문/서문")
        val adapter2 = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, doorList)
        binding.inputDoor.adapter = adapter2
        binding.inputDoor.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)  {
                selected_door = binding.inputDoor.selectedItem.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?){
            }

            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long)  {
                TODO("Not yet implemented")
            }
        }


        //등록 버튼 누르면
        binding.registerBtn.setOnClickListener {

            val url = "https://knueat.herokuapp.com"
            val JSON = MediaType.parse("application/json; charset=utf-8")

            val client = OkHttpClient()

            //POST 요청 객체 생성

            val jsonInput = JSONObject()
            try{
                jsonInput.put("name", binding.inputName.text.toString())
                jsonInput.put("description", binding.inputExplan.text.toString())
                jsonInput.put("tel", binding.inputTel.text.toString())
                jsonInput.put("address", binding.inputLocation.text.toString())

                val jsonArray = JSONArray()

                for (i in 0 until list.size){
                    val jsonTemp = JSONObject()
                    jsonTemp.put("name", list.get(i).name)
                    jsonTemp.put("price", list.get(i).price)

                    jsonArray.put(0, jsonTemp)
                }

                jsonInput.put("menu", jsonArray)
                jsonInput.put("category", selected_category)
                jsonInput.put("location", selected_door)
            } catch(e: JSONException){
                e.printStackTrace();
                return@setOnClickListener;
            }

            val body = RequestBody.create(JSON, jsonInput.toString())

            val builder = Request.Builder().url(url).post(body)
            val request = builder.build()

            val response = client.newCall(request).execute()
            //등록하고 메인화면으로..
            if (response.isSuccessful) {
                Log.d("PRINT", "SUCCESS")
                val intent= Intent(this, MapActivity::class.java)
                startActivity(intent)

                finish()
            }
            else {
                Log.e("upload post", "code: ${response.code()}, message: ${response.body().string()}")
            }
        }
    }
}