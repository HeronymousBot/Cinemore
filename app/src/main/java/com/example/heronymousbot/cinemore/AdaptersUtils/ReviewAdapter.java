package com.example.heronymousbot.cinemore.AdaptersUtils;

import android.content.ActivityNotFoundException;
import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.heronymousbot.cinemore.ClassesUtils.Reviews;
import com.example.heronymousbot.cinemore.R;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {


    private ArrayList<Reviews> reviewsList;
    private Context context;

    public ReviewAdapter(Context context, ArrayList<Reviews> reviewsList) {
        this.context = context;
        this.reviewsList = reviewsList;
    }

    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @NonNull
    @Override
    public void onBindViewHolder(ReviewViewHolder holder, final int position) {
        Reviews currentReview = reviewsList.get(position);

        holder.author_tv.setText(currentReview.getAuthor());
        holder.content_tv.setText(currentReview.getContent());

        holder.content_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(currentReview.getReviewUrl()));

                context.startActivity(webIntent);

            }
        });


    }

    @NonNull
    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        private TextView author_tv;
        private TextView content_tv;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            author_tv = itemView.findViewById(R.id.author_of_review);
            content_tv = itemView.findViewById(R.id.content_of_review);
        }
    }
}
