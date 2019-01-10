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

import com.example.heronymousbot.cinemore.ClassesUtils.Films;
import com.example.heronymousbot.cinemore.ClassesUtils.Trailers;
import com.example.heronymousbot.cinemore.MovieDetail;
import com.example.heronymousbot.cinemore.R;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {


    private ArrayList<Trailers> trailersList;
    private Context context;

    public TrailerAdapter(Context context, ArrayList<Trailers> trailersList) {
        this.context = context;
        this.trailersList = trailersList;
    }

    @NonNull
    @Override
    public TrailerAdapter.TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.trailer_item, parent, false);
        return new TrailerViewHolder(view);
    }

    @NonNull
    @Override
    public void onBindViewHolder(TrailerViewHolder holder, final int position) {
        Trailers currentTrailer = trailersList.get(position);


        String youtubeKey = currentTrailer.getYoutubeUrl();
        Picasso.get()
                .load(currentTrailer.getYoutubeKey()).fit().centerCrop().into(holder.youtubeThumb);


        holder.youtubeThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + youtubeKey));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + youtubeKey));
                try {
                    context.startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    context.startActivity(webIntent);
                }

            }
        });


    }

    @NonNull
    @Override
    public int getItemCount() {
        return trailersList != null ? trailersList.size() : 0;
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder {
        private ImageView youtubeThumb;


        public TrailerViewHolder(View itemView) {
            super(itemView);
            youtubeThumb = itemView.findViewById(R.id.youtube_thumbnail);

        }
    }
}
