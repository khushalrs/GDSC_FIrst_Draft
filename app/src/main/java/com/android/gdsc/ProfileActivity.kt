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
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth


class ProfileActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mFirebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        mFirebaseAuth = FirebaseAuth.getInstance()

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        val profileImage = findViewById<View>(R.id.profile_image) as ImageView
        val profileImageUri = Utils.getStringPreference(this, "profile_image", "")
        Glide.with(this).load(Uri.parse(profileImageUri?.replace("s96-c", "s400-c"))).circleCrop()
            .into(profileImage)

        val profileName = findViewById<View>(R.id.profile_display_name) as TextView
        val name = Utils.getStringPreference(this, "profile_display_name", "")
        profileName.text = getString(R.string.name, name)

        val profileEmail = findViewById<View>(R.id.profile_email) as TextView
        val email = Utils.getStringPreference(this, "profile_email", "")
        profileEmail.text = getString(R.string.email, email)

        val profilePoints = findViewById<View>(R.id.profile_points) as TextView
        val points = Utils.getIntPreference(this, "profile_points", 0)
        profilePoints.text = getString(R.string.points, points)

        val signOutButton = findViewById<MaterialButton>(R.id.sign_out_button)
        signOutButton.text = " " + getString(R.string.sign_out)
        signOutButton.setOnClickListener { signOut() }
    }

    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener {
                Utils.setBooleanPreference(this, "signed_in", false)
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
            }
    }
}
