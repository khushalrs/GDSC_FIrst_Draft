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

object Utils {

    @JvmStatic
    fun getBooleanPreference(context: Context, key: String?, defaultValue: Boolean): Boolean {
        val sharedPref = context.getSharedPreferences(
            "settings", Context.MODE_PRIVATE
        )
        return sharedPref.getBoolean(key, defaultValue)
    }

    @JvmStatic
    fun setBooleanPreference(context: Context, key: String?, newValue: Boolean) {
        val sharedPref = context.getSharedPreferences(
            "settings", Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()
        editor.putBoolean(key, newValue)
        editor.apply()
    }
}