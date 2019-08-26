package com.example.smartillero;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.Transliterator;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Edicion extends AppCompatActivity
{

    public static ArrayList<String> Medicamentos = new ArrayList<>();
    public static ArrayList<Integer> Numero_Meds = new ArrayList<>();
    private ListView Meds;

    public static ArrayList<String> Medicamentos_Temporal = new ArrayList<>(); //ARRAY DE PRUEBA
    public static ArrayList<Integer>MedsTimes = new ArrayList<>(); //Array de horas entre cada toma

    int Add_Flag = 0;

    EditText Med, Num_Med, MedHours;
    //String Meds_Temporal[];
    Button Add, Save;
    int Bandera_fragmento = 1;
    SharedPreferences SP;
    private DatabaseReference Smart;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Smart = FirebaseDatabase.getInstance().getReference("Medicamentos");
        Add = (Button)findViewById(R.id.AddMed);
        Save = (Button)findViewById(R.id.save_button);

        Med = (EditText)findViewById(R.id.InputMed);
        Num_Med = (EditText)findViewById(R.id.InputNumMed);
        MedHours = (EditText)findViewById(R.id.InputMedsTime);

        Meds = (ListView)findViewById(R.id.MedsList);
        final ArrayAdapter AA;

        AlertDialog.Builder Alerta = new AlertDialog.Builder(this);
        Alerta.setCancelable(true); Alerta.setTitle("Atención"); Alerta.setMessage("Desea eliminar el medicamento?");





        RecoverData(); //Cada vez que guardamos un dato lo recuperamos en el array de prueba "MEDS_NAME"

        AA = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,Medicamentos);
        Meds.setAdapter(AA);

/////////////////////////////////////// Borra y seleccion del listview ///////////////////////////////////////////////////////////////////////////

        Meds.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                //String posicion = Meds.getItemAtPosition(i).toString();

                String posicion = Medicamentos.get(i);
                Medicamentos.remove(i);
                SaveData();
                AA.notifyDataSetChanged();
                Toast.makeText(Edicion.this, posicion, Toast.LENGTH_LONG).show();
            }
        });

////////////////////////////////// Habilita y deshabilita los botones de guardado /////////////////////////////////////////////////////////

        Med.addTextChangedListener(medsTextWatcher);
        Num_Med.addTextChangedListener(medsTextWatcher);
        MedHours.addTextChangedListener(medsTextWatcher);

/////////////////////////// Guarda los valores en el listview ///////////////////////////////////////////////////////////////

        Add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                RegisterMeds();
                Add_Flag = Add_Flag + 1;
                String txt = Med.getText().toString().trim();
                String txt_num = Num_Med.getText().toString().trim();
                String txt_Hours = MedHours.getText().toString().trim();

                int Txt_num = Integer.parseInt(txt_num); //Convertimos el texto entrante a entero
                int Txt_Hours = Integer.parseInt(txt_Hours);

                if (txt.length() !=0 && txt_num.length() != 0)
                {
                    Medicamentos.add(txt);
                    //Medicamentos_Temporal.add(txt);
                    Med.setText("");

                    Numero_Meds.add(Txt_num);
                    Num_Med.setText("");

                    MedsTimes.add(Txt_Hours);
                    MedHours.setText("");

                    AA.notifyDataSetChanged();
                }
            }
        });
/////////////////////////////////// Guarda los valores y los pasa a la próxima actividad ///////////////////////////////////////////////////////////////

                Save.setEnabled(Add_Flag > 0);
                Save.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                      //  SharedPreferences.Editor Borrar = SP.edit();
                      //  Borrar.remove("TaskList");
                      //  Borrar.commit();

                        SaveData();
                        Intent intent = new Intent(Edicion.this, BasicMenu.class);
                        intent.putExtra("Meds", Medicamentos);
                        intent.putExtra("Fragmento", Bandera_fragmento);
                        startActivity(intent);
                    }
                });
/////////////////////////// /////////////////////////////////////////////////////////////////////////
    }

    public void RegisterMeds()  //Sends information to firebase
    {
        //String name = "Ericka";
        //String nMeds = "2";
        //String tDay = "3";
        //String id = Smart.push().getKey();
       String name = Med.getText().toString();
       String nMeds = Num_Med.getText().toString();
       String tDay = MedHours.getText().toString();
       String id = Smart.push().getKey();

        Medicamentos Receta = new Medicamentos(id,name,nMeds,tDay);
        Smart.child("Pacientes").child(id).setValue(Receta);


    }

    public void SaveData()
    {
        SP = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor SPE = SP.edit();
        Gson gson = new Gson(); //variable del nombre del medicamento
        String json = gson.toJson(Medicamentos);
        SPE.putString("TaskList", json);
        SPE.apply();

       /* SharedPreferences SP_2 = getSharedPreferences("SharedCuantities",MODE_PRIVATE);
        SP_2.edit();
        Gson gson_2 = new Gson(); //variable para el numero de tomas
        String json_2 = gson_2.toJson(Numero_Meds);
        SPE.putString("Cantidades",json_2);

        SharedPreferences SP_3 = getSharedPreferences("SharedHours",MODE_PRIVATE);
        SP_3.edit();
        Gson gson_3 = new Gson(); //variable para las horas entre tomas
        String json_3 = gson_3.toJson(MedsTimes);
        SPE.putString("Horas", json_3);*/

        SPE.apply();
    }

    public void RecoverData()
    {
        SharedPreferences SP = this.getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = SP.getString("TaskList", null);
        Type TP = new TypeToken<ArrayList<String>>(){}.getType();
        Medicamentos = gson.fromJson(json, TP);

       /* SharedPreferences SP_2 = this.getSharedPreferences("SharedCuantities", MODE_PRIVATE);
        Gson gson_2 = new Gson(); //variable para el numero de tomas
        String json_2 = SP.getString("Cantidades", null);
        Type TP_2 = new TypeToken<ArrayList<String>>(){}.getType();
        Numero_Meds = gson_2.fromJson(json_2,TP_2);

        SharedPreferences SP_3 = getSharedPreferences("SharedHours",MODE_PRIVATE);
        Gson gson_3 = new Gson();
        String json_3 = SP.getString("Horas", null);
        Type TP_3 = new TypeToken<ArrayList<String>>(){}.getType();
        MedsTimes = gson_3.fromJson(json_3,TP_3);*/
    }

    private TextWatcher medsTextWatcher = new TextWatcher() //Evita que este activo el boton de guardado sin texto
    {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {
            String MedsInput = Med.getText().toString().trim();
            String NumMedsInput = Num_Med.getText().toString().trim();
            String HoursMedsInput = MedHours.getText().toString().trim();

            Add.setEnabled(!MedsInput.isEmpty() && !NumMedsInput.isEmpty() && !HoursMedsInput.isEmpty());
            Save.setEnabled(Add_Flag > 0);
        }

        @Override
        public void afterTextChanged(Editable editable) { }
    };

}
