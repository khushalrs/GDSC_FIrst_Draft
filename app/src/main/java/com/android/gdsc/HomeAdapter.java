package com.android.gdsc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class HomeAdapter extends FirebaseRecyclerAdapter<HomeList, HomeAdapter.HomeViewHolder> {
    public HomeAdapter(
            @NonNull FirebaseRecyclerOptions<HomeList> options)
    {
        super(options);
    }
    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_home, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull HomeViewHolder holder, int position, @NonNull HomeList model) {
        holder.setImageName(model.getName());
        holder.mSetImage(model.getImage());
    }

    class HomeViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private ImageView image;
        public HomeViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.image_title);
            image = itemView.findViewById(R.id.recycler_image);
        }
        public void setImageName(String title){
            name.setText(title);
        }
        public void mSetImage(String url){
            Picasso.get().load(url).fit().centerCrop().into(image);
        }
    }
}
