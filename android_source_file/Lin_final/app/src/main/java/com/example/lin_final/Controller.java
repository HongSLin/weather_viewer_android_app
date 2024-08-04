//********************************************************
// Hongshen Lin
// Final Project Weather Viewer APP
// Professor Salim Lakhani



// it is the controller of the app
// when new user opens the app, it ask Model to connect to Open Weather API to get Denver's weather
// if the user is a return user, it gets city names from sharePreferences and ask Model to get all weather
//********************************************************
package com.example.lin_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


// in build.gradle implement gson library
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import android.content.Context;
import android.content.SharedPreferences;

// Set global variables

public class Controller extends AppCompatActivity implements Model.WeatherDataCallback, WeatherListener{

    public ArrayList<Weather> weather_list = new ArrayList<Weather>();
    private WeatherViewAdapter weather_view_adapter;
    private Boolean imperial, update;

    // data key and name for sharePreferences
    private static final String PREF_NAME = "userData";
    private static final String ARRAY_KEY = "cityList", UNIT = "unit";
    private ArrayList<String> cityList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        imperial = true;
        update = false;
        Model new_model = new Model("denver", this, this, imperial);

        cityList = loadCityList();


        // if cityList exist, this user is a return user, if not exist, this user is a new user
        // for return user, get all city's weather info
        if(cityList != null )
        {
            for (int i = 0; i < cityList.size(); i++)
            {
                Model get_weather = new Model(cityList.get(i), this, this, imperial);
                get_weather.connect_api(get_weather, get_weather.get_city());
            }
        }
        // for new user, get weather of Denver
        else
        {
            // connect api to get denver's weather
            // waiting for future implementation -> get weather base in user's gps

            new_model.connect_api(new_model, new_model.get_city());
        }


        // useless for now
        // new_model.getWeatherLiveData().observe(this, new Observer<Weather>() {
        //     @Override
        //     public void onChanged(Weather weather) {
        //         // To handle the updated Weather data here
        //
        //         // update the recycler view
        //         weather_view_adapter.notifyDataSetChanged();
        //     }
        // });


        // show the main page
        setContentView(R.layout.view_main);
        // show option menu to allow user to change weather unit
        invalidateOptionsMenu();

    }

    // create option menu that allows user to change unit
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.drop_down_menu, menu);

        return true;
    }

    // Changing the unit from imperial to metric and vice versa
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.change_unit)
        {
            // flip the bool, if it is imperial right now, in Model it calls for metric data
            // if it is metric right now, in Model it calls for imperial data
            imperial = !imperial;
            ArrayList<Weather> temp_weather = new ArrayList<>();
            update = true;

            // update weather data in weather_list
            for (int i = 0; i < weather_list.size(); i++)
            {
                Model new_model = new Model(weather_list.get(i).get_city(), this, this, imperial);
                new_model.connect_api(new_model, new_model.get_city());
            }


            return true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }

    }


    // add button listener on main page view_main xml,
    // open dialog fragment and allow user to enter city name
    public void addWeather(View view)
    {
        update = false;
        AddCityDialog addCityDialog_ref = new AddCityDialog();
        addCityDialog_ref.show(getSupportFragmentManager(), "");

    }


    // refresh button listener, connect to api to get new data
    public void refreshWeather(View view)
    {
        update = true;
        for (int i = 0; i < weather_list.size(); i++)
        {
            Model new_model = new Model(weather_list.get(i).get_city(), this, this, imperial);
            new_model.connect_api(new_model, new_model.get_city());
        }
    }

    // call in AddCityDialog.add_city()
    // to call model to get weather info for this city
    public void callModel(String name)
    {
        Model new_model = new Model(name, this, this, imperial);
        new_model.connect_api(new_model, new_model.get_city());
    }

    // call back method
    // call in Model to add new weather info into array weather_list
    // or update weather info
    public void onWeatherDataReceived(Weather data) {

        // add the unit symbol to current_temp string
        if(imperial)
        {

            data.set_current_temp(data.get_current_temp() + " °F");
        }
        else
        {
            data.set_current_temp(data.get_current_temp() + " °C");
        }

        // if adding new weather
        if(!update)
        {

            data.set_id(weather_list.size()+1);

            weather_list.add(data);

            weather_view_adapter = new WeatherViewAdapter(this,weather_list,this);



            RecyclerView recyclerView = findViewById(R.id.recycler);

            recyclerView.setLayoutManager(new LinearLayoutManager(Controller.this));
            recyclerView.setAdapter(weather_view_adapter);

        }

        // if updating data in weather_list array
        else
        {
            for(int i = 0; i < weather_list.size(); i++)
            {
                if(weather_list.get(i).get_city().equals(data.get_city()))
                {
                    weather_list.get(i).set_weather_detail(data.get_weather_detail());
                    weather_list.get(i).set_current_temp(data.get_current_temp());
                    weather_list.get(i).set_feel_like(data.get_feel_like());
                    weather_list.get(i).set_max_temp(data.get_max_temp());
                    weather_list.get(i).set_min_temp(data.get_min_temp());
                    weather_list.get(i).set_wind_speed(data.get_wind_speed());
                    weather_list.get(i).set_humidity(data.get_humidity());
                    weather_list.get(i).set_icon(data.get_icon());

                }
            }
            weather_view_adapter.notifyDataSetChanged();

        }


    }

    // When the user clicks on a city on main page
    // show them the weather detail of the city
    // pass info to WeatherDetailDialog using Bundle
    public void onWeatherClicked(Weather temp)
    {
        String temp_symbol = "", humid_symbol = " %", wind_symbol = "";
        if(imperial)
        {
            temp_symbol = " °F";
            wind_symbol = " mi/hr";
        }
        else
        {
            temp_symbol = " °C";
            wind_symbol = " m/sec";
        }
        Bundle data = new Bundle();
        data.putInt("id",temp.get_id());
        data.putString("city", temp.get_city());
        data.putString("country", temp.get_country());
        data.putString("weather_detail", temp.get_weather_detail());
        data.putString("current", temp.get_current_temp());
        data.putString("feel", temp.get_feel_like()+temp_symbol);
        data.putString("max", temp.get_max_temp()+temp_symbol);
        data.putString("min", temp.get_min_temp()+temp_symbol);
        data.putString("wind", temp.get_wind_speed()+wind_symbol);
        data.putString("humidity", temp.get_humidity()+" %");
        data.putString("icon", temp.get_icon());

        WeatherDetailDialog weather_dialog = new WeatherDetailDialog();
        weather_dialog.setArguments(data);
        weather_dialog.show(getSupportFragmentManager(), "");

    }


    //delete a city
    // call in WeatherDetailDialog
    public void deleteCity(int id)
    {
        for(int i = 0; i< weather_list.size(); i++)
        {
            if(weather_list.get(i).get_id() == id)
            {
                weather_list.remove(i);
            }
        }
        for(int i = 0; i< weather_list.size(); i++)
        {
            weather_list.get(i).set_id(i+1);
        }
        weather_view_adapter.notifyDataSetChanged();
    }



    // save user data (city name) when on pause
    @Override
    protected void onPause()
    {
        super.onPause();

        if (cityList == null) {
            cityList = new ArrayList<String>();
        }
        else {
            // Clear the existing cities to avoid duplication
            cityList.clear();
        }

        for(int i = 0; i < weather_list.size(); i++)
        {
            cityList.add(weather_list.get(i).get_city());
        }
        saveCityList(cityList);

    }

    // save user data (city name) when on destroy
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (cityList == null) {
            cityList = new ArrayList<String>();
        }
        else
        {
            // Clear the existing cities to avoid duplication
            cityList.clear();
        }
        for(int i = 0; i < weather_list.size(); i++)
        {
            cityList.add(weather_list.get(i).get_city());
        }
        saveCityList(cityList);
    }

    // save user data (city name) when on Stop
    @Override
    protected void onStop()
    {
        super.onStop();
        if (cityList == null) {
            cityList = new ArrayList<String>();
        }
        else
        {
            // Clear the existing cities to avoid duplication
            cityList.clear();
        }
        for(int i = 0; i < weather_list.size(); i++)
        {
            cityList.add(weather_list.get(i).get_city());
        }
        saveCityList(cityList);
    }

    // call in onPause, onStop, onDestroy
    // save city name to json and put it inside sharePreferences
    private void saveCityList(ArrayList<String> city)
    {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(city);

        // save the city names and unit type
        editor.clear();
        editor.putString(ARRAY_KEY, json);
        editor.putBoolean(UNIT, imperial);
        editor.apply();
    }

    // call in onCreate
    // if not new user, get user data (city list) from sharedPreferences
    // get and set unit type as well
    private ArrayList<String> loadCityList() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(ARRAY_KEY, null);
        imperial = sharedPreferences.getBoolean(UNIT, false);

        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        Gson gson = new Gson();

        return gson.fromJson(json, type);
    }


}