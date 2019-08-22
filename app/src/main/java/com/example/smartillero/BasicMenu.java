package com.example.smartillero;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class BasicMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    //FragmentManager fm  = getSupportFragmentManager();
    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fm.beginTransaction();
    Fragment fragment = fm.findFragmentById(R.id.Scenary_2);
    int Caso = 0;

    //ListView Lv;
    //ArrayAdapter adapter;
    //String[] St = {"1", "2", "3", "4", "5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) //Inicializa la actividad, relaciona layout con actividad
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        int IntentFragment = getIntent().getExtras().getInt("Fragmento");

        if(IntentFragment ==  1)
        {

            fm.beginTransaction().replace(R.id.Scenary_2, new MainFragment()).addToBackStack("Home").commit();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.basic_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Info", Toast.LENGTH_SHORT).show();
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        if(id == R.id.nav_timer)
        {
            fm.beginTransaction().replace(R.id.Scenary_2, new MainFragment()).addToBackStack("Home").commit();
        }

        else if (id == R.id.nav_info)
        {
            Caso = 1;
            fm.beginTransaction().replace(R.id.Scenary_2, new InfoFragment()).addToBackStack("Home").commit();
            Toast.makeText(this, "Info", Toast.LENGTH_SHORT).show();
            // Handle the camera action
        }

        else if (id == R.id.nav_stats)
        {
            Caso = 2;
            fm.beginTransaction().replace(R.id.Scenary_2, new statsfragment()).addToBackStack("Home").commit();
            Toast.makeText(this, "Stats", Toast.LENGTH_SHORT).show();
        }

        else if (id == R.id.nav_settings)
        {
            Caso = 3;
            //startActivity(new Intent(BasicMenu.this, BasicMenu.class));
            fm.beginTransaction().replace(R.id.Scenary_2, new SettingsFragment()).addToBackStack("Home").commit();
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed()
    {

        //fm.findFragmentById(R.id.Scenary);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }

        else
        {
           // if (fm != null)
           // {
              //  fragmentTransaction = fm.beginTransaction();
              //  fragmentTransaction.remove(fragment);
              //  fragmentTransaction.commit();
          //  }

           // else
           // {
                super.onBackPressed();
           // }
        }
    }
}
