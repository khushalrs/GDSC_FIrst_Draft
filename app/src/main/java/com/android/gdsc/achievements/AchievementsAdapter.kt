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

package com.android.gdsc.achievements

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.android.gdsc.R
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip

class AchievementsAdapter(
    private val context: Context,
    private val achievements: ArrayList<Achievements>
) :
    RecyclerView.Adapter<AchievementsAdapter.AchievementsView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementsView {
        val v = LayoutInflater.from(context).inflate(R.layout.achievements, parent, false)
        return AchievementsView(v)
    }

    override fun onBindViewHolder(achievementsView: AchievementsView, position: Int) {
        val achievements = achievements[position]
        achievementsView.name.text = achievements.name
        achievementsView.description.text = achievements.description
        achievementsView.department.text = achievements.department

        when (achievements.medal) {
            0 -> {
                achievementsView.medal.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.ic_certificate
                    )
                )
                achievementsView.medalDescription.text =
                    context.getText(R.string.participation_description)
            }
            1 -> {
                achievementsView.medal.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.ic_medal_gold
                    )
                )
                achievementsView.medalDescription.text =
                    context.getText(R.string.medal_gold_description)
            }
            2 -> {
                achievementsView.medal.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.ic_medal_silver
                    )
                )
                achievementsView.medalDescription.text =
                    context.getText(R.string.medal_silver_description)
            }
            3 -> {
                achievementsView.medal.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.ic_medal_bronze
                    )
                )
                achievementsView.medalDescription.text =
                    context.getText(R.string.medal_bronze_description)
            }
        }

        if (achievements.url != null) {
            achievementsView.card.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(achievements.url)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return achievements.size
    }

    class AchievementsView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: MaterialCardView = itemView.findViewById(R.id.card)
        val name: TextView = itemView.findViewById(R.id.name)
        val description: TextView = itemView.findViewById(R.id.description)
        val department: Chip = itemView.findViewById(R.id.department)
        val medal: ImageView = itemView.findViewById(R.id.medal)
        val medalDescription: TextView = itemView.findViewById(R.id.medal_description)
    }
}
