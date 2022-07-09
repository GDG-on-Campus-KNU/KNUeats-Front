package gdsc.knu

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.UiThread
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import gdsc.knu.api.getStores
import gdsc.knu.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private val binding by lazy { ActivityMapBinding.inflate(layoutInflater) }
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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

        getStores().forEach { store ->
            Marker().apply {
                position = LatLng(store.latitude, store.longitude)
                map = naverMap
                captionText = store.name
                captionHaloColor = Color.WHITE
                captionTextSize = 15f
            }.setOnClickListener {
                println(store.id)
                true
            }
        }
    }

    private fun setupMapUi(uiSettings: UiSettings) {
        uiSettings.isLocationButtonEnabled = true
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}