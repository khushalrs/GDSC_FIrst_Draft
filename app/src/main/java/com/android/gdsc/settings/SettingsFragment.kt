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

package com.android.gdsc.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.android.gdsc.R
import com.android.gdsc.Utils

class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {

    private lateinit var mContext: Context
    private lateinit var mTheme: ListPreference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings)

        mContext = requireActivity()

        mTheme = findPreference(KEY_APP_THEME)!!
        val index = Utils.getIntPreference(mContext, KEY_APP_THEME, 0)
        mTheme.setValueIndex(index)
        mTheme.summary = mTheme.entries[index]
        mTheme.onPreferenceChangeListener = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.fragment_settings, container, false)
        (view as ViewGroup).addView(super.onCreateView(inflater, container, savedInstanceState))

        mContext.setTheme(R.style.Theme_GDSC_Settings)

        return view
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        if (preference == mTheme) {
            val value = newValue as String
            val index = mTheme.findIndexOfValue(value)
            mTheme.summary = mTheme.entries[index]
            mTheme.setValueIndex(index)
            Utils.setIntPreference(mContext, KEY_APP_THEME, index)
            Utils.updateTheme(mContext)
            return true
        }
        return false
    }

    companion object {
        const val KEY_APP_THEME = "app_theme"
    }
}
