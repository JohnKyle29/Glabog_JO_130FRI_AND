package com.capstone.news

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize layout based on current orientation
        val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        updateLayoutForOrientation(isLandscape)

        if (savedInstanceState == null) {
            val listFragment = ListFragment.newInstance("param1", "param2")

            if (isLandscape) {
                // Show ListFragment and an empty ContentFragment side by side
                supportFragmentManager.beginTransaction()
                    .replace(R.id.list_fragment_container, listFragment)
                    .commit()

                // Add an empty ContentFragment initially
                supportFragmentManager.beginTransaction()
                    .replace(R.id.content_fragment_container, ContentFragment.newInstance("", ""))
                    .commit()
            } else {
                // Show ListFragment in portrait mode
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, listFragment)
                    .commit()
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val isLandscape = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE
        updateLayoutForOrientation(isLandscape)
    }

    private fun updateLayoutForOrientation(isLandscape: Boolean) {
        val listContainer = findViewById<FrameLayout>(R.id.list_fragment_container)
        val contentContainer = findViewById<FrameLayout>(R.id.content_fragment_container)
        val singleContainer = findViewById<FrameLayout>(R.id.fragment_container)

        if (isLandscape) {
            listContainer.visibility = View.VISIBLE
            contentContainer.visibility = View.VISIBLE
            singleContainer.visibility = View.GONE

            // Reload fragments in landscape mode
            supportFragmentManager.beginTransaction()
                .replace(R.id.list_fragment_container, ListFragment.newInstance("param1", "param2"))
                .commit()

            // Ensure the content fragment is visible and empty initially
            supportFragmentManager.beginTransaction()
                .replace(R.id.content_fragment_container, ContentFragment.newInstance("", ""))
                .commit()
        } else {
            listContainer.visibility = View.GONE
            contentContainer.visibility = View.GONE
            singleContainer.visibility = View.VISIBLE

            // Reload fragment for portrait mode
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ListFragment.newInstance("param1", "param2"))
                .commit()
        }
    }

    fun replaceFragment(fragment: Fragment, isContent: Boolean = false) {
        val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        if (isLandscape) {
            val containerId = if (isContent) R.id.content_fragment_container else R.id.list_fragment_container
            supportFragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
