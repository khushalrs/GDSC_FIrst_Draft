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

package com.android.gdsc.ui.triumphs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.gdsc.AchievementsActivity
import com.android.gdsc.PastEventsActivity
import com.android.gdsc.databinding.FragmentTriumphsBinding

class TriumphsFragment : Fragment() {

    private val binding get() = _binding!!

    private lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentTriumphsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mContext = requireActivity()

        binding.achievements.setOnClickListener {
            startActivity(Intent(mContext, AchievementsActivity::class.java))
        }

        binding.pastEvents.setOnClickListener {
            startActivity(Intent(mContext, PastEventsActivity::class.java))
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        var _binding: FragmentTriumphsBinding? = null
    }
}