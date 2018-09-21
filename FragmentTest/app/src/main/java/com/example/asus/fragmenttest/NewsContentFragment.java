package com.example.asus.fragmenttest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ASUS on 2018/4/12.
 */

public class NewsContentFragment extends Fragment {
    private View view;
    public View onCreateView(LayoutInflater inflater, ViewGroup contain, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.news_content_frag,contain,false);
        return view;
    }
    public void refresh(String newsTitle,String newsContent) {
        View visibilityLayout = view.findViewById(R.id.visibility_layout);
        visibilityLayout.setVisibility(View.VISIBLE);
        TextView newsTitleText = (TextView) view.findViewById(R.id.news_title);
        TextView newsContentText = (TextView) view.findViewById(R.id.new_content);
        newsContentText.setText(newsContent);
        newsTitleText.setText(newsTitle);
    }
}
