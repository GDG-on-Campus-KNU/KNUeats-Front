package gdsc.knu

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.KeyEvent.ACTION_DOWN
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager

import androidx.annotation.UiThread
import androidx.recyclerview.widget.LinearLayoutManager
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import gdsc.knu.api.getRestaurants
import gdsc.knu.databinding.ActivityMapBinding
import gdsc.knu.model.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private val binding by lazy { ActivityMapBinding.inflate(layoutInflater) }
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    private var selectedCategory: Category = Category.KOREA
    private var markers: List<Marker> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupCategories()

        binding.plusButton.setOnClickListener {
            val intent= Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync(this)

        binding.searchMain.setOnKeyListener {view, keyCode, event ->
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                imm.hideSoftInputFromWindow(binding.searchMain.windowToken, 0)

                val intent=Intent(this, SearchlistActivity::class.java)
                intent.putExtra("search_item", binding.searchMain.text.toString())
                startActivity(intent)

                true
            }
            else {
                false
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions,
                grantResults)) {
            if (!locationSource.isActivated) {
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        naverMap.locationOverlay.isVisible = true

        setupMapUi(naverMap.uiSettings)

        naverMap.extent = LatLngBounds(
            LatLng(35.885, 128.6),
            LatLng(35.895, 128.62)
        )

        loadRestaurants(selectedCategory)
    }

    private fun setupMapUi(uiSettings: UiSettings) {
        uiSettings.isLocationButtonEnabled = true
    }

    private fun setupCategories() {
        val categories = ArrayList<Category>()
        for (category in Category.values()) {
            categories.add(category)
        }

        binding.categories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.categories.adapter = CategoryAdapter(categories) {
         selectedCategoryItem: Category -> categoryItemClicked(selectedCategoryItem)
        }
    }

    private fun categoryItemClicked(category: Category) {
        if (selectedCategory != category) {
            selectedCategory = category
            loadRestaurants(category)
        }
    }

    private fun loadRestaurants(category: Category) {
        CoroutineScope(Dispatchers.Main).launch {
            val response =
                CoroutineScope(Dispatchers.IO).async {
                    return@async getRestaurants(category)
                }

            markers.forEach {
                it.map = null
            }

            markers =
                response.await().map { store ->
                    Marker().also {
                        it.position = LatLng(store.latitude, store.longitude)
                        it.map = naverMap
                        it.captionText = store.name
                        it.captionHaloColor = Color.WHITE
                        it.captionTextSize = 15f
                        it.setOnClickListener {
                            val intent= Intent(this@MapActivity, LookupActivity::class.java)
                            intent.putExtra("store_id", store.id)
                            startActivity(intent)

                            true
                        }
                    }
                }
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}