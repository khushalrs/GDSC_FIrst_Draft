package com.android.gdsc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    String proName, proEmail;
    Uri proPic;
    Button signout;
    GoogleSignInClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        proName = "Name : " + currentUser.getDisplayName().toString();
        proEmail = "Email : " + currentUser.getEmail().toString();
        proPic = currentUser.getPhotoUrl();
        signout = findViewById(R.id.signout);
        client = GoogleSignIn.getClient(this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build());
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.signOut();
                startActivity(new Intent(ProfileActivity.this, SignInActivity.class));
            }
        });
        updateData();
    }
    public void updateData(){
        TextView name = findViewById(R.id.proname);
        TextView email = findViewById(R.id.proemail);
        CircleImageView cirImg = findViewById(R.id.profile_image);
        name.setText(proName);
        email.setText(proEmail);
        Picasso.get().load(proPic.toString()).fit().centerCrop().into(cirImg);
    }
}