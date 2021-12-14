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

package com.android.gdsc.faq

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.gdsc.R
import com.google.android.material.card.MaterialCardView

class FaqAdapter(private val context: Context, private val faq: ArrayList<Faq>) :
    RecyclerView.Adapter<FaqAdapter.FaqView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqView {
        val v = LayoutInflater.from(context).inflate(R.layout.faq, parent, false)
        return FaqView(v)
    }

    override fun onBindViewHolder(faqView: FaqView, position: Int) {
        val faq = faq[position]
        faqView.question.text = faq.question
        faqView.answer.text = faq.answer

        faqView.card.setOnClickListener {
            val visible = faqView.answer.visibility == View.VISIBLE
            faqView.expand.setImageResource((if (visible) R.drawable.ic_expand else R.drawable.ic_collapse))
            faqView.answer.visibility = if (visible) View.GONE else View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return faq.size
    }

    class FaqView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: MaterialCardView = itemView.findViewById(R.id.card)
        val expand: ImageView = itemView.findViewById(R.id.expand)
        val question: TextView = itemView.findViewById(R.id.question)
        val answer: TextView = itemView.findViewById(R.id.answer)
    }
}
