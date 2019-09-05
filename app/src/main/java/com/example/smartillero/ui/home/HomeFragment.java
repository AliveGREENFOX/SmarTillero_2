package com.example.smartillero.ui.home;

import android.app.AlarmManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.smartillero.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HomeFragment extends Fragment
{

    private ListView Alarms;
    Button Med1, Med2, Med3;
    TextView Texto2;
    EditText pruebaFB;
    private DatabaseReference Smart;
    private TimePicker clock;
    private AlarmManager alarm;
    public static ArrayList<String> MedsName = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main,container,false);

        Smart = FirebaseDatabase.getInstance().getReference("Medicamentos");

        MedsName = new ArrayList<>();
        Med1 = (Button)view.findViewById(R.id.Med1);
        Med2 = (Button)view.findViewById(R.id.Med2);
        Med3 = (Button)view.findViewById(R.id.Med3);

        Texto2 =(TextView)view.findViewById(R.id.textView2);
        pruebaFB =(EditText)view.findViewById(R.id.editText3);
        clock = (TimePicker)view.findViewById(R.id.simpleTimePicker);




///////////////////////////////////////////////////////

        RecoverData();
        //Readfromdatabase();

///////////////////////////////////////////////////////////

        final ArrayAdapter Arrayadapter;
        Alarms = (ListView)view.findViewById(R.id.AlarmList); //Lista para mostrar las alarmas
        Arrayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,MedsName);

        if(MedsName != null && MedsName.size() > 0)
        {
            Alarms.setAdapter(Arrayadapter);
        }

        Med1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Toast.makeText(getContext(), clock.getCurrentHour())
                String hora = clock.getCurrentHour().toString();
                //Texto2.setText(hora);

                Smart.child("Erika BB").addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });



            }
        });
        Med2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //1. Creamos un child en la ruta del obejeto
                //2. Asignamos un valor al child

                // Smart.child("Medicamentos").child("Algo").setValue("Ericka");

                String prueba = pruebaFB.getText().toString();
                Smart.setValue(prueba);

                Readfromdatabase();

            }
        });

        return view;
        }

    public void RecoverData()
    {
        SharedPreferences SP = this.getActivity().getSharedPreferences("SharedPrefs", getActivity(). MODE_PRIVATE);
        Gson gson = new Gson();
        String json = SP.getString("TaskList", null);

        Type TP = new TypeToken<ArrayList<String>>(){}.getType();
        MedsName = gson.fromJson(json, TP);
    }


    public void Readfromdatabase()
    {
        // Read from the database
        Smart.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue(String.class);

                String value = dataSnapshot.child("Erika BB").getValue().toString();
                Log.d("Valor:", "Value is: " + value);

                Texto2.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error)
            {
                // Failed to read value
                Log.w("Valor:", "Failed to read value.", error.toException());
            }
        });
    }
   /* private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }*/
}