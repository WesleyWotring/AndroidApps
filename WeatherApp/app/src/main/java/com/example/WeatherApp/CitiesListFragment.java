package com.example.WeatherApp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * HW03
 * Group_F22_HW03
 * Wesley Wotring and Kyle Prindle
 */
public class CitiesListFragment extends Fragment {
    ArrayList<Data.City> cityList = new ArrayList<>();
    View view;
    ListView listView;
    CityListAdapter adapter;





    public CitiesListFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CitiesListFragment newInstance() {
        CitiesListFragment fragment = new CitiesListFragment();

        return fragment;
    }


    public CityListFragmentListener listenerCitiesList;
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof CityListFragmentListener){
            listenerCitiesList = (CityListFragmentListener) context;

        } else {
            throw new RuntimeException("Error");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /** if (getArguments() != null) {

        }**/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cities_list, container, false);
        getActivity().setTitle(R.string.cities);
        listView = view.findViewById(R.id.listViewCities);

        for(int i = 0; i < Data.cities.size(); i++){
            cityList.add(Data.cities.get(i));
        }


        adapter = new CityListAdapter(getContext(),R.layout.city_list_layout,cityList);


        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Data.City selectedCity = adapter.getItem(position);
                listenerCitiesList.gotoCity(selectedCity);
            }
        });




        return view;
    }



    public interface CityListFragmentListener{
        void gotoCity(Data.City selectedCity);

    }
}