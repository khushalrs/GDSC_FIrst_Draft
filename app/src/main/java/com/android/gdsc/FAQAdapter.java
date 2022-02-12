package com.android.gdsc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.FAQViewHolder> {
    ArrayList<FAQList> faqLists;

    public FAQAdapter(ArrayList<FAQList> movieList) {
        this.faqLists = movieList;
    }

    @NonNull
    @Override
    public FAQViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faq, parent, false);
        return new FAQViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FAQViewHolder holder, int position) {

        FAQList faq = faqLists.get(position);
        holder.questionTextView.setText(faq.getName());
        holder.answerTextView.setText(faq.getAnswer());

        boolean isExpanded = faqLists.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return faqLists.size();
    }

    class FAQViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout expandableLayout;
        TextView questionTextView, answerTextView;

        public FAQViewHolder(@NonNull final View itemView) {
            super(itemView);

            questionTextView = itemView.findViewById(R.id.questionTextView);
            answerTextView = itemView.findViewById(R.id.answerTextView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);


            questionTextView.setOnClickListener(view -> {

                FAQList faq = faqLists.get(getAdapterPosition());
                faq.setExpanded(!faq.isExpanded());
                notifyItemChanged(getAdapterPosition());

            });
        }
    }
}
