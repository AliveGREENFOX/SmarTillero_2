package com.example.smartillero.ui.notifications;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.smartillero.Edicion;
import com.example.smartillero.R;

import java.util.ArrayList;
import java.util.Set;

public class NotificationsFragment extends Fragment
{
    BluetoothAdapter BTA;
    private static final String TAG = "Settings";
    Button  BTN, BTN2, button_new;
    Switch switch1;


    int index = 0;
    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;
    private static final int RESULT_OK = -1;
    Activity act = getActivity();
    private ListView BTDList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_settings,container,false);

        BTN = view.findViewById(R.id.Edit_med);
        BTN2 =view.findViewById(R.id.Erase_med);
        button_new = view.findViewById(R.id.button_new);

        switch1 = view.findViewById(R.id.switch1);

        BTDList = view.findViewById(R.id.Devices);


        BTA = BluetoothAdapter.getDefaultAdapter(); //Control del adaptador de bluetooth



        /****************************************************************************************************/
        BTN.setEnabled(false);
        BTN2.setEnabled(false);


        /****************************************************************************************************/
        button_new.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Toast.makeText(getActivity(),"Principal 2", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), Edicion.class);
                startActivity(intent);

            }
        });
        /****************************************************************************************************/
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                EnableDisableBT();
            }
        });

        /****************************************************************************************************/

        BTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });
        /****************************************************************************************************/
        BTN2.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                Set<BluetoothDevice> BTD = BTA.getBondedDevices();//Control de los dispositivos conectados al bluetooth
                String[] devs= new String[BTD.size()];

                if(BTD.size()>0)
                {
                    for(BluetoothDevice dc : BTD)
                    {
                        devs[index] = dc.getName();
                        index++;
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,devs);
                BTDList.setAdapter(arrayAdapter);
            }
        });
        return view;
    }

    /****************************************************************************************************/
    public void EnableDisableBT()
    {
        if(BTA == null)     //If Bluetooth isn't available
        {
            Toast.makeText(getActivity(),"El dispositivo no cuenta con Bluetooth", Toast.LENGTH_SHORT).show();
        }
        if(!BTA.isEnabled()) //Enables BT
        {
            BTN.setEnabled(true);
            BTN2.setEnabled(true);
            Log.d(TAG, "ENABLE BT");
            Intent in = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(in,REQUEST_ENABLE_BT);
            Toast.makeText(getActivity(),"ENCENDIENDO BLUETOOTH...",Toast.LENGTH_SHORT).show();
            //Intent EnableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //startActivity(EnableBT);

            //IntentFilter BtIntent = new IntentFilter(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
            //getActivity().registerReceiver(mBroadcastReceiver1,BtIntent);
        }
        if(BTA.isEnabled()) //Disables BT if enabled
        {
            BTN.setEnabled(false);
            BTN2.setEnabled(false);
            Log.d(TAG, "DISABLE BT");
            BTA.disable();
            //IntentFilter BtIntent = new IntentFilter(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
            //getActivity().registerReceiver(mBroadcastReceiver1,BtIntent);
        }
    }
}