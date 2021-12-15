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

package com.android.gdsc.ui.faq

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.gdsc.databinding.FragmentFaqBinding
import com.android.gdsc.faq.Faq
import com.android.gdsc.faq.FaqAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FaqFragment : Fragment() {

    private var _binding: FragmentFaqBinding? = null
    private val binding get() = _binding!!

    private lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentFaqBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mContext = requireActivity()

        val faqView = binding.faq
        val faqDBRef = FirebaseDatabase.getInstance().reference.child("faq")
        val faq = ArrayList<Faq>()
        val faqAdapter = FaqAdapter(mContext, faq)

        faqView.setHasFixedSize(true)
        faqView.layoutManager = LinearLayoutManager(mContext)
        faqView.adapter = faqAdapter
        faqDBRef.keepSynced(true)
        faqDBRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                faq.clear()
                for (dataSnapshot in snapshot.children) {
                    val data = dataSnapshot.getValue(Faq::class.java)
                    if (data != null) {
                        faq.add(data)
                    }
                }
                faqAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
