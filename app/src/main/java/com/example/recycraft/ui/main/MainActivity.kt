package com.example.recycraft.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.example.recycraft.R
import com.example.recycraft.databinding.ActivityMainBinding
import com.example.recycraft.ui.HomeFragment
import com.example.recycraft.ui.camera.CameraActivity
import com.example.recycraft.ui.camera.UploadActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        //splash screen
        Thread.sleep(2000)
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.menu.getItem(1).isEnabled = false

        val homeFragment = HomeFragment()
//        val accountFragment = AccountFragment()

        makeCurrentFragment(homeFragment)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menuHome -> makeCurrentFragment(homeFragment)
//                R.id.menuAccount -> makeCurrentFragment(accountFragment)
            }
            true
        }

        binding.fabCamera.setOnClickListener { view ->
            if (view.id == R.id.fabCamera) {
                val intent = Intent(this@MainActivity, UploadActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container_fragment, fragment)
            commit()
        }
    }
}