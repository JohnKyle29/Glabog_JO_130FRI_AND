package com.capstone.bottomnavigation.ui.dashboard

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.bottomnavigation.R
import com.capstone.bottomnavigation.databinding.FragmentDashboardBinding
import kotlin.collections.ArrayList

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var listView: ListView
    private lateinit var customAdapter: CustomListAdapter
    private val itemList: ArrayList<ListItem> = arrayListOf(
        ListItem("Item 1", false, R.drawable.img),
        ListItem("Item 2", false, R.drawable.img),
        ListItem("Item 3", false, R.drawable.img)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        listView = binding.listView

        // Set up the custom adapter for ListView
        customAdapter = CustomListAdapter(requireContext(), itemList) { position ->
            // Handle item click here if necessary
            showEditDeleteDialog(position)
        }

        listView.adapter = customAdapter

        // Handle save button click
        binding.saveButton.setOnClickListener {
            addItemToList()
        }

        return root
    }

    private fun addItemToList() {
        val inputText = binding.editText.text.toString()
        if (inputText.isNotBlank()) {
            // Add new item to the list with default image
            itemList.add(ListItem(inputText, false, R.drawable.img))
            customAdapter.notifyDataSetChanged()
            binding.editText.text.clear() // Clear the EditText after adding
        } else {
            Toast.makeText(requireContext(), "Please enter some text", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showEditDeleteDialog(position: Int) {
        val builder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_delete, null)
        builder.setView(dialogView)

        val dialog = builder.create()

        // Get the buttons from dialog layout
        val editButton = dialogView.findViewById<Button>(R.id.editButton)
        val deleteButton = dialogView.findViewById<Button>(R.id.deleteButton)

        // Edit button listener
        editButton.setOnClickListener {
            showEditDialog(position)
            dialog.dismiss()
        }

        // Delete button listener
        deleteButton.setOnClickListener {
            itemList.removeAt(position)
            customAdapter.notifyDataSetChanged()  // Change from adapter to customAdapter
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showEditDialog(position: Int) {
        val builder = AlertDialog.Builder(requireContext())
        val input = EditText(requireContext())
        input.setText(itemList[position].text) // Access the text property of ListItem

        builder.setTitle("Edit Item")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val newItem = input.text.toString()
                itemList[position].text = newItem // Update the text property
                customAdapter.notifyDataSetChanged()  // Change from adapter to customAdapter
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }

        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
