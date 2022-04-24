package com.example.taskmanager.fragments.update

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taskmanager.R
import com.example.taskmanager.model.Task
import com.example.taskmanager.viewmodels.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*

class UpdateFragment : Fragment() {
    private val myCalendar: Calendar = Calendar.getInstance()
    private val args by navArgs<UpdateFragmentArgs>()
    private var deadline: Date? = null
    private lateinit var mTaskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_update, container, false)
        mTaskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]
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

        view.findViewById<Button>(R.id.update_btn).setOnClickListener{
            updateItem(view)
        }

        // Add menu
        setHasOptionsMenu(true)

        return view
    }
    private fun updateLabel(editText: EditText, skipUpdateDate: Boolean) {
        val myFormat = "dd/MM/yy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        editText.setText(dateFormat.format(myCalendar.time))
        if(!skipUpdateDate) {
            deadline = myCalendar.time
        }
    }

    private fun updateItem(view: View) {
        val taskName = view.findViewById<EditText>(R.id.et_taskName_update).text.toString()
        val priority = view.findViewById<EditText>(R.id.et_priority_update).text.toString().toInt()
        val percentageDone = view.findViewById<EditText>(R.id.et_percentageDone_update).text.toString().toInt()
        val estimatedTime = view.findViewById<EditText>(R.id.et_estimatedTime_update).text.toString().toInt()

        if(inputCheck(taskName)) {
            val task = Task(args.currentTask.uid, taskName, priority, deadline!!, percentageDone, estimatedTime)
            mTaskViewModel.updateTask(task)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show()
        }

    }
    private fun inputCheck(taskName: String): Boolean{
        return !(TextUtils.isEmpty(taskName))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete) {
            deleteTask()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteTask() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mTaskViewModel.deleteTask(args.currentTask)
            Toast.makeText(requireContext(), "Successfully deleted!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete task")
        builder.setMessage("Are you sure you want to delete this task?")
        builder.create().show()
    }
}