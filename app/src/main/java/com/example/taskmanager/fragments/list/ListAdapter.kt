package com.example.taskmanager.fragments.list

import android.app.AlertDialog
import android.icu.text.ConstrainedFieldPosition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.R
import com.example.taskmanager.model.Task
import com.example.taskmanager.viewmodels.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*

class ListAdapter(private val mTaskViewModel: TaskViewModel): RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private var taskList = emptyList<Task>()

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
     return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent,false))
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTask = taskList[position]
        holder.itemView.findViewById<TextView>(R.id.taskName_txt).text = currentTask.name.toString()
        holder.itemView.findViewById<TextView>(R.id.priority_txt).text = "PRIO: ${currentTask.priority.toString()}"
        holder.itemView.findViewById<TextView>(R.id.percentageDone_txt).text = "${currentTask.percentageDone.toString()}%"

        val myFormat = "dd/MM/yy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        holder.itemView.findViewById<TextView>(R.id.tv_deadline).text = "Deadline: ${dateFormat.format(currentTask.deadline)}"
        holder.itemView.findViewById<ConstraintLayout>(R.id.cl_tasksList).setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentTask)
            holder.itemView.findNavController().navigate(action)
        }
        holder.itemView.findViewById<ConstraintLayout>(R.id.cl_tasksList).setOnLongClickListener() { _ ->
            deleteTask(currentTask, holder.itemView)
        }


    }

    fun setData(tasks: List<Task>) {
        this.taskList = tasks
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    private fun deleteTask(task: Task, itemView: View): Boolean {
        val builder = AlertDialog.Builder(itemView.context)
        builder.setPositiveButton("Yes") { _, _ ->
            mTaskViewModel.deleteTask(task)
            Toast.makeText(itemView.context, "Successfully completed!", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Complete task")
        builder.setMessage("Are you sure you want to complete this task?")
        builder.create().show()
        return true
    }
}