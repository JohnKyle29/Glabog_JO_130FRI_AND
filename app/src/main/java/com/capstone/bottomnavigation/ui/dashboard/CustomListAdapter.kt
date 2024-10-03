package com.capstone.bottomnavigation.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.capstone.bottomnavigation.R

class CustomListAdapter(
    private val context: Context,
    private val itemList: ArrayList<ListItem>,
    private val onRowClickListener: (position: Int) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = itemList.size

    override fun getItem(position: Int): Any = itemList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_items, parent, false)

        // Get references to the UI elements
        val checkBox: CheckBox = view.findViewById(R.id.checkBox)
        val imageView: ImageView = view.findViewById(R.id.image)
        val textView: TextView = view.findViewById(R.id.text)

        // Set data for the item
        val currentItem = itemList[position]
        checkBox.isChecked = currentItem.isChecked
        imageView.setImageResource(currentItem.imageResId)
        textView.text = currentItem.text

        // Set click listener for the entire row
        view.setOnClickListener {
            onRowClickListener(position)
        }

        return view
    }
}
