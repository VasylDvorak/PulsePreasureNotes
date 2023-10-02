package com.pulsepreassurenotes.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.pulsepreassurenotes.view.markers.ListFragment
import com.pulsepreasurenotes.R
import com.pulsepreasurenotes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setCurrentFragment(ListFragment())
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setCurrentFragment(ListFragment())
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            addToBackStack("")
            commit()
        }
}