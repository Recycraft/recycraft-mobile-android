package com.example.recycraft.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.recycraft.R
import com.example.recycraft.data.local.SettingPreferences
import com.example.recycraft.data.local.SettingPreferencesViewModel
import com.example.recycraft.data.local.SettingPreferencesViewModelFactory
import com.example.recycraft.data.remote.Session
import com.example.recycraft.databinding.ActivityMainBinding
import com.example.recycraft.ui.camera.CameraActivity
import com.example.recycraft.ui.login.LoginActivity

//private val Context.datasore: DataStore<Preferences> by preferencesDataStore(name = "login")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object{
        const val DATA_SESSION = "data session"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //splash screen
        Thread.sleep(2000)
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

      //  getLoginSetting()

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
/*
    private fun getLoginSetting() {
        val pref = SettingPreferences.getInstance(datasore)
        val settingPreferencesViewModel = ViewModelProvider(
            this,
            SettingPreferencesViewModelFactory(pref)
        )[SettingPreferencesViewModel::class.java]

        settingPreferencesViewModel.getLoginSettings().observe(
            this
        ) { data: Session ->
            if (!data.isLogin) {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
            }
        }
    }*/



}