package com.example.WeatherApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * HW03
 * Group_F22_HW03
 * Wesley Wotring and Kyle Prindle
 */
public class MainActivity extends AppCompatActivity implements CitiesListFragment.CityListFragmentListener, CurrentWeatherFragment.WeatherFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerView, new CitiesListFragment(), "start")
                .commit();


    }


    @Override
    public void gotoCity(Data.City data) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, CurrentWeatherFragment.newInstance(data), "citySelected")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void sendWeather(Data.City data) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, WeatherForecastFragment.newInstance(data), "5day")
                .addToBackStack(null)
                .commit();
    }
}