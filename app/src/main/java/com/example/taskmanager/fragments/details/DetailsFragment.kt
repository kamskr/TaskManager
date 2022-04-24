package com.example.taskmanager.fragments.details

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taskmanager.R
import com.example.taskmanager.fragments.list.ListFragmentDirections
import com.example.taskmanager.fragments.update.UpdateFragmentArgs
import com.example.taskmanager.viewmodels.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*

class DetailsFragment : Fragment() {
    private lateinit var mTaskViewModel: TaskViewModel
    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        mTaskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        setHasOptionsMenu(true)

        view.findViewById<TextView>(R.id.tv_taskDetailsTitle).setText(args.currentTask.name)
        view.findViewById<TextView>(R.id.tv_taskDetailsPrio).setText(args.currentTask.priority.toString())
        view.findViewById<TextView>(R.id.tv_taskDetailsEstimatedH).setText(args.currentTask.estimatedTimeInHours.toString())
        view.findViewById<TextView>(R.id.tv_taskDetailsDeadline).setText(args.currentTask.deadline.toString())
        val myFormat = "dd/MM/yy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        view.findViewById<TextView>(R.id.tv_taskDetailsDeadline).setText(dateFormat.format(args.currentTask.deadline))

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete) {
            deleteTask()
        }
        if(item.itemId == R.id.menu_edit) {
            val action = DetailsFragmentDirections.actionDetailsFragmentToUpdateFragment(args.currentTask)
            findNavController().navigate(action)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteTask() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mTaskViewModel.deleteTask(args.currentTask)
            Toast.makeText(requireContext(), "Successfully deleted!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_detailsFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete task")
        builder.setMessage("Are you sure you want to delete this task?")
        builder.create().show()
    }
}