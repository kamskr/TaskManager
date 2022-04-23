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
import android.text.TextUtils
import android.widget.Button

import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.data.Task
import com.example.taskmanager.viewmodels.TaskViewModel
import java.time.LocalDate


class AddFragment : Fragment() {
    private lateinit var mTaskViewModel: TaskViewModel
    private val myCalendar: Calendar = Calendar.getInstance()
    private var deadline: Date? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        mTaskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]
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

        view.findViewById<Button>(R.id.add_btn).setOnClickListener{
            insertDataToDatabase(view)
        }
        return view
    }

    private fun insertDataToDatabase(view: View) {
        val taskName = view.findViewById<EditText>(R.id.et_taskName).text.toString()
        val priority = view.findViewById<EditText>(R.id.et_priority).text.toString().toInt()
        val percentageDone = view.findViewById<EditText>(R.id.et_percentageDone).text.toString().toInt()
        val estimatedTime = view.findViewById<EditText>(R.id.et_estimatedTime).text.toString().toInt()

        if(inputCheck(taskName)) {
            var task = Task(0, taskName, priority, deadline, percentageDone, estimatedTime)
            mTaskViewModel.addTask(task)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck(taskName: String): Boolean{
        return !(TextUtils.isEmpty(taskName))
    }

    private fun updateLabel(editText: EditText) {
        val myFormat = "MM/dd/yy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        editText.setText(dateFormat.format(myCalendar.time))
        deadline = myCalendar.time
    }
}