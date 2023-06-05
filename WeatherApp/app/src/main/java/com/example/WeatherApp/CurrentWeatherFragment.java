package com.example.WeatherApp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * HW03
 * Group_F22_HW03
 * Wesley Wotring and Kyle Prindle
 */
public class CurrentWeatherFragment extends Fragment {
    Data.City data;
    View view;
    TextView temp, tempMax, tempMin, desc, humidity, windSpd, windDeg, cloudiness, cityName;
    Button buttonChkFore;
    private static final String ARG_DATA = "ARG_DATA";
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appId = "def387126b70d2de28578211ef153d30";
    String cityCity, country, tempURL;
    DecimalFormat df = new DecimalFormat("#.##");
    private final OkHttpClient client = new OkHttpClient();
    ImageView image;
    final String  TAG = "DEMO";





    public CurrentWeatherFragment() {
        // Required empty public constructor
    }


    public static CurrentWeatherFragment newInstance(Data.City data) {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        fragment.data = data;
         Bundle args = new Bundle();
         args.putSerializable(ARG_DATA, data);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
          /**  mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);**/
            data = (Data.City) getArguments().getSerializable(ARG_DATA);
        }


    }

    public CurrentWeatherFragment.WeatherFragmentListener listenerCurrent;
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof CitiesListFragment.CityListFragmentListener){
            listenerCurrent = (CurrentWeatherFragment.WeatherFragmentListener) context;

        } else {
            throw new RuntimeException("Error");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_current_weather, container, false);
        getActivity().setTitle(R.string.current_weather);

        cityName = view.findViewById(R.id.tvWeather);
        cityName.setText(data.getCity() + ", " + data.getCountry());

        temp = view.findViewById(R.id.textViewTemp);
        tempMax = view.findViewById(R.id.textViewTempMax);
        tempMin = view.findViewById(R.id.textViewTempMin);
        desc = view.findViewById(R.id.textViewDesc);
        humidity = view.findViewById(R.id.textViewHumidity);
        windSpd = view.findViewById(R.id.textViewWindSpd);
        windDeg = view.findViewById(R.id.textViewWindDeg);
        cloudiness = view.findViewById(R.id.textViewCloudiness);
        buttonChkFore = view.findViewById(R.id.buttonCheckForecast);
        image = view.findViewById(R.id.ivImage);

        loadData();


        buttonChkFore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerCurrent.sendWeather(data);
            }
        });


        return view;
    }
    public interface WeatherFragmentListener{
        void sendWeather(Data.City data);

    }

    void loadData(){

        tempURL = url + "?q="+ data.getCity()  + "&units=imperial" + "&appid=" + appId;
        Request request = new Request.Builder()
                .url(tempURL)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            if(response.isSuccessful()){

                String resultHolder = "";
                String descriptionHold = "";
                String iconHolder = "";
                String temperatureHolder = "";
                String tempMaxHolder = "";
                String tempMinHolder = "";
                String humidityHolder = "";
                String windSpeedHolder = "";
                String windDegHolder = "";
                String cloudinessHolder = "";



                try{
                    JSONObject responseJSON = new JSONObject(response.body().string());
                    JSONArray arrayJSON = responseJSON.getJSONArray("weather");
                    JSONObject weatherObjJSON = arrayJSON.getJSONObject(0);
                    String description = weatherObjJSON.getString("description");
                    String icon = weatherObjJSON.getString("icon");

                    JSONObject jsonObjectMain = responseJSON.getJSONObject("main");
                    double temp = jsonObjectMain.getDouble("temp");
                    double tempMax = jsonObjectMain.getDouble("temp_max");
                    double tempMin = jsonObjectMain.getDouble("temp_min");
                    int humidity = jsonObjectMain.getInt("humidity");

                    JSONObject jsonObjectWind = responseJSON.getJSONObject("wind");
                    String wind = jsonObjectWind.getString("speed");
                    String deg = jsonObjectWind.getString("deg");

                    JSONObject jsonObjectClouds = responseJSON.getJSONObject("clouds");
                    String clouds = jsonObjectClouds.getString("all");

                    descriptionHold += description;
                    temperatureHolder += String.valueOf(temp);
                    tempMaxHolder += String.valueOf(tempMax);
                    tempMinHolder += String.valueOf(tempMin);
                    humidityHolder += String.valueOf(humidity);
                    windSpeedHolder += wind;
                    windDegHolder += deg;
                    cloudinessHolder += clouds;
                    iconHolder += icon;



                }catch(Exception whatever){
                whatever.printStackTrace();
                }


                final String descriptionFinal = descriptionHold;
                final String tempFinal = temperatureHolder;
                final String tempMaxFinal = tempMaxHolder;
                final String tempMinFinal = tempMinHolder;
                final String humidityFinal = humidityHolder;
                final String windSpdFinal = windSpeedHolder;
                final String windDegFinal = windDegHolder;
                final String cloudFinal = cloudinessHolder;


                Log.d(TAG,"Description " + descriptionFinal);
                Log.d(TAG,"Temp " + tempFinal);
                Log.d(TAG,"TempMax " + tempMaxFinal);
                Log.d(TAG,"TempMin " + tempMinFinal);
                Log.d(TAG,"Humididty " + humidityFinal);
                Log.d(TAG,"Wind Speed " + windSpdFinal);
                Log.d(TAG,"Wind Degree " + windDegFinal);
                Log.d(TAG,"Cloudiness " + cloudFinal);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    desc.setText(descriptionFinal);
                    temp.setText(tempFinal + " F");
                    tempMax.setText(tempMaxFinal + " F");
                    tempMin.setText(tempMinFinal + " F");
                    windSpd.setText(windSpdFinal + " miles/hr");
                    windDeg.setText(windDegFinal + " degrees");
                    humidity.setText(humidityFinal + " %");
                    cloudiness.setText(cloudFinal + "%");


                    desc.setVisibility(View.VISIBLE);
                    temp.setVisibility(View.VISIBLE);
                    tempMax.setVisibility(View.VISIBLE);
                    tempMin.setVisibility(View.VISIBLE);
                    windSpd.setVisibility(View.VISIBLE);
                    windDeg.setVisibility(View.VISIBLE);
                    humidity.setVisibility(View.VISIBLE);
                    cloudiness.setVisibility(View.VISIBLE);


                    }
                });
            }
            else{
                ResponseBody responseBody = response.body();
                String body = responseBody.string();

            }
            }
        });

    }
}