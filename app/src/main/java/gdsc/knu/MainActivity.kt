package gdsc.knu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import gdsc.knu.databinding.ActivityMainBinding


private lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        binding.plusButton.setOnClickListener {
            val intent= Intent(this, Register::class.java)
            startActivity(intent)
        }
    }
}