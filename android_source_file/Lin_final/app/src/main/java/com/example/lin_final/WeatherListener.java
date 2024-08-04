//********************************************************
// Hongshen Lin
// Final Project Weather Viewer APP
// Professor Salim Lakhani



// it is an interface for opening WeatherDetailDialog
// When the user click on a city on main page
// it launches on_weather_clicked in the Controller
//********************************************************
package com.example.lin_final;

public interface WeatherListener {
    void onWeatherClicked(Weather temp);
}
