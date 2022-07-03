package gdsc.knu

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import gdsc.knu.api.getStores
import gdsc.knu.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private val binding by lazy { ActivityMapBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        naverMap.extent = LatLngBounds(
            LatLng(35.885, 128.6),
            LatLng(35.895, 128.62)
        )

        naverMap.setOnMapClickListener { point, coord ->
            println("${coord.latitude}, ${coord.longitude}")
        }

        getStores().forEach { store ->
            Marker().apply {
                position = LatLng(store.latitude, store.longitude)
                map = naverMap
                captionText = store.name
                captionHaloColor = Color.WHITE
                captionTextSize = 15f
            }.setOnClickListener {
                println(store)
                true
            }
        }
    }
}