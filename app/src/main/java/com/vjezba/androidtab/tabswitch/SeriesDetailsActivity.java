package com.vjezba.androidtab.tabswitch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class SeriesDetailsActivity extends AppCompatActivity {

    private ImageView image;
    private TextView title,date,overview,rating;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        image = (ImageView) findViewById(R.id.poster);
        title = (TextView) findViewById(R.id.title);
        rating = (TextView) findViewById(R.id.rating);
        date =(TextView) findViewById(R.id.date);
        overview = (TextView) findViewById(R.id.overview);

        SeriesDetails sdetails =(SeriesDetails) getIntent().getExtras().getSerializable("MOVIE_DETAILS");

        if(sdetails != null){
            Glide.with(this).load("https://image.tmdb.org/t/p/w500/" + sdetails.getPoster_path()).into(image);
            title.setText(sdetails.getOriginal_title());
            date.setText(sdetails.getRelease_date());
            rating.setText(Double.toString(sdetails.getVote_average()));
            overview.setText(sdetails.getOverview());

        }
    }
}