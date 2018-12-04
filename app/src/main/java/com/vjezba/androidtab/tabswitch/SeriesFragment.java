package com.vjezba.androidtab.tabswitch;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SeriesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SeriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeriesFragment extends Fragment {
    ListView listView;
    private static Context context = null;





    private OnFragmentInteractionListener mListener;

    public SeriesFragment() {
        // Required empty public constructor
    }

    public static SeriesFragment newInstance(int sectionNumber) {
        SeriesFragment fragment = new SeriesFragment();
        Bundle args = new Bundle();
        args.putInt("2", sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_series, container, false);
        listView = (ListView) result.findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SeriesDetailsActivity.class);
                intent.putExtra("MOVIE_DETAILS", (SeriesDetails) parent.getItemAtPosition(position));
                startActivity(intent);

            }
        });

        new CheckConnectionStatus().execute("https://api.themoviedb.org/3/tv/popular?api_key=0c50af5b835b053f03fdb4730020453e&language=en-US&page=1");

        return result;
    }

    // TODO: Rename method, update argument and hook method into UI event



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    class CheckConnectionStatus extends AsyncTask<String, Void, String> {

        @Override
        protected  void onPreExecute(){
            super.onPreExecute();


        }
        @Override
        protected String doInBackground(String...params){
            URL url = null;
            try{
                url = new URL(params[0]);
            }
            catch(MalformedURLException e) {
                e.printStackTrace();
            }

            try{
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


                InputStream inputStream = urlConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String s = bufferedReader.readLine();
                bufferedReader.close();
                return s;
            }
            catch(IOException e){
                Log.e("Error:",e.getMessage(),e);

            }
            return null;

        }

        @Override
        protected void onPostExecute(String s){

            super.onPostExecute(s);
            JSONObject jsonObject = null;

            try {

                jsonObject = new JSONObject(s);

                ArrayList<SeriesDetails> seriesList = new ArrayList<>();

                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for (int i = 0; i < 10; i++){

                    JSONObject object = jsonArray.getJSONObject(i);
                    SeriesDetails seriesDetails = new SeriesDetails();
                    seriesDetails.setOriginal_title(object.getString("original_name"));
                    seriesDetails.setVote_average(object.getDouble("vote_average"));
                    seriesDetails.setOverview(object.getString("overview"));
                    seriesDetails.setRelease_date(object.getString("first_air_date"));
                    seriesDetails.setPoster_path(object.getString("poster_path"));
                    seriesList.add(seriesDetails);


                }


                SeriesArrayAdapter seriesArrayAdapter = new SeriesArrayAdapter(getContext(),R.layout.series_list,seriesList);
                listView.setAdapter(seriesArrayAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}