package com.example.smartillero.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.smartillero.R;

public class DashboardFragment extends Fragment
{
    //Button Izquierda, Derecha, Borrar;
    ImageButton Izquierda, Derecha, Borrar;
    TextView Texto;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        Izquierda = (ImageButton) view.findViewById(R.id.izquierda);
        Derecha = (ImageButton)view.findViewById(R.id.derecha);
        Borrar = (ImageButton)view.findViewById(R.id.borrar);
        Texto = (TextView)view.findViewById(R.id.textView5);

        Izquierda.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Texto.setText("Izquierda");
            }
        });

        Derecha.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Texto.setText("Derecha");
            }
        });

        Borrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Texto.setText("");
            }
        });


        return view;
    }

}