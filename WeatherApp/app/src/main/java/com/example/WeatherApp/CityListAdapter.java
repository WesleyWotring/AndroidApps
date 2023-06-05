package com.example.WeatherApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * HW03
 * Group_F22_HW03
 * Wesley Wotring and Kyle Prindle
 */
public class CityListAdapter extends ArrayAdapter<Data.City> {

    public CityListAdapter(@NonNull Context context, int resource, @NonNull List<Data.City> objects) {
        super(context, resource, objects);
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.city_list_layout, parent, false);
        }
        Data.City city = getItem(position);

        TextView cityName = convertView.findViewById(R.id.tvWeatherCity);

        cityName.setText(city.getCity() + ", " + city.getCountry());

        return convertView;
    }
}
