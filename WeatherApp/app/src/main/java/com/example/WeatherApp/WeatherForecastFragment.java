package com.example.WeatherApp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * HW03
 * Group_F22_HW03
 * Wesley Wotring and Kyle Prindle
 */
public class WeatherForecastFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    Data.City selectedCity;
    View view;
    ListView fiveDayList;
    TextView city;
    String key = "be8d298d3a515eca1263a7351910bce5";
    private final OkHttpClient client = new OkHttpClient();



    // TODO: Rename and change types of parameters


    public WeatherForecastFragment() {
        // Required empty public constructor
    }


    public static WeatherForecastFragment newInstance(Data.City selectedCity) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        fragment.selectedCity = selectedCity;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_weather_forecast, container, false);
        getActivity().setTitle(R.string.weather_forecast);

        city = view.findViewById(R.id.textViewCityName);
        city.setText(selectedCity.getCity() + ", " + selectedCity.getCountry());

        fiveDayList = view.findViewById(R.id.listView5Day);
        












        return view;
    }



    void load5Day(){


        String url = "https://api.openweathermap.org/data/2.5/forecast?q="+ selectedCity.getCity() + "&appid=" + key;
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });


    }
}

