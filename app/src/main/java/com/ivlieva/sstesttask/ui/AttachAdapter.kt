package com.ivlieva.sstesttask.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ivlieva.sstesttask.R

class AttachAdapter(private var list: List<Uri>, private val listener: Listener) :
    RecyclerView.Adapter<AttachAdapter.TasksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TasksViewHolder(inflater.inflate(R.layout.attach_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class TasksViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val attachNameTextView = view.findViewById<TextView>(R.id.attachNameTextView)
        private val attachImageView = view.findViewById<ImageView>(R.id.attachImageView)

        fun bind(uri: Uri) {

            attachNameTextView.text = uri.lastPathSegment
            if (uri.scheme.equals("img")) {
                attachImageView.setImageURI(uri)
            }
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) listener.onItemClick(list[adapterPosition])
            }
        }
    }

    interface Listener {
        fun onItemClick(uri: Uri)
    }
}