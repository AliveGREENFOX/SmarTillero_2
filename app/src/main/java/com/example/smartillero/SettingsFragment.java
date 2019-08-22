package com.example.smartillero;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_settings,container,false);

        Switch switch1 = (Switch)view.findViewById(R.id.switch1);
       Button button_new = (Button)view.findViewById(R.id.button_new);





        button_new.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Toast.makeText(getActivity(),"Principal 2", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),Edicion.class);
                startActivity(intent);

            }
        });

        if(switch1.isChecked())
        {
            Toast.makeText(getActivity(),"Principal 2", Toast.LENGTH_SHORT).show();
        }
        //return inflater.inflate(R.layout.fragment_settings, container, false);

        return view;
    }
}
