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

package com.android.gdsc

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.gdsc.achievements.Achievements
import com.android.gdsc.achievements.AchievementsAdapter
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AchievementsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievements)

        findViewById<ImageView>(R.id.back).setOnClickListener {
            finish()
        }

        val profileImage = findViewById<ImageView>(R.id.profile)
        val profileImageUri = Utils.getStringPreference(this, "profile_image", "")
        Glide.with(this).load(Uri.parse(profileImageUri)).circleCrop().into(profileImage)
        profileImage.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        setAchievements()
    }

    private fun setAchievements() {
        val achievementsView = findViewById<RecyclerView>(R.id.achievements)
        val achievementsDBRef = FirebaseDatabase.getInstance().reference.child("achievements")
        val achievements = ArrayList<Achievements>()
        val achievementsAdapter = AchievementsAdapter(this, achievements)

        if (achievementsView != null) {
            achievementsView.setHasFixedSize(true)
            achievementsView.layoutManager = LinearLayoutManager(this)
            achievementsView.adapter = achievementsAdapter
            achievementsDBRef.keepSynced(true)
            achievementsDBRef.addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    achievements.clear()
                    for (dataSnapshot in snapshot.children) {
                        val post = dataSnapshot.getValue(Achievements::class.java)
                        if (post != null) {
                            achievements.add(post)
                        }
                    }
                    achievementsAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }
    }
}