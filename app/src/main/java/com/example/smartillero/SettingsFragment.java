package com.example.smartillero;

import android.app.Activity;
import android.app.DownloadManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
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
    Button  BTN, BTN2, button_new;
    Switch switch1;
    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;
    private static final int RESULT_OK = -1;
    Activity act = getActivity();

    // Create a BroadcastReceiver for ACTION_FOUND
  /*  private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver()
    {
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            // When discovery finds a device
            if (action.equals(BTA.ACTION_CONNECTION_STATE_CHANGED))
            {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,BTA.ERROR);
                switch(state)
                {
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "On receive state: OFF ");
                        break;

                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "On receive state: ON ");
                        break;

                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "On receive state: TURNING OFF ");
                        break;

                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "On receive state: TURNING ON ");
                        break;
                }

            }
        }
    };*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_settings,container,false);

        BTN = view.findViewById(R.id.Edit_med);
        BTN2 =view.findViewById(R.id.Erase_med);
        button_new = view.findViewById(R.id.button_new);

        switch1 = view.findViewById(R.id.switch1);

        BTA = BluetoothAdapter.getDefaultAdapter();


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
                //Toast.makeText(getActivity(),"Principal 2", Toast.LENGTH_SHORT).show();
                //EnableDisableBT();
            }
        });

        BTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                EnableDisableBT();
                Toast.makeText(act,"ENTRO", Toast.LENGTH_SHORT).show();
                BTN2.setEnabled(false);

            }
        });

        BTN2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });



        return view;
    }

  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch(requestCode)
        {
            case REQUEST_ENABLE_BT:
                if(resultCode == RESULT_OK)
                {
                    Toast.makeText(getActivity(),"ENCENDIDO",Toast.LENGTH_SHORT).show();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/

      public void EnableDisableBT()
    {
        if(BTA == null)     //If Bluetooth isn't available
        {
            Toast.makeText(getActivity(),"El dispositivo no cuenta con Bluetooth", Toast.LENGTH_SHORT).show();
        }
        if(!BTA.isEnabled()) //Enables BT
        {
            Log.d(TAG, "ENABLE BT");
            Intent EnableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(EnableBT);

            IntentFilter BtIntent = new IntentFilter(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
            //getActivity().registerReceiver(mBroadcastReceiver1,BtIntent);
        }
        if(BTA.isEnabled()) //Disables BT if enabled
        {
            Log.d(TAG, "DISABLE BT");
            BTA.disable();
            IntentFilter BtIntent = new IntentFilter(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
            //getActivity().registerReceiver(mBroadcastReceiver1,BtIntent);
        }
    }
}
