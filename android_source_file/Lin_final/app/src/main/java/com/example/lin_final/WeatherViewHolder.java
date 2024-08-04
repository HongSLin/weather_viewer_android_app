//********************************************************
// Hongshen Lin
// Final Project Weather Viewer APP
// Professor Salim Lakhani



// it is the recycler view holder binding weather_view_holder.xml with recycler
//********************************************************
package com.example.lin_final;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WeatherViewHolder extends RecyclerView.ViewHolder {

    // initialize the holder
    TextView city_view, temp_view, description_view;

    RelativeLayout weather_holder_xml;

    public WeatherViewHolder(@NonNull View itemView) {
        super(itemView);

        weather_holder_xml = itemView.findViewById(R.id.weather_view_holder_xml);
        weather_holder_xml.setClickable(true);

        city_view = itemView.findViewById(R.id.city);
        temp_view = itemView.findViewById(R.id.temperature);
        description_view = itemView.findViewById(R.id.weather_description);


    }
}
