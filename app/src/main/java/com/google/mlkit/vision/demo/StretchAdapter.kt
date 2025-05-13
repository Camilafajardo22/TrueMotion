package com.google.mlkit.vision.demo

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.mlkit.vision.demo.kotlin.LivePreviewActivity

class StretchAdapter(
    private val items: List<Int>,
    private val context: Context
) : RecyclerView.Adapter<StretchAdapter.StretchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StretchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_stretch_card, parent, false)
        return StretchViewHolder(view)
    }

    override fun onBindViewHolder(holder: StretchViewHolder, position: Int) {
        holder.image.setImageResource(items[position])

        holder.itemView.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setMessage("Posicionate como lo muestar la figura")   // 👈 mensaje solicitado
                .setPositiveButton("Aceptar") { dialog, _ ->
                    dialog.dismiss()
                    launchLivePreview(position)
                }
                .show()
        }
    }

    override fun getItemCount(): Int = items.size

    private fun launchLivePreview(position: Int) {
        val intent = Intent(context, LivePreviewActivity::class.java)
        when (position) {
            0 -> intent.putExtra("POSE_MODE", "RIGHT_ARM_STRETCH")
            1 -> intent.putExtra("POSE_MODE", "LEFT_ARM_STRETCH")
            2 -> intent.putExtra("POSE_MODE", "RIGHT_LEG_STRETCH")
            3 -> intent.putExtra("POSE_MODE", "LEFT_LEG_STRETCH")
            else -> intent.putExtra("POSE_MODE", "UNKNOWN")
        }
        context.startActivity(intent)
    }

    class StretchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.stretch_image)
        val card: CardView = view.findViewById(R.id.card_container)
    }
}
