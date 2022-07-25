package com.example2.roomapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example2.roomapp.R
import com.example2.roomapp.data.Reminder

class RemainderRecyclerViewAdapter(val list:List<Reminder>): RecyclerView.Adapter<RemainderRecyclerViewAdapter.ReminderViewHolder>() {

    inner class ReminderViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ReminderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reminder_list,parent,false)
        return ReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return list.size
    }


}