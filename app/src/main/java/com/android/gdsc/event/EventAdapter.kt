/*
 * Copyright (C) 2021 Anushek Prasal (SKULSHADY) <anushekprasal@gmail.com>
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

package com.android.gdsc.event

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.gdsc.R
import com.bumptech.glide.Glide

class EventAdapter(private val context: Context, private val event: ArrayList<Event>) :
    RecyclerView.Adapter<EventAdapter.EventView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventView {
        val v = LayoutInflater.from(context).inflate(R.layout.event, parent, false)
        return EventView(v)
    }

    override fun onBindViewHolder(eventView: EventView, position: Int) {
        val event = event[position]
        Glide.with(context).load(event.image).into(eventView.image)

        if (event.url != null) {
            eventView.image.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(event.url)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return event.size
    }

    class EventView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
    }
}
