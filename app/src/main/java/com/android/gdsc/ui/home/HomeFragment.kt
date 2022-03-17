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

package com.android.gdsc.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.android.gdsc.R
import com.android.gdsc.banner.Banner
import com.android.gdsc.banner.BannerAdapter
import com.android.gdsc.databinding.FragmentHomeBinding
import com.android.gdsc.feed.Feed
import com.android.gdsc.feed.FeedAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {

    private val binding get() = _binding!!
    private var mBannerCount: Int = 0
    private var mPage: Int = 0
    private var mHandler: Handler? = null

    private lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mContext = requireActivity()
        mHandler = Handler(Looper.getMainLooper())

        updateViews()

        return root
    }

    override fun onResume() {
        super.onResume()
        mHandler?.postDelayed(mRunnable, DELAY)
    }

    override fun onPause() {
        super.onPause()
        mHandler?.removeCallbacks(mRunnable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setBanner() {
        val bannerView = binding.banner
        val bannerDBRef = FirebaseDatabase.getInstance().reference.child("banners")
        val banner = ArrayList<Banner>()
        val bannerAdapter = BannerAdapter(mContext, banner)

        bannerView.adapter = bannerAdapter
        bannerDBRef.keepSynced(true)
        bannerDBRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                banner.clear()
                for (dataSnapshot in snapshot.children) {
                    val image = dataSnapshot.getValue(Banner::class.java)
                    if (image != null) {
                        banner.add(image)
                    }
                }
                mBannerCount = snapshot.childrenCount.toInt()
                bannerAdapter.notifyDataSetChanged()
                if (_binding != null) setBannerDots()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun setBannerDots() {
        val bannerView = binding.banner
        val slider = binding.slider
        val dots = arrayOfNulls<ImageView>(mBannerCount)

        if (mBannerCount == 0) {
            bannerView.visibility = View.GONE
            slider.visibility = View.GONE
            return
        } else {
            bannerView.visibility = View.VISIBLE
            slider.visibility = View.VISIBLE
            for (i in 0 until mBannerCount) {
                dots[i] = ImageView(mContext)
                dots[i]?.setImageResource(R.drawable.ic_inactive_dot)
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(8, 0, 8, 0)
                slider.addView(dots[i], params)
            }
            dots[0]?.setImageResource(R.drawable.ic_active_dot)
        }

        bannerView.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {
            }

            override fun onPageSelected(position: Int) {
                mPage = position
                for (i in 0 until mBannerCount) {
                    dots[i]?.setImageResource(R.drawable.ic_inactive_dot)
                }
                dots[position]?.setImageResource(R.drawable.ic_active_dot)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private var mRunnable: Runnable = object : Runnable {
        override fun run() {
            if (mBannerCount != 0) {
                if (mBannerCount == mPage - 1) mPage = 0
                binding.banner.setCurrentItem(mPage++, true)
                mHandler?.postDelayed(this, DELAY)
            }
        }
    }

    private fun setFeed() {
        val feedView = binding.feed
        val feedDBRef = FirebaseDatabase.getInstance().reference.child("posts")
        val feed = ArrayList<Feed>()
        val feedAdapter = FeedAdapter(this, mContext, feed)

        feedView.setHasFixedSize(true)
        feedView.layoutManager = LinearLayoutManager(mContext)
        feedView.adapter = feedAdapter
        feedDBRef.keepSynced(true)
        feedDBRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                feed.clear()
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
        hideProgressBar(this, false)
        setBanner()
        setFeed()
    }

    companion object {
        private const val DELAY = 2500L

        var _binding: FragmentHomeBinding? = null

        fun hideProgressBar(homeFragment: HomeFragment, hide: Boolean) {
            val progressBar = homeFragment.binding.progressBar
            val scrollView = homeFragment.binding.scrollView

            if (progressBar.visibility != View.VISIBLE) return

            progressBar.visibility = if (hide) View.GONE else View.VISIBLE
            scrollView.visibility = if (hide) View.VISIBLE else View.INVISIBLE
        }
    }
}