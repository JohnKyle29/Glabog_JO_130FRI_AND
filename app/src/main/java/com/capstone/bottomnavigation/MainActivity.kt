package com.capstone.bottomnavigation

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.bottomnavigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate layout using View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the toolbar as the ActionBar
        setSupportActionBar(binding.toolbar)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Configure AppBarConfiguration to treat each menu item as a top-level destination
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_notifications,
                R.id.navigation_home,
                R.id.navigation_dashboard,
            )
        )

        // Set up the ActionBar with the NavController
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Set up BottomNavigationView with the NavController
        navView.setupWithNavController(navController)

        // Navigate to notifications fragment on app launch
        if (savedInstanceState == null) {
            navController.navigate(R.id.navigation_notifications)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
