package com.example.recycraft.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.recycraft.R
import com.example.recycraft.databinding.ActivityMainBinding
import com.example.recycraft.ui.camera.CameraActivity


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homeFragment = HomeFragment()
        val accountFragment = AccountFragment()

        makeCurrentFragment(homeFragment)

        binding.bottomNavigationView.menu.getItem(1).isEnabled = false
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menuHome -> makeCurrentFragment(homeFragment)
                R.id.menuAccount -> makeCurrentFragment(accountFragment)
            }
            true
        }

        binding.fabCamera.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container_fragment, fragment)
            commit()
        }
    }

    override fun onBackPressed() {
    }
}