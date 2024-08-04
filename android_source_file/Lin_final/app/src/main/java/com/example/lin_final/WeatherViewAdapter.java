//********************************************************
// Hongshen Lin
// Final Project Weather Viewer APP
// Professor Salim Lakhani



// it is the recycler view adapter binding weather_view_holder.xml with recycler
//********************************************************
package com.example.lin_final;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import java.util.ArrayList;

public class WeatherViewAdapter extends RecyclerView.Adapter<WeatherViewHolder> {

    // looking for context to get the data
    Context context;
    ArrayList<Weather> weather_list;

    WeatherListener weatherListener;

    public WeatherViewAdapter(Context context, ArrayList<Weather> weather_list, WeatherListener weatherListener)
    {
        this.context = context;
        this.weather_list = weather_list;
        this.weatherListener = weatherListener;
    }


    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeatherViewHolder(LayoutInflater.from(context).inflate(R.layout.weather_view_holder,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position)
    {
        holder.weather_holder_xml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherListener.onWeatherClicked(weather_list.get(holder.getAdapterPosition()));
            }
        });

        holder.city_view.setText(weather_list.get(position).get_city());
        holder.temp_view.setText(weather_list.get(position).get_current_temp());
        holder.description_view.setText(weather_list.get(position).get_weather_detail());
    }

    @Override
    public int getItemCount() {
        return weather_list.size();
    }
}
