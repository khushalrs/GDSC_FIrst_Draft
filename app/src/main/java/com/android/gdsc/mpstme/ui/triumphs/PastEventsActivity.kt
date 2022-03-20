/*
 * Copyright (C) 2022 Anushek Prasal (SKULSHADY) <anushekprasal@gmail.com>
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

package com.android.gdsc.mpstme.ui.triumphs

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.gdsc.mpstme.ProfileActivity
import com.android.gdsc.mpstme.R
import com.android.gdsc.mpstme.Utils
import com.android.gdsc.mpstme.event.Event
import com.android.gdsc.mpstme.event.EventAdapter
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PastEventsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_past_events)

        val action = findViewById<ImageView>(R.id.action)
        action.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.ic_back))
        action.setOnClickListener {
            finish()
        }

        val profileIcon = findViewById<ImageView>(R.id.profile)
        val profileImageUri = Utils.getStringPreference(this, "profile_image", "")
        Glide.with(this).load(Uri.parse(profileImageUri)).circleCrop().into(profileIcon)
        profileIcon.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        updateViews()
    }

    private fun setEvents() {
        val eventView = findViewById<RecyclerView>(R.id.event)
        val eventDBRef = FirebaseDatabase.getInstance().reference.child("events")
        val event = ArrayList<Event>()
        val eventAdapter = EventAdapter(this, event)

        eventView.setHasFixedSize(true)
        eventView.layoutManager = LinearLayoutManager(this)
        eventView.adapter = eventAdapter
        eventDBRef.keepSynced(true)
        eventDBRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                event.clear()
                for (dataSnapshot in snapshot.children) {
                    val image = dataSnapshot.getValue(Event::class.java)
                    if (image != null) {
                        event.add(image)
                    }
                }
                eventAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun updateViews() {
        setEvents()
    }
}