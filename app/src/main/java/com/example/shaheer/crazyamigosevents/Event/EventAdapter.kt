package com.example.shaheer.crazyamigosevents.Event
import com.example.shaheer.crazyamigosevents.R
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import kotlinx.android.synthetic.main.event_row.*
import kotlinx.android.synthetic.main.event_row.view.*

/**
 * Created by SHAHEER on 26/07/2018.
 */
class EventAdapter(val events:ArrayList<Event>):RecyclerView.Adapter<EventAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout=LayoutInflater.from(parent.context).inflate(R.layout.event_row,parent,false)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(events.get(position))
    }

    override fun getItemCount(): Int {
        return events.size
    }

    class ViewHolder(val view:View):RecyclerView.ViewHolder(view){
        fun bind(event: Event){
            view.event_name.text=event.name.toString()
            view.venue.text=event.venue.toString()
            view.date.text=event.date.toString()
        }
    }
}