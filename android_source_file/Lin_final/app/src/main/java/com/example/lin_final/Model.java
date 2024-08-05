//********************************************************
// Hongshen Lin
// Final Project Weather Viewer APP
// Professor Salim Lakhani



// it connects to Open Weather API using my personal key
// and the city name given by the Controller
// once it has the data back, it invoke call back method in the Controller onWeatherDataReceived
// to add/update the new data into weather_list(declare in Controller)
//********************************************************
package com.example.lin_final;


import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;

import java.util.ArrayList;

public class Model{
    // expired key, change it when you run the program
    private String api = "https://api.openweathermap.org/data/2.5/weather?appid=6a2b07954fc26972c448d0a43ae01cee";
    private String prefix_city = "&q=";
    private String city;
    private String unit;

    // to use volley library, we need to context object
    private Context context;

    private Boolean imperial_local;


    // not implemented
    private MutableLiveData<Weather> weatherLiveData = new MutableLiveData<>();
    public LiveData<Weather> getWeatherLiveData() {
        return weatherLiveData;
    }


    private WeatherDataCallback callback;
    public interface WeatherDataCallback {
        void onWeatherDataReceived(Weather weather);
    }

    // setters and getters
    public void set_city(String new_city)
    {
        this.city = new_city;
    }
    public void set_api(String new_api)
    {
        this.api = new_api;
    }

    public String get_api()
    {
        return api;
    }
    public String get_prefix_city()
    {
        return prefix_city;
    }
    public String get_city()
    {
        return city;
    }
    public String get_unit()
    {
        return unit;
    }
    public Context get_context()
    {
        return context;
    }

    // constructor
    public Model(String city, Context context, WeatherDataCallback callback, Boolean imperial_local)
    {

        this.city = city;

        this.context = context;
        this.callback = callback;
        this.imperial_local = imperial_local;
    }

    // function that connects to API
    public void connect_api(Model temp_object, String city_name)
    {
        // constructing the api address
        if(imperial_local)
        {
            unit= "&units=imperial";
        }
        else
        {
            unit= "&units=metric";
        }
        String new_api = temp_object.get_api() + temp_object.get_unit() + temp_object.get_prefix_city() + city_name;

        // connect to Open Weather with GET method
        RequestQueue weather_request_queue = Volley.newRequestQueue(temp_object.get_context());
        StringRequest request_weather = new StringRequest(Request.Method.GET, new_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d("weather_data", response);
                int id = 1, humid;
                Double temp, max, min, feel, wind;
                String city, country, weather_detail, icon;

                // if retrieve data successfully
                // parse data from JSON into local variables
                // and passes these variables back to the Controller
                try {
                    // parsing json arrays
                    JSONObject jsonResponse = new JSONObject(response);


                    //JSONObject name_array = jsonResponse.getJSONObject("name");
                    JSONObject sys_array = jsonResponse.getJSONObject("sys");
                    JSONArray array = jsonResponse.getJSONArray("weather");
                    JSONObject weather_array = array.getJSONObject(0);
                    JSONObject main_array = jsonResponse.getJSONObject("main");
                    JSONObject wind_array = jsonResponse.getJSONObject("wind");


                    // store data to local variables
                    city = jsonResponse.getString("name");
                    country = sys_array.getString("country");
                    weather_detail = weather_array.getString("description");
                    temp = main_array.getDouble("temp");
                    feel = main_array.getDouble("feels_like");
                    max = main_array.getDouble("temp_max");
                    min = main_array.getDouble("temp_min");
                    wind = wind_array.getDouble("speed");
                    humid = main_array.getInt("humidity");
                    icon = weather_array.getString("icon");


                    // save data
                    // construct a Weather object
                    // Weather defines in Weather class
                    Weather temp_list = new Weather(id, city, country, weather_detail, Double.toString(temp), Double.toString(feel),
                            Double.toString(max), Double.toString(min), Double.toString(wind), Integer.toString(humid), icon);

                    // not implemented
                    weatherLiveData.postValue(temp_list);

                    // using call back method to save data because of asynchronous nature of API calls
                    if (temp_object.callback != null) {
                        callback.onWeatherDataReceived(temp_list);
                    }

                }

                // if cannot parse data
                catch (JSONException e) {
                    Log.d("JSONsuccess", "It didnt work, trycatch");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // if user input is invalid
                // for example city does not exist
                // display error message with toast
                if(city_name.length()==0)
                {
                    Toast.makeText(temp_object.get_context(), "Please enter city name", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(temp_object.get_context(), "City Does Not Exist", Toast.LENGTH_SHORT).show();
                }

            }
        });

        weather_request_queue.add(request_weather);


    }


}
