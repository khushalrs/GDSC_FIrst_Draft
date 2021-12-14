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

package com.android.gdsc

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView

class FeedAdapter(private val context: Context, private val feed: ArrayList<Feed>) :
    RecyclerView.Adapter<FeedAdapter.Post>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Post {
        val v = LayoutInflater.from(context).inflate(R.layout.post, parent, false)
        return Post(v)
    }

    override fun onBindViewHolder(post: Post, position: Int) {
        val feed = feed[position]
        Glide.with(context).load(feed.image).into(post.image)
        post.title.text = feed.title
        post.description.text = feed.description
        post.author.text = feed.author
        Utils.setTypeface(context, post.title, post.author)

        if (feed.url != null) {
            post.card.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(feed.url)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return feed.size
    }

    class Post(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: MaterialCardView = itemView.findViewById(R.id.post)
        val image: ImageView = itemView.findViewById(R.id.image)
        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)
        val author: TextView = itemView.findViewById(R.id.author)
    }
}
