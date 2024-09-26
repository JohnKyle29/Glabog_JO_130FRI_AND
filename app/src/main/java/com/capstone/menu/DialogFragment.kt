package com.capstone.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity

class CustomDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment (dialog layout)
        val view = inflater.inflate(R.layout.fragment_dialog, container, false)

        // Find the buttons in the layout
        val negativeButton: Button = view.findViewById(R.id.negative)
        val positiveButton: Button = view.findViewById(R.id.position)

        // Set click listener for the negative button to exit the application
        negativeButton.setOnClickListener {
            activity?.finishAffinity()  // Exits the app
        }

        // Set click listener for the positive button to navigate to NextFragment
        positiveButton.setOnClickListener {
            (activity as? FragmentActivity)?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, NextFragment())
                addToBackStack(null)  // Add to back stack to allow navigation back
                commit()
            }
            dismiss()  // Close the dialog after navigation
        }

        return view
    }
}
