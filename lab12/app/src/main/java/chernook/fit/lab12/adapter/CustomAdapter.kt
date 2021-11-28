package chernook.fit.lab12.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import chernook.fit.lab12.R
import chernook.fit.lab12.vk.*
import java.util.ArrayList

class CustomAdapter internal constructor(context: Context?, items: ArrayList<Item>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var items: List<Item> = items

    fun getTaskAt(position: Int): Item {
        return items[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.recycler_view_vk_friends, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.fullNameView.text = item.firstName + " " + item.lastName
        holder.idView.text = "Id: " + item.id
        holder.onlineView.text = "Online: " + (item.online==1)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        val fullNameView: TextView = view.findViewById(R.id.Fullname)
        val idView: TextView = view.findViewById(R.id.id)
        val onlineView: TextView = view.findViewById(R.id.online)
    }

}