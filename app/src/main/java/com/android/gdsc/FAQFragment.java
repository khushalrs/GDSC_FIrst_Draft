package com.android.gdsc;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class FAQFragment extends Fragment {

    RecyclerView rc;
    Context thiscontext;
    ArrayList<FAQList> faqList;
    public FAQFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_faq, container, false);
        thiscontext = container.getContext();
        rc = v.findViewById(R.id.recylcer_faq);
        return v;
    }

    public void addData(){
        faqList = new ArrayList<>();
        faqList.add(new FAQList("Who is the creator of this app?", "Your friendly neighbourhood spiderman"));
        faqList.add(new FAQList("What is this app?", "This is the official app for Google Developers Student Club of Mukesh Patel Schools of Technology and Management"));
        faqList.add(new FAQList("Why do I see wallpapers?", "These are just placeholders until actual data from GDSC can be displayed"));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FAQAdapter faqAdapter = new FAQAdapter(faqList);
        rc.setLayoutManager(new LinearLayoutManager(thiscontext));
        rc.setAdapter(faqAdapter);
    }
}