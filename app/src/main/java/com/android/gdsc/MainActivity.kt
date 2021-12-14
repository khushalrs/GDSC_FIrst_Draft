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
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private var mBannerCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appName = findViewById<TextView>(R.id.header_title)
        Utils.setTypeface(this, appName)

//        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        updateViews()
    }

    private fun setAvatar() {
        val profileImage = findViewById<View>(R.id.profile) as ImageView
        val profileImageUri = Utils.getStringPreference(this, "profile_image", "")
        Glide.with(this).load(Uri.parse(profileImageUri)).circleCrop().into(profileImage)
        profileImage.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun setBanner() {
        val bannerView = findViewById<View>(R.id.banner) as ViewPager
        val bannerDBRef = FirebaseDatabase.getInstance().reference.child("banners")
        val banner = ArrayList<Banner>()
        val bannerAdapter = BannerAdapter(this, banner)

        bannerView.adapter = bannerAdapter
        bannerDBRef.keepSynced(true)
        bannerDBRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val image = dataSnapshot.getValue(Banner::class.java)
                    if (image != null) {
                        banner.add(image)
                    }
                }
                mBannerCount = snapshot.childrenCount.toInt()
                bannerAdapter.notifyDataSetChanged()
                setBannerDots()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun setBannerDots() {
        val bannerView = findViewById<View>(R.id.banner) as ViewPager
        val slider = findViewById<LinearLayout>(R.id.slider)
        val dots = arrayOfNulls<ImageView>(mBannerCount)

        if (mBannerCount == 0) {
            bannerView.visibility = View.GONE
            slider.visibility = View.GONE
        } else {
            bannerView.visibility = View.VISIBLE
            slider.visibility = View.VISIBLE
            for (i in 0 until mBannerCount) {
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
        }

        bannerView.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                for (i in 0 until mBannerCount) {
                    dots[i]?.setImageResource(R.drawable.inactive_dot)
                }
                dots[position]?.setImageResource(R.drawable.active_dot)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun setFeed() {
        val feedView = findViewById<View>(R.id.feed) as RecyclerView
        val feedDBRef = FirebaseDatabase.getInstance().reference.child("posts")
        val feed = ArrayList<Feed>()
        val feedAdapter = FeedAdapter(this, feed)

        feedView.setHasFixedSize(true)
        feedView.layoutManager = LinearLayoutManager(this)
        feedView.adapter = feedAdapter
        feedDBRef.keepSynced(true)
        feedDBRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val post = dataSnapshot.getValue(Feed::class.java)
                    if (post != null) {
                        feed.add(post)
                    }
                }
                feedAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun updateViews() {
        setAvatar()
        setBanner()
        setFeed()
    }
}
