package com.vjezba.androidtab.tabswitch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SeriesArrayAdapter extends ArrayAdapter {
    private List<SeriesDetails> seriesDetailsList;
    private int resource ;
    private Context context;



    public SeriesArrayAdapter(@NonNull Context context, int resource, @NonNull List<SeriesDetails> movieDetails) {
        super(context, resource, movieDetails);
        this.context = context;
        this.resource = resource;
        this.seriesDetailsList = movieDetails;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SeriesDetails details = seriesDetailsList.get(position);
        View view = LayoutInflater.from(context).inflate(resource,parent,false);
        TextView movieName = (TextView) view.findViewById(R.id.textView);
        ImageView image =(ImageView) view.findViewById(R.id.imageView);

        movieName.setText(details.getOriginal_title());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/" + details.getPoster_path()).into(image);


        return view;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return seriesDetailsList.get(position);
    }
}
