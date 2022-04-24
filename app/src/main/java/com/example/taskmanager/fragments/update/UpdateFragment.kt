package com.example.taskmanager.fragments.update

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.navArgs
import com.example.taskmanager.R
import java.text.SimpleDateFormat
import java.util.*

class UpdateFragment : Fragment() {
    private val myCalendar: Calendar = Calendar.getInstance()
    private val args by navArgs<UpdateFragmentArgs>()
    private var deadline: Date? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_update, container, false)
        view.findViewById<EditText>(R.id.et_taskName_update).setText(args.currentTask.name)
        view.findViewById<EditText>(R.id.et_priority_update).setText(args.currentTask.priority.toString())
        view.findViewById<EditText>(R.id.et_percentageDone_update).setText(args.currentTask.percentageDone.toString())
        view.findViewById<EditText>(R.id.et_estimatedTime_update).setText(args.currentTask.estimatedTimeInHours.toString())
        deadline = args.currentTask.deadline
        val editText = view.findViewById<EditText>(R.id.et_deadline_update)

        updateLabel(editText, true)

        val date =
            DatePickerDialog.OnDateSetListener { view, year, month, day ->
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = month
                myCalendar[Calendar.DAY_OF_MONTH] = day
                updateLabel(editText, false)

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
    private fun updateLabel(editText: EditText, skipUpdateDate: Boolean) {
        val myFormat = "MM/dd/yy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        editText.setText(dateFormat.format(myCalendar.time))
        if(!skipUpdateDate) {
            deadline = myCalendar.time
        }
    }
}