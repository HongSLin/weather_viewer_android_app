//********************************************************
// Hongshen Lin
// Final Project Weather Viewer APP
// Professor Salim Lakhani



// This dialog fragment allows user to enter a city name and
// call method in Controller to add the city's data in weather_list
// it has simple checking on user input
//********************************************************

package com.example.lin_final;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.lin_final.databinding.AddCityBinding;

public class AddCityDialog extends DialogFragment{
    private AddCityBinding binding;

    private String city, country, weather_detail, current, feel, max, min, wind, humidity, icon;
    private int id;

    public Dialog onCreateDialog (Bundle savedInstanceState)
    {
        binding = AddCityBinding.inflate(LayoutInflater.from(getContext()));

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(binding.getRoot());
        //binding.viewContact.inflateMenu(R.menu.menu_view_contact);

        // add the city
        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_city();
            }
        });

        // go back to main
        binding.buttonMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });




        return builder.create();
    }

    // call call_model in Controller to get weather information of this new city
    // if user does not enter anything, it display a toast message
    private void add_city()
    {
        if(binding.cityName.getText().toString().trim().length() > 0)
        {
            Controller controller_ref = (Controller) getActivity();
            controller_ref.callModel(binding.cityName.getText().toString());

            dismiss();
        }
        else
        {
            Toast.makeText(getContext(), "Please enter a city", Toast.LENGTH_LONG).show();
        }
    }
}
