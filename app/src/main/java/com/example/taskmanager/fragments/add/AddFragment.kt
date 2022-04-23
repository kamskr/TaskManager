package com.example.taskmanager.fragments.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.taskmanager.R
import java.text.SimpleDateFormat
import java.util.*
import com.example.taskmanager.MainActivity

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener

import android.widget.DatePicker




class AddFragment : Fragment() {
    private val myCalendar: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        val editText = view.findViewById<EditText>(R.id.et_deadline)
        val date =
            OnDateSetListener { view, year, month, day ->
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = month
                myCalendar[Calendar.DAY_OF_MONTH] = day
                updateLabel(editText)
            }

        editText.setOnClickListener(View.OnClickListener {
            DatePickerDialog(
                view.context,
                date,
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        })
        return view
    }

    private fun updateLabel(editText: EditText) {
        val myFormat = "MM/dd/yy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        editText.setText(dateFormat.format(myCalendar.time))
    }
}