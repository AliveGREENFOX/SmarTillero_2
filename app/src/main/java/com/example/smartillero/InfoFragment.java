package com.example.smartillero;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class InfoFragment extends Fragment
{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings,container,false);
       // return inflater.inflate(R.layout.fragment_info, container, false);

      /*  ListView Lv;
        ArrayAdapter adapter;
        String[] St = {"1", "2", "3", "4", "5"};

        Lv = (ListView)view.findViewById(R.id.List1);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, St);

        Lv.setAdapter(adapter);*/


        return view;
    }

}