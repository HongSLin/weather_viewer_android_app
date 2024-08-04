//********************************************************
// Hongshen Lin
// Final Project Weather Viewer APP
// Professor Salim Lakhani



// it defines weather object
// weather_list in the controller is an array of weather object
//********************************************************

package com.example.lin_final;

public class Weather {

    private int id;

    private String city;

    private String country;
    private String weather_detail;
    private String current_temp;
    private String feel_like;
    private String max_temp;
    private String min_temp;
    private String wind_speed;
    private String humidity;
    private String icon;

    public Weather(int id, String city, String country, String weather_detail, String current_temp, String feel_like, String max_temp, String min_temp, String wind_speed, String humidity, String icon)
    {
        this.id = 0;
        this.city = city;
        this.country = country;
        this.weather_detail = weather_detail;
        this.current_temp = current_temp;
        this.feel_like = feel_like;
        this.max_temp = max_temp;
        this.min_temp = min_temp;
        this.wind_speed = wind_speed;
        this.humidity = humidity;
        this.icon = icon;
    }

    public int get_id()
    {
        return id;
    }

    public void set_id(int new_id)
    {
        this.id = new_id;
    }

    public String get_city()
    {
        return city;
    }

    public void set_city(String new_city)
    {
        this.city = new_city;
    }

    public String get_country()
    {
        return country;
    }

    public void set_country(String new_country)
    {
        this.country = new_country;
    }
    public String get_weather_detail()
    {
        return weather_detail;
    }

    public void set_weather_detail(String new_weather_detail)
    {
        this.weather_detail = new_weather_detail;
    }
    public String get_current_temp()
    {
        return current_temp;
    }

    public void set_current_temp(String new_current_temp)
    {
        this.current_temp = new_current_temp;
    }
    public String get_feel_like()
    {
        return feel_like;
    }

    public void set_feel_like(String new_feel_like)
    {
        this.feel_like = new_feel_like;
    }
    public String get_max_temp()
    {
        return max_temp;
    }

    public void set_max_temp(String new_max_temp)
    {
        this.max_temp = new_max_temp;
    }
    public String get_min_temp()
    {
        return min_temp;
    }
    public void set_min_temp(String new_min_temp)
    {
        this.min_temp = new_min_temp;
    }
    public String get_wind_speed()
    {
        return wind_speed;
    }
    public void set_wind_speed(String new_wind_speed)
    {
        this.wind_speed = new_wind_speed;
    }

    public String get_humidity()
    {
        return humidity;
    }
    public void set_humidity(String new_humidity)
    {
        this.humidity = new_humidity;
    }
    public String get_icon()
    {
        return icon;
    }
    public void set_icon(String new_icon)
    {
        this.icon = new_icon;
    }
}
