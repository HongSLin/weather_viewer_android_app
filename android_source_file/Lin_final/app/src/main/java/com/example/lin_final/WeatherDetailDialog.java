//********************************************************
// Hongshen Lin
// Final Project Weather Viewer APP
// Professor Salim Lakhani



// it shows weather detail using Bundle provided by the Controller
// including city name, weather, current temperature, max, min temperature, feel-like temperature
// humidity, wind speed, and weather icon

// This fragment has two buttons, one is go back to main page
// another one is delete this city from weather_list
//********************************************************
package com.example.lin_final;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import com.example.lin_final.databinding.WeatherDetailBinding;

public class WeatherDetailDialog extends DialogFragment {

    private WeatherDetailBinding binding;

    private String city, country, weather_detail, current, feel, max, min, wind, humidity, icon;
    private int id;

    public Dialog onCreateDialog (Bundle savedInstanceState)
    {
        binding = WeatherDetailBinding.inflate(LayoutInflater.from(getContext()));

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(binding.getRoot());
        //binding.viewContact.inflateMenu(R.menu.menu_view_contact);

        // passing data from main activity on_name_clicked function
        Bundle pass_data = getArguments();
        // save data to local variables
        id = pass_data.getInt("id");
        city = pass_data.getString("city");
        country = pass_data.getString("country");
        weather_detail = pass_data.getString("weather_detail");
        current = pass_data.getString("current");
        feel = pass_data.getString("feel");
        max = pass_data.getString("max");
        min = pass_data.getString("min");
        wind = pass_data.getString("wind");
        humidity = pass_data.getString("humidity");
        icon = pass_data.getString("icon");


        // setting up data for user to view
        String temp = "d" + icon;
        int image_id = getResources().getIdentifier(temp, "drawable", getContext().getPackageName());
        binding.weatherIcon.setImageResource(image_id);
        Log.d("weathersize666", temp);

        temp = city + " weather";
        binding.cityText.setText(temp);
        binding.currentText.setText(current);
        binding.weatherDetail.setText(weather_detail);
        binding.feelText.setText(feel);
        binding.maxText.setText(max);
        binding.minText.setText(min);
        binding.humidityText.setText(humidity);
        binding.windText.setText(wind);


        // back button
        binding.buttonMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // delete button
        binding.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_city();
            }
        });




        return builder.create();
    }

    // If the user clicks the delete button
    // pass the id of the city to controller.delete_city()
    // and dismiss this fragment
    public void delete_city()
    {
        Controller controller_ref = (Controller) getActivity();
        controller_ref.deleteCity(id);
        dismiss();
    }

}
