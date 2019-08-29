package com.example.smartillero;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment
{
    private ListView Alarms;
    Button Med1, Med2, Med3;
    TextView Texto2;
    EditText pruebaFB;
     private DatabaseReference Smart;

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

///////////////////////////////////////////////////////

        RecoverData();

///////////////////////////////////////////////////////////

        final ArrayAdapter Arrayadapter;
        Alarms = (ListView)view.findViewById(R.id.AlarmList); //Lista para mostrar las alarmas
        Arrayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,MedsName);

        if(MedsName != null && MedsName.size() > 0)
        {
            Alarms.setAdapter(Arrayadapter);
        }

      /*  Med1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });*/
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
                String value = dataSnapshot.getValue(String.class);
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

}
