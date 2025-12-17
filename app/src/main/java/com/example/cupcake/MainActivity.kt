/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.cupcake

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.cupcake.screens.HomeScreen
import com.example.cupcake.theme.CupcakeTheme

/**
 * Activity for cupcake order flow.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CupcakeTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(
                        onSendOrder = { order ->
                            shareOrder(order)
                        }
                    )
                }
            }
        }
    }

    private fun shareOrder(orderSummary: String) {
        val intent = Intent(Intent.ACTION_SEND)
            .setType("text/plain")
            .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.new_cupcake_order))
            .putExtra(Intent.EXTRA_TEXT, orderSummary)

        val shareIntent = Intent.createChooser(intent, "Share order")

        try {
            startActivity(shareIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this,
                "No available apps",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}