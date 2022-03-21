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

package com.android.gdsc.mpstme.feed

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.gdsc.mpstme.EventsScreenActivity
import com.android.gdsc.mpstme.MainActivity
import com.android.gdsc.mpstme.R
import com.android.gdsc.mpstme.ui.home.HomeFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.card.MaterialCardView

class FeedAdapter(
    private val homeFragment: HomeFragment,
    private val context: Context,
    private val feed: ArrayList<Feed>,
) :
    RecyclerView.Adapter<FeedAdapter.Post>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Post {
        val v = LayoutInflater.from(context).inflate(R.layout.post, parent, false)
        return Post(v)
    }

    override fun onBindViewHolder(post: Post, @SuppressLint("RecyclerView") position: Int) {
        val feed = feed[position]
        Glide.with(context)
            .load(feed.image)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean,
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean,
                ): Boolean {
                    if (position == itemCount - 1 && HomeFragment._binding != null) {
                        HomeFragment.hideProgressBar(homeFragment, true)
                    }
                    return false
                }
            })
            .into(post.image)

        post.main_title.text = feed.main_title
        post.description.text = feed.description
        post.author.text = feed.author
        post.date.text = feed.date

        post.card.setOnClickListener {
            val intent = Intent(context, EventsScreenActivity::class.java)
            intent.putExtra("action_name", feed.action_name)
            intent.putExtra("action_url", feed.action_url)
            intent.putExtra("title", feed.title)
            intent.putExtra("subtitle", feed.subtitle)
            intent.putExtra("yt_id", feed.yt_id)
            Log.i("yt_id", feed.yt_id.toString())
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return feed.size
    }

    class Post(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: MaterialCardView = itemView.findViewById(R.id.post)
        val image: ImageView = itemView.findViewById(R.id.image)
        val main_title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)
        val author: TextView = itemView.findViewById(R.id.author)
        val date: TextView = itemView.findViewById(R.id.date)
    }
}
