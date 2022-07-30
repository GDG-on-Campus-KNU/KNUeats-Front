package gdsc.knu.api

import android.util.Log
import gdsc.knu.model.Category
import gdsc.knu.model.MenuItem
import gdsc.knu.model.Restaurant
import gdsc.knu.model.SearchItem
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

fun getRestaurant(id: Long): Restaurant {
    try {
        val url = "https://knueat.herokuapp.com/$id"

        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        val response = client.newCall(request).execute()
        val body = response.body()

        if (body != null) {
            val json = JSONObject(body.string())

            return parseJsonObjectToRestaurant(json, id)
        }

    } catch (e: Exception){
        Log.e("api_call", e.toString())
    }

    return Restaurant(
        id,
        "dummy",
        "dummy",
        "dummy",
        "dummy",
        0.0,
        0.0,
        "dummy",
        emptyList(),
        Category.ETC,
        0.0,
        0
    )
}

fun getRestaurants(category: Category): List<Restaurant> {
    try {
        val url = "https://knueat.herokuapp.com/category/${category.code}"

        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        val response = client.newCall(request).execute()
        val body = response.body()

        if (body != null) {
            val jsonRestaurants = JSONArray(body.string())
            val restaurants = ArrayList<Restaurant>()

            for (i in 0 until jsonRestaurants.length()) {
                val json = jsonRestaurants.getJSONObject(i)
                json.put("menu", JSONArray())

                restaurants.add(parseJsonObjectToRestaurant(json))
            }

            return restaurants
        }

    } catch (e: Exception) {
        Log.e("api_call", e.toString())
    }

    return emptyList()
}

fun searchRestaurants(search_keyword: String): List<SearchItem> {
    val result = ArrayList<SearchItem>()

    try {
        val url="https://knueat.herokuapp.com/search?word=$search_keyword"

        val client = OkHttpClient()
        val builder = Request.Builder().url(url).get()
        builder.addHeader("Content-Type", "application/json; charset=utf-8")
        val request = builder.build()

        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                val restaurants = JSONArray(body.string())

                for(i in 0 until restaurants.length()){
                    val restaurant = restaurants.getJSONObject(i)

                    val searchItem = SearchItem(
                        restaurant.getLong("id"),
                        restaurant.getString("name")
                    )
                    result.add(searchItem)
                }
            }
        } else System.err.println("Error Occurred")

    } catch (e: Exception) {
        Log.e("api_call", e.toString())
    }

    return result
}

private fun parseJsonObjectToRestaurant(json: JSONObject, id: Long = 0): Restaurant {
    val jsonArray = json.getJSONArray("menu")
    val menus = ArrayList<MenuItem>()
    for (i in 0 until jsonArray.length()) {
        menus.add(parseJsonObjectToMenuItem(jsonArray.getJSONObject(i)))
    }

    return Restaurant(
        json.optLong("id", id),
        json.getString("name"),
        json.getString("description"),
        json.getString("tel"),
        json.getString("address"),
        json.getDouble("lat"),
        json.getDouble("lon"),
        json.getString("location"),
        menus,
        getCategory(json.getString("category")),
        json.getDouble("score"),
        json.getInt("review")
    )
}

private fun parseJsonObjectToMenuItem(json: JSONObject) : MenuItem =
    MenuItem(
        json.getLong("id"),
        json.getLong("restaurantId"),
        json.getString("name"),
        json.getString("price")
    )


private fun getCategory(displayName: String): Category =
    when(displayName) {
        "한식" -> Category.KOREA
        "분식" -> Category.SNACK
        "양식" -> Category.WESTERN
        "일식" -> Category.JAPANESE
        "카페/디저트" -> Category.CAFE
        "중식" -> Category.CHINESE
        "아시안" -> Category.ASIAN
        "술집" -> Category.BAR
        else -> Category.ETC
    }

fun putReview (id: Long, score: Float) {
    try {
        val url = "https://knueat.herokuapp.com/${id}/score"
        val JSON = MediaType.parse("application/json; charset=utf-8")
        val client = OkHttpClient()
        val jsonInput = JSONObject()
        try{
            jsonInput.put("score", score)
        } catch(e: JSONException){
            e.printStackTrace()
            return
        }

        val body = RequestBody.create(JSON, jsonInput.toString())

        val builder = Request.Builder().url(url).post(body)
        val request = builder.build()

        val response = client.newCall(request).execute()
        //등록하고 메인화면으로..
        if (response.isSuccessful) {
            Log.d("PRINT", "SUCCESS")
        }
        else {
            Log.e("upload post", "code: ${response.code()}, message: ${response.body()?.string()}")
        }
    } catch (e: Exception) {
        Log.e("api_call", e.toString())
    }
}