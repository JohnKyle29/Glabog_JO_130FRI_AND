package com.capstone.bottomnavigation.ui.notifications

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.bottomnavigation.R
import com.capstone.bottomnavigation.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private lateinit var etName: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var cbReading: CheckBox
    private lateinit var cbTraveling: CheckBox
    private lateinit var cbSports: CheckBox
    private lateinit var editButton: Button
    private lateinit var saveButton: Button
    private lateinit var resultTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize views
        etName = binding.etName
        rgGender = binding.rgGender
        cbReading = binding.cbReading
        cbTraveling = binding.cbTraveling
        cbSports = binding.cbSports
        editButton = binding.editButton
        saveButton = binding.saveButton
        resultTextView = binding.resultTextView // Add this TextView in your layout

        // Initially disable input fields
        setEditable(false)

        // Handle the Edit Button click event
        editButton.setOnClickListener {
            setEditable(true)
        }

        // Handle the Save Button click event
        saveButton.setOnClickListener {
            saveChanges()
            setEditable(false)
        }

        return root
    }

    // Function to enable or disable editing of fields
    private fun setEditable(enabled: Boolean) {
        etName.isEnabled = enabled
        for (i in 0 until rgGender.childCount) {
            val radioButton = rgGender.getChildAt(i) as RadioButton
            radioButton.isEnabled = enabled
        }
        cbReading.isEnabled = enabled
        cbTraveling.isEnabled = enabled
        cbSports.isEnabled = enabled
        saveButton.isEnabled = enabled
    }

    // Function to save the changes and display the result with highlighting
    private fun saveChanges() {
        val name = etName.text.toString()

        val selectedGenderId = rgGender.checkedRadioButtonId
        val selectedGender = if (selectedGenderId != -1) {
            binding.root.findViewById<RadioButton>(selectedGenderId)?.text.toString()
        } else {
            "Not specified"
        }

        val hobbies = mutableListOf<String>()
        if (cbReading.isChecked) hobbies.add("Reading")
        if (cbTraveling.isChecked) hobbies.add("Traveling")
        if (cbSports.isChecked) hobbies.add("Sports")

        // Create a result string
        val result = "Name: $name\nGender: $selectedGender\nHobbies: ${hobbies.joinToString(", ")}"

        // Update the resultTextView with the result and highlight it
        resultTextView.text = result
        resultTextView.setBackgroundColor(Color.YELLOW) // Highlight by changing background color

        // Optionally, show a Toast as well
        Toast.makeText(requireContext(), "Changes Saved:\n$result", Toast.LENGTH_LONG).show()

        // Optionally, reset the background color after a short delay (e.g., 2 seconds)
        resultTextView.postDelayed({
            resultTextView.setBackgroundColor(Color.TRANSPARENT)
        }, 2000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
