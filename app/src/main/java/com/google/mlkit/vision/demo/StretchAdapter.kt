package com.google.mlkit.vision.demo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class StretchAdapter(
    private val items: List<String>,
    private val context: Context
) : RecyclerView.Adapter<StretchAdapter.StretchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StretchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_stretch_card, parent, false)
        return StretchViewHolder(view)
    }

    override fun onBindViewHolder(holder: StretchViewHolder, position: Int) {
        val name = items[position]
        holder.title.text = name

        holder.card.setOnClickListener {
            val intent = Intent(context, com.google.mlkit.vision.demo.kotlin.ChooserActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size

    class StretchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.stretch_name)
        val card: CardView = view.findViewById(R.id.card_container)
    }
}
