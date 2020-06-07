package com.pratheek.thoughts

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

class ThoughtAdapter(
    var activity: Activity,
    var thoughtList: List<ThoughtDataClass>,
    var listener: ItemClickListener
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<ThoughtAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.thought_card, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return thoughtList.size
    }

    interface ItemClickListener {
        fun onClick(
            content: String,
            thoughtTextStyle: Int,
            thoughtCardBackgroundColor: Int,
            thoughtTextColor: String
        )
    }

    class ViewHolder(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        var thoughtCardView: CardView = itemView.findViewById(R.id.card_thought)
        var thoughtTextView: TextView = itemView.findViewById(R.id.title)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val face = ResourcesCompat.getFont(activity, thoughtList[position].thoughtTextStyle)
        holder.thoughtTextView.typeface = face
//        holder.thoughtTextView.setTextColor(Color.parseColor(thoughtList[position].thoughtTextColor))
        holder.thoughtTextView.text = thoughtList[position].thoughtContent
        holder.thoughtCardView.setCardBackgroundColor(
            ContextCompat.getColor(
                activity,
                thoughtList[position].thoughtCardBackgroundColor
            )
        )
        try {
            holder.thoughtTextView.setTextColor(Color.parseColor(thoughtList[position].thoughtTextColor))
        } catch (e: IllegalArgumentException) {
            holder.thoughtTextView.setTextColor(Color.BLACK)
        }
        holder.thoughtCardView.setOnClickListener {
            listener.onClick(
                thoughtList[position].thoughtContent,
                thoughtList[position].thoughtTextStyle,
                thoughtList[position].thoughtCardBackgroundColor,
                thoughtList[position].thoughtTextColor
            )
        }
    }
}