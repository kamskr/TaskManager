package com.example.taskmanager.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.R
import com.example.taskmanager.viewmodels.TaskViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class ListFragment : Fragment() {
    private lateinit var mTaskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        mTaskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        val adapter = ListAdapter(mTaskViewModel)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_tasksList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mTaskViewModel.readAllData.observe(this, Observer { tasks ->
            val today = Date();
            val calendar = Calendar.getInstance();

            calendar.time = today;
            calendar.add(Calendar.DATE, 7);
            val weekFromNow = calendar.time;
            val leftThisWeekText =view.findViewById<TextView>(R.id.tv_tasksLeftThisWeek)
            val addTestDataButton = view.findViewById<Button>(R.id.fillDataButton)
            val visibleTasks = tasks.filter{it.deadline > Date() }

            if(visibleTasks.isEmpty()) {
                addTestDataButton.visibility = View.VISIBLE
                leftThisWeekText.visibility = View.INVISIBLE
                addTestDataButton.setOnClickListener{
                    addTestData()
                }
            } else {
                leftThisWeekText.visibility = View.VISIBLE
                addTestDataButton.visibility = View.INVISIBLE
            }

            adapter.setData(visibleTasks)
            val tasksLeftThisWeek = tasks.filter{it.deadline >= Date() &&  it.deadline <= weekFromNow}
            leftThisWeekText.text = "Tasks left this week: ${tasksLeftThisWeek.size}"
        })




        view.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        return view
    }

    private fun addTestData() {
        mTaskViewModel.addTestData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete) {
            deleteAllTass()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllTass() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mTaskViewModel.deleteAllTasks()
            Toast.makeText(requireContext(), "Successfully deleted all the tasks!", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete task")
        builder.setMessage("Are you sure you want to delete all tasks? You won't be able to reverse this action.")
        builder.create().show()
    }
}