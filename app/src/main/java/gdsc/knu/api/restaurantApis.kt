package gdsc.knu.api

import android.util.Log
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import gdsc.knu.model.Category
import gdsc.knu.model.MenuItem
import gdsc.knu.model.Restaurant
import okhttp3.*
import org.json.JSONArray
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
        val url = "https://knueat.herokuapp.com/category/${category.displayName}"

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
