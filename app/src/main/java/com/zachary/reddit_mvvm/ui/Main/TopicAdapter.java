package com.zachary.reddit_mvvm.ui.Main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.zachary.reddit_mvvm.R;
import com.zachary.reddit_mvvm.base.BaseViewHolder;
import com.zachary.reddit_mvvm.common.Util;
import com.zachary.reddit_mvvm.model.Topic;

import java.util.List;

/**
 * Created by user on 10/5/2017.
 */

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder>{
    private List<Topic> topicList;
    private OnClickListener onClickListener;

    public TopicAdapter(List<Topic> topicList){
        this.topicList = topicList;
    }

    public void setTopicList(List<Topic> topicList){
        this.topicList = topicList;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(topicList.get(position));
    }

    @Override
    public int getItemCount() {
        if (topicList == null) return 0;
        return topicList.size();
    }


    public class ViewHolder extends BaseViewHolder<Topic> implements View.OnClickListener{
        TextView tvDate;
        TextView tvTitle;
        TextView tvCount;
        ImageView ivUp;
        ImageView ivDown;

        public ViewHolder(View itemView) {
            super(itemView);

            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvCount = (TextView) itemView.findViewById(R.id.tvCount);
            ivUp = (ImageView) itemView.findViewById(R.id.ivUp);
            ivDown = (ImageView) itemView.findViewById(R.id.ivDown);

            ivUp.setOnClickListener(this);
            ivDown.setOnClickListener(this);
        }

        @Override
        public void bindData(Topic model) {
            super.bindData(model);
            tvTitle.setText(model.getTitle());
            tvCount.setText(Util.addThousandseparator(model.getCount()));
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null){
                switch (v.getId()){
                    case R.id.ivUp:
                        onClickListener.onUpClick(model.getId());
                        break;
                    case R.id.ivDown:
                        onClickListener.onDownClick(model.getId());
                        break;
                }
            }
        }
    }

    public interface OnClickListener{
        void onUpClick(int topicId);
        void onDownClick(int topicId);
    }
}
