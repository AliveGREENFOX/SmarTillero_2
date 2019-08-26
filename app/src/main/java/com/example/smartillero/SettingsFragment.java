package com.example.smartillero;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsFragment extends Fragment
{
    BluetoothAdapter BTA;
    private static final String TAG = "Settings";
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

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                Toast.makeText(getActivity(),"Principal 2", Toast.LENGTH_SHORT).show();
                //EnableDisableBT();
            }
        });

        return view;
    }

    public void EnableDisableBT()
    {
        if(BTA == null)     //If Bluetooth isn't available
        {
            Toast.makeText(getActivity(),"El dispositivo no cuenta con Bluetooth", Toast.LENGTH_SHORT).show();
        }
        if(!BTA.isEnabled()) //Enables BT
        {
            Intent EnableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(EnableBT);
        }

        if(BTA.isEnabled()) //Disables BT if enabled
        {
            BTA.disable();
            IntentFilter BTintent = new IntentFilter(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
        }

    }
}
