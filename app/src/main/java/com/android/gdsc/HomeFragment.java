package com.android.gdsc;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.material.progressindicator.BaseProgressIndicator;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    FirebaseDatabase database;
    FirebaseRecyclerOptions<HomeList> options;
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    DatabaseReference ref2;
    Query ref1;
    Context thiscontext;
    ArrayList<SliderData> sliderList;
    SliderView sliderView;
    SliderAdapter sliderAdapter;
    CircularProgressIndicator progressBar;
    LinearLayout scroll;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance("https://gdsc-task1-default-rtdb.firebaseio.com/");
        ref1 = FirebaseDatabase.getInstance().getReference().child("Recycler");
        ref2 = database.getReference().child("Home");
        Log.i("Ref1", ref1.toString());
        sliderList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        thiscontext = container.getContext();
        sliderView = v.findViewById(R.id.home_slider);
        scroll = v.findViewById(R.id.linearlayout);
        progressBar = v.findViewById(R.id.home_progress);
        recyclerView = v.findViewById(R.id.recylcer_home);
        scroll.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setShowAnimationBehavior(BaseProgressIndicator.SHOW_NONE);
        options = new FirebaseRecyclerOptions.Builder<HomeList>().setQuery(ref1, new SnapshotParser<HomeList>() {
            @NonNull
            @Override
            public HomeList parseSnapshot(@NonNull DataSnapshot snapshot) {
                return new HomeList(snapshot.child("Name").getValue().toString(), snapshot.child("url").getValue().toString());
            }
        }).build();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sliderAdapter = new SliderAdapter(thiscontext, sliderList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(thiscontext);
        recyclerView.setLayoutManager(layoutManager);
        addData();
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setScrollTimeInSec(8);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<HomeList, HomeHolder>(options) {
            @NonNull
            @Override
            public HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                View view = layoutInflater.inflate(R.layout.recycler_home, parent, false);
                return new HomeHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull HomeHolder holder, int position, @NonNull HomeList model) {
                holder.setImageName(model.getName());
                holder.mSetImage(model.getImage());
            }

            @Override
            public void onDataChanged() {
                progressBar.setVisibility(View.GONE);
                progressBar.setHideAnimationBehavior(BaseProgressIndicator.HIDE_NONE);
                scroll.setVisibility(View.VISIBLE);
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }
    public void addData(){
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    sliderList.add(new SliderData(snapshot.getValue().toString()));
                }
                sliderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    static class HomeHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public ImageView image;
        public HomeHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.image_title);
            image = itemView.findViewById(R.id.recycler_image);
        }
        public void setImageName(String title){
            name.setText(title);
        }
        public void mSetImage(String url){
            Picasso.get().load(url).fit().centerCrop().into(image);
            Log.i("Image set", url);
        }
    }


    @Override
    public void onStart()
    {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }
}

