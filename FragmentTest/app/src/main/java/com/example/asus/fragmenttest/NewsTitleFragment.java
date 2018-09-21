package com.example.asus.fragmenttest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by ASUS on 2018/4/13.
 */

public class NewsTitleFragment extends Fragment {
    private boolean isTwoPane;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.news_title_frag,container,false);
        RecyclerView newsTitleRecyclerView =(RecyclerView)view.findViewById(R.id.new_tltle_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        newsTitleRecyclerView.setLayoutManager(layoutManager);
        NewsAdapter adapter = new NewsAdapter(getNews());
        newsTitleRecyclerView.setAdapter(adapter);
        return view;
    }
    public void onActivityCreated(Bundle savedIntancestate){
        super.onActivityCreated(savedIntancestate);
        if(getActivity().findViewById(R.id.news_content_layout)!=null)
            isTwoPane=true;
        else
            isTwoPane=false;
    }
    private List<News> getNews(){
        List<News> newses = new ArrayList<>();
        for(int i= 1;i<=50;i++){
            News news = new News();
            news.setTitle("This is new title"+i);
            news.setContent(getRandomLengthContent("this is new content")+i);
            newses.add(news);
        }
        return newses;
    }
    private String getRandomLengthContent(String content){
        Random random = new Random();
        int length = random.nextInt(20)+1;
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<length;i++){
            builder.append(content);
        }
        return builder.toString();
    }

    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
        private List<News> newsList;
        class ViewHolder extends  RecyclerView.ViewHolder{
            TextView newsTitleView;
            public ViewHolder(View view){
                super(view);
                newsTitleView = (TextView)view.findViewById(R.id.news_title);
            }
        }
        public NewsAdapter(List<News> newses){
            newsList = newses;
        }
        public ViewHolder onCreateViewHolder(ViewGroup parent,int viewtype){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_item,parent,false);
            final ViewHolder holder = new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    News news = newsList.get(holder.getAdapterPosition());
                    if (isTwoPane) {
                        NewsContentFragment newsContentFragment = (NewsContentFragment) getFragmentManager()
                                .findFragmentById(R.id.news_content_fragment);
                        newsContentFragment.refresh(news.getTitle(), news.getContent());
                    } else {
                        NewsContentActivity.actionStart(getActivity(), news.getTitle(), news.getContent());
                    }
                  }
            } );
                    return holder;
                }
            public void onBindViewHolder(ViewHolder holder,int position){
                News news = newsList.get(position);
                holder.newsTitleView.setText(news.getTitle());
            }
        public int getItemCount(){
            return newsList.size();
        }
    }
}
