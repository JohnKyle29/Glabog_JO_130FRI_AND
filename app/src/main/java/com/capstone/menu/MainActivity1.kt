package com.capstone.menu

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import java.lang.reflect.Method

class MainActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        // Find the menu icon by its ID
        val menuIcon: ImageView = findViewById(R.id.menu_icon)

        // Set a click listener to show the popup menu when clicked
        menuIcon.setOnClickListener { view ->
            showPopupMenu(view)
        }
    }

    // Method to show the popup menu
    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.menu_main, popupMenu.menu)

        // Use reflection to force icons to show in the popup menu
        try {
            val fields = popupMenu.javaClass.declaredFields
            for (field in fields) {
                if ("mPopup" == field.name) {
                    field.isAccessible = true
                    val menuPopupHelper = field.get(popupMenu)
                    val method: Method = menuPopupHelper.javaClass
                        .getDeclaredMethod("setForceShowIcon", Boolean::class.javaPrimitiveType)
                    method.isAccessible = true
                    method.invoke(menuPopupHelper, true)
                    break
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Set item click listener to handle menu item and sub-menu item clicks
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.first_option -> {
                    // When first_option is clicked, navigate to NextFragment
                    replaceFragment(NextFragment())
                    true
                }
                R.id.second_option -> {
                    // When second_option is clicked, show DialogFragment
                    showDialogFragment()
                    true
                }
                R.id.exit_app -> {
                    // When exit_app is clicked, exit the application
                    finishAffinity()
                    true
                }
                R.id.cancel -> {
                    // When cancel is clicked, show a toast and do nothing
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        // Show the popup menu
        popupMenu.show()
    }

    // Method to replace the current fragment with the specified fragment
    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null) // Add to back stack to enable navigation back
        fragmentTransaction.commit()
    }

    // Method to show the DialogFragment
    private fun showDialogFragment() {
        val dialogFragment = CustomDialogFragment()
        dialogFragment.show(supportFragmentManager, "CustomDialog")
    }
}
