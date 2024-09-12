package com.capstone.todolist1

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainListActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: CustomListAdapter
    private lateinit var editText: EditText
    private lateinit var saveButton: ImageButton
    private val itemList = arrayListOf(
        ListItem("Item 1", false, R.drawable.img),
        ListItem("Item 2", false, R.drawable.img),
        ListItem("Item 3", false, R.drawable.img)
    )
    private var lastClickTime: Long = 0
    private val doubleClickInterval: Long = 300 // 300 milliseconds for double-click detection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_list)

        listView = findViewById(R.id.listView)
        editText = findViewById(R.id.editText)
        saveButton = findViewById(R.id.saveButton)

        // Set up the adapter for ListView
        adapter = CustomListAdapter(this, itemList) { position ->
            handleDoubleClick(position)
        }
        listView.adapter = adapter

        // Handle Enter key press in EditText to add new item
        editText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                addItem()
                true
            } else {
                false
            }
        }

        // Handle click on the saveButton to add new item
        saveButton.setOnClickListener {
            addItem()
        }
    }

    // Function to handle double-click
    private fun handleDoubleClick(position: Int) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime < doubleClickInterval) {
            // Show dialog on double-click
            showEditDeleteDialog(position)
        } else {
            // First click detected
            Toast.makeText(this, "Click detected, double-click to edit/delete.", Toast.LENGTH_SHORT).show()
        }
        lastClickTime = currentTime
    }

    // Function to show the Edit/Delete dialog
    private fun showEditDeleteDialog(position: Int) {
        // Inflate the dialog layout
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_delete, null)

        // Create the dialog
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        // Show the dialog
        dialog.show()

        // Set click listeners for Edit and Delete buttons inside the dialog
        dialogView.findViewById<Button>(R.id.editButton).setOnClickListener {
            Toast.makeText(this, "Edit option clicked for item $position", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.deleteButton).setOnClickListener {
            deleteItem(position)
            dialog.dismiss()
        }
    }

    // Function to delete an item from the ListView
    private fun deleteItem(position: Int) {
        if (position in itemList.indices) {
            itemList.removeAt(position)
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Invalid item position", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to add a new item to the ListView
    private fun addItem() {
        val newItemText = editText.text.toString().trim()
        if (newItemText.isNotEmpty()) {
            val newItem = ListItem(newItemText, false, R.drawable.img)
            itemList.add(newItem)
            adapter.notifyDataSetChanged()
            editText.text.clear()
        } else {
            Toast.makeText(this, "Please enter text", Toast.LENGTH_SHORT).show()
        }
    }
}
