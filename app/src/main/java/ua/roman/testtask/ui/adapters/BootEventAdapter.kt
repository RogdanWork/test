package ua.roman.testtask.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ua.roman.testtask.R
import ua.roman.testtask.data.enitty.BootEventEntity

class BootEventAdapter : RecyclerView.Adapter<BootEventAdapter.BootEventViewHolder>() {
    private val items: MutableList<BootEventEntity> = ArrayList()

    fun setEvents(newItems: List<BootEventEntity>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BootEventViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_boot_event, parent, false)
        return BootEventViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BootEventViewHolder, position: Int) {
        val event = items[position]
        holder.bind(position, event)
    }

    override fun getItemCount() = items.size

    inner class BootEventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int, event: BootEventEntity) {
            val textView = itemView.findViewById<TextView>(R.id.text_view) //todo ViewBinding
            textView.text = "$position. ${event.time}" //todo string format
        }
    }
}