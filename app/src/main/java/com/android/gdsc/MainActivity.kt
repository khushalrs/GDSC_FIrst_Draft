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

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private var images = intArrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appName = findViewById<TextView>(R.id.header_title)
        Utils.setTypeface(this, appName)

        val mRecyclerView = findViewById<View>(R.id.feed) as RecyclerView
        val mDBRef = FirebaseDatabase.getInstance().reference.child("posts")
        val mFeed = ArrayList<Feed>()
        val mFeedAdapter = FeedAdapter(this, mFeed)
        val mBanner = findViewById<ViewPager>(R.id.banner)

        mBanner.adapter = BannerAdapter(this, images)

        val slider = findViewById<LinearLayout>(R.id.slider)
        val dotsCount = images.size

        val dots = arrayOfNulls<ImageView>(dotsCount)

        if (dotsCount != 0) {
            for (i in 0 until dotsCount) {
                dots[i] = ImageView(this)
                dots[i]?.setImageResource(R.drawable.inactive_dot)
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(8, 0, 8, 0)
                slider.addView(dots[i], params)
            }
            dots[0]?.setImageResource(R.drawable.active_dot)
        } else {
            mBanner.visibility = View.GONE
            slider.visibility = View.GONE
        }

        mBanner.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                for (i in 0 until dotsCount) {
                    dots[i]?.setImageResource(R.drawable.inactive_dot)
                }
                dots[position]?.setImageResource(R.drawable.active_dot)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mFeedAdapter

        mDBRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val post = dataSnapshot.getValue(Feed::class.java)
                    if (post != null) {
                        mFeed.add(post)
                    }
                }
                mFeedAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        val profileImage = findViewById<View>(R.id.profile) as ImageView
        val avatar = BitmapFactory.decodeResource(resources, R.drawable.avatar)
        val roundDrawable = RoundedBitmapDrawableFactory.create(resources, avatar)
        roundDrawable.isCircular = true
        profileImage.setImageDrawable(roundDrawable)
    }
}
