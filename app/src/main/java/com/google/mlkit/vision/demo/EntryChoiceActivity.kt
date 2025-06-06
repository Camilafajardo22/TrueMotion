/*
 * Copyright 2020 Google LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.mlkit.vision.demo

import android.Manifest
import android.content.Context
import android.graphics.Color
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.demo.java.ChooserActivity
import java.util.ArrayList

class EntryChoiceActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)

    val cuadroList = listOf("Cuadro 1", "Cuadro 2", "Cuadro 3", "Cuadro 4", "Cuadro 5")
    window.decorView.setBackgroundColor(Color.parseColor("#1B5979"))

    val btnStart = findViewById<Button>(R.id.btn_start)
    btnStart.setOnClickListener {
      val intent = Intent(this, StretchListActivity::class.java)
      startActivity(intent)
    }

    // Aquí está la magia para el texto responsivo
    val titleTextView = findViewById<TextView>(R.id.title_home)
    val displayMetrics = resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels
    val desiredTextSize = screenWidth * 0.14f  // por ejemplo, 10% del ancho
    titleTextView.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, desiredTextSize)



      /*
    findViewById<TextView>(R.id.java_entry_point).setOnClickListener {
      val intent = Intent(this@EntryChoiceActivity, ChooserActivity::class.java)
      startActivity(intent)
    }

    findViewById<TextView>(R.id.kotlin_entry_point).setOnClickListener {
      val intent =
        Intent(
          this@EntryChoiceActivity,
          com.google.mlkit.vision.demo.kotlin.ChooserActivity::class.java
        )
      startActivity(intent)
    }*/

    if (!allRuntimePermissionsGranted()) {
      getRuntimePermissions()
    }
  }

  private fun allRuntimePermissionsGranted(): Boolean {
    for (permission in REQUIRED_RUNTIME_PERMISSIONS) {
      permission?.let {
        if (!isPermissionGranted(this, it)) {
          return false
        }
      }
    }
    return true
  }
  private fun showChoiceDialog() {
    val builder = android.app.AlertDialog.Builder(this)
    builder.setTitle("Elegir entrada")

    val options = arrayOf("Java", "Kotlin")

    builder.setItems(options) { _, which ->
      when (which) {
        0 -> { // Java
          val intent = Intent(this, com.google.mlkit.vision.demo.java.ChooserActivity::class.java)
          startActivity(intent)
        }
        1 -> { // Kotlin
          val intent = Intent(this, com.google.mlkit.vision.demo.kotlin.ChooserActivity::class.java)
          startActivity(intent)
        }
      }
    }

    builder.create().show()
  }
  private fun getRuntimePermissions() {
    val permissionsToRequest = ArrayList<String>()
    for (permission in REQUIRED_RUNTIME_PERMISSIONS) {
      permission?.let {
        if (!isPermissionGranted(this, it)) {
          permissionsToRequest.add(permission)
        }
      }
    }

    if (permissionsToRequest.isNotEmpty()) {
      ActivityCompat.requestPermissions(
        this,
        permissionsToRequest.toTypedArray(),
        PERMISSION_REQUESTS
      )
    }
  }

  private fun isPermissionGranted(context: Context, permission: String): Boolean {
    if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    ) {
      Log.i(TAG, "Permission granted: $permission")
      return true
    }
    Log.i(TAG, "Permission NOT granted: $permission")
    return false
  }

  companion object {
    private const val TAG = "EntryChoiceActivity"
    private const val PERMISSION_REQUESTS = 1

    private val REQUIRED_RUNTIME_PERMISSIONS =
      arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
      )
  }
}
