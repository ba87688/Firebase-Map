package com.example2.roomapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example2.roomapp.R
import com.example2.roomapp.data.Reminder
import kotlinx.android.synthetic.main.reminder_list.view.*

class RemainderRecyclerViewAdapter(val list:List<Reminder>): RecyclerView.Adapter<RemainderRecyclerViewAdapter.ReminderViewHolder>() {

    inner class ReminderViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemainderRecyclerViewAdapter.ReminderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reminder_list,parent,false)
        return ReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: RemainderRecyclerViewAdapter.ReminderViewHolder, position: Int) {
        Log.i("FUCKING HELL", "onBindViewHolder: in the adapater ${list.get(position).id}")

        holder.itemView.apply {
            tv_name.text = list.get(position).title
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}