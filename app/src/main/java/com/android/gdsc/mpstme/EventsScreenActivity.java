package com.android.gdsc.mpstme;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;

public class EventsScreenActivity extends YouTubeBaseActivity {
    String api_key = "AIzaSyAoVHyKEo2T6lVqhugQftzjgpoDyiZfQsg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_screen);
        Intent intent = getIntent();
        String action_name = intent.getStringExtra("action_name");
        String action_url = intent.getStringExtra("action_url");
        String ytid = intent.getStringExtra("yt_id");
        String title = intent.getStringExtra("title");
        String subtitle = intent.getStringExtra("subtitle");
        ExpandableTextView expTv =findViewById(R.id.expand_text_view);
        expTv.setText(subtitle);
        TextView tview = findViewById(R.id.titleTV);
        tview.setText(title);
        ImageButton sharebtn = findViewById(R.id.sharebtn);
        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = "Check out the following video: https://youtube.com/watch?v=" + action_url;
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, data);
                i.setType("text/plain");
                startActivity(i);
            }
        });
        Button action = findViewById(R.id.action_button);
        action.setText(action_name);
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(action_url));
                startActivity(i);
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        YouTubePlayerView ytPlayer = (YouTubePlayerView)findViewById(R.id.ytPlayer);
        ytPlayer.initialize(
                api_key,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(
                            YouTubePlayer.Provider provider,
                            YouTubePlayer youTubePlayer, boolean b){
                        youTubePlayer.loadVideo(ytid.toString());
                        youTubePlayer.play();
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult){
                        Toast.makeText(getApplicationContext(), "Video player Failed", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Youtube Player View initialization failed");
                    }
                });
    }
}