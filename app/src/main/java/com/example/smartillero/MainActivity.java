package com.example.smartillero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "Main activity";
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private CheckBox R_Info;
    private EditText Nombre;
    private EditText Cont;


    @Override
    protected void onCreate(Bundle savedInstanceState) //Main activity
    {
        //*******************APP RUNS ON API 23***************************
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "APP STARTED"); //Tag for Log

        Button Ok_Btn = (Button) findViewById(R.id.Btn_Ok);     //Declare buttons
        Button New_Btn = (Button) findViewById(R.id.Btn_New);
        Nombre = findViewById(R.id.User);
        Cont = findViewById(R.id.Pass);
        R_Info = (CheckBox) findViewById(R.id.RememberInfo);



        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        mEditor.putString("key","");

        CheckSharedPreferences();



        Ok_Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if(R_Info.isChecked())
                {
                    //Pone el checkbox en positivo
                    mEditor.putString(getString(R.string.Checkbox),"True");
                    mEditor.commit();

                    //Guarda el nombre
                    String Nombre_2 = Nombre.getText().toString();
                    mEditor.putString(getString(R.string.Name), Nombre_2);
                    mEditor.commit();

                    //Guarda la contraseña
                    String Cont_2 = Cont.getText().toString();
                    mEditor.putString(getString(R.string.Pass), Cont_2);
                    mEditor.commit();

                    //Log.d(TAG, "BUTTON New");
                   // Toast.makeText(MainActivity.this,"New user", Toast.LENGTH_LONG).show();

                }

                else
                {
                    //Pone el checkbox en positivo
                    mEditor.putString(getString(R.string.Checkbox),"False");
                    mEditor.commit();

                    //Guarda el nombre

                    mEditor.putString(getString(R.string.Name), "");
                    mEditor.commit();

                    //Guarda la contraseña

                    mEditor.putString(getString(R.string.Pass),"");
                    mEditor.commit();
                }


                Log.d(TAG, "BUTTON OK");
                Toast.makeText(MainActivity.this,"Bienvenido", Toast.LENGTH_LONG).show();
                //startActivity(new Intent(MainActivity.this,BasicMenu.class));

                Intent intent = new Intent(MainActivity.this, BasicMenu.class);
                int Fragmento = 1;
                intent.putExtra("Fragmento",Fragmento);
                startActivity(intent);
                finish();
            }
        });

        New_Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {


            }
        });

    }

    private void CheckSharedPreferences()
    {
        String checkbox = mPreferences.getString(getString(R.string.Checkbox),"False");
        String Name = mPreferences.getString(getString(R.string.Name),"");
        String Password = mPreferences.getString(getString(R.string.Pass),"");

        Nombre.setText(Name);
        Cont.setText(Password);

        if(checkbox.equals("True"))
        {
            R_Info.setChecked(true);
        }
        else
        {
            R_Info.setChecked(false);
        }
    }


}
