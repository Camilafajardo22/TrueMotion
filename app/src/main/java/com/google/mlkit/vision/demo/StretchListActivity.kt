package com.google.mlkit.vision.demo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StretchListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stretch_list)

        val stretchList = listOf("Brazo Derecho", "Brazo Izquierdo", "Pierna Derecha", "Pierna Izquierda", "Cuadro 5")
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_stretches)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = StretchAdapter(stretchList, this)    }
}