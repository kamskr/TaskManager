package com.example.taskmanager.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.R
import com.example.taskmanager.data.Task

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private var taskList = emptyList<Task>()
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
     return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent,false))
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTask = taskList[position]
        holder.itemView.findViewById<TextView>(R.id.id_txt).text = currentTask.uid.toString()
        holder.itemView.findViewById<TextView>(R.id.taskName_txt).text = currentTask.name.toString()
        holder.itemView.findViewById<TextView>(R.id.priority_txt).text = "PRIO: ${currentTask.priority.toString()}"
        holder.itemView.findViewById<TextView>(R.id.percentageDone_txt).text = "${currentTask.percentageDone.toString()}%"
    }

    fun setData(tasks: List<Task>) {
        this.taskList = tasks
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}