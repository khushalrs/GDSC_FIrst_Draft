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

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.android.gdsc.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)

        val profileImage = binding.profile
        val profileImageUri = Utils.getStringPreference(this, "profile_image", "")
        Glide.with(this).load(Uri.parse(profileImageUri)).circleCrop().into(profileImage)
        profileImage.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.theme.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(this)
            var checkedItem = Utils.getIntPreference(this, KEY_APP_THEME, 0)
            builder.setTitle(R.string.app_theme_title)
            builder.setSingleChoiceItems(R.array.app_theme_entries, checkedItem) { dialog, which ->
                checkedItem = which
                when (which) {
                    0 -> {
                        Utils.setIntPreference(this, KEY_APP_THEME, 0)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    }
                    1 -> {
                        Utils.setIntPreference(this, KEY_APP_THEME, 1)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                    2 -> {
                        Utils.setIntPreference(this, KEY_APP_THEME, 2)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                }
            }
            builder.setPositiveButton(android.R.string.ok) { dialog, which ->
                Utils.setIntPreference(this, KEY_APP_THEME, checkedItem)
            }
            builder.setNegativeButton(android.R.string.cancel, null)
            builder.show()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {} // Night mode is not active, we're using the light theme
            Configuration.UI_MODE_NIGHT_YES -> {} // Night mode is active, we're using dark theme
        }
    }

    companion object {
        const val KEY_APP_THEME = "app_theme"
    }
}