package com.example.smartillero.ui.notifications;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class NotificationsFragment extends Fragment
{
    BluetoothAdapter BTA;
    BluetoothSocket BTS;
    static final UUID myuuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private static final String TAG = "Settings";
    Button  BTN, BTN2, button_new;
    Switch switch1;
    String address;

    int index = 0;
    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;
    private static final int RESULT_OK = -1;
    private boolean connection_succes = true;
    Activity act = getActivity();
    private ListView BTDList;
    String[] devs;
    public static ArrayList<String>directions = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_settings,container,false);

        //BTN = view.findViewById(R.id.Edit_med);
        BTN = view.findViewById(R.id.prueba);
        BTN2 =view.findViewById(R.id.Erase_med);
        button_new = view.findViewById(R.id.button_new);

        switch1 = view.findViewById(R.id.switch1);

        BTDList = view.findViewById(R.id.Devices);


        BTA = BluetoothAdapter.getDefaultAdapter(); //Control del adaptador de bluetooth


        /****************************************************************************************************/
        if(BTA.isEnabled())
        {
            BTN.setEnabled(true);
            BTN2.setEnabled(true);
            switch1.setChecked(true);
        }
        else
        {
            BTN.setEnabled(false);
            BTN2.setEnabled(false);
            switch1.setChecked(false);
        }

         Set<BluetoothDevice> BTD = BTA.getBondedDevices();//Control de los dispositivos conectados al bluetooth
                //String[] devs= new String[BTD.size()];
                devs = new String[BTD.size()];
                if(BTD.size()>0)
                {
                    for(BluetoothDevice dc : BTD)
                    {
                        //directions.add(dc.getName());
                        directions.add(dc.getName() + "\n" + dc.getAddress());
                        //devs[index] = dc.getName();
                        //index++;
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,directions);
                BTDList.setAdapter(arrayAdapter);
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
                connect();
            }
        });
        /****************************************************************************************************/
        BTN2.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                if (BTS!=null)
                {
                    try
                    {
                        BTS.getOutputStream().write("1".toString().getBytes());
                    }
                    catch (IOException e)
                    {
                        Toast.makeText(getActivity(),"Error", Toast.LENGTH_SHORT).show();
                    }
                }

                //Set<BluetoothDevice> BTD = BTA.getBondedDevices();//Control de los dispositivos conectados al bluetooth

               /* Set<BluetoothDevice> BTD = BTA.getBondedDevices();//Control de los dispositivos conectados al bluetooth
                //String[] devs= new String[BTD.size()];
                devs = new String[BTD.size()];
                if(BTD.size()>0)
                {
                    for(BluetoothDevice dc : BTD)
                    {
                        devs[index] = dc.getName();
                        index++;
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,devs);
                BTDList.setAdapter(arrayAdapter);*/

            }
        });

       /* Set<BluetoothDevice> BTD = BTA.getBondedDevices();//Control de los dispositivos conectados al bluetooth
        //String[] devs= new String[BTD.size()];
        devs = new String[BTD.size()];
        if(BTD.size()>0)
        {
            for(BluetoothDevice dc : BTD)
            {
                devs[index] = dc.getName();
                index++;
            }
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,devs);
        BTDList.setAdapter(arrayAdapter);*/
        /****************************************************************************************************/

        BTDList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                String device = directions.get(i);
                String info = ((TextView) view).getText().toString();
                address = info.substring(info.length() - 17);
                //Toast.makeText(getActivity(),address, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    /****************************************************************************************************/
    public void connect()
    {
        try
        {
            if(BTS == null || !BTA.isEnabled())
            {
                BluetoothDevice BTD = BTA.getRemoteDevice(address);
                BTS = BTD.createInsecureRfcommSocketToServiceRecord(myuuid);
                BTA.cancelDiscovery();
                BTS.connect();
            }
        }

        catch (IOException e)
        {
            connection_succes = false;
        }

        if (!connection_succes)
        {
            Toast.makeText(getActivity(),"Conexión fallida", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getActivity(),"Dispositivo conectado", Toast.LENGTH_SHORT).show();
        }

    }

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