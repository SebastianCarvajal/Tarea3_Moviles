package com.example.login_navigationvew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    Bundle b = new Bundle();
    CircleImageView circleImageView;

    DrawerLayout drawerLayout;
    NavigationView navView;
    TextView txt_usuario;

    Toolbar toolbar1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        b = this.getIntent().getExtras();

        toolbar1 = findViewById(R.id.toolbar_admin);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        navView = findViewById(R.id.nav_viewadmin);
        Menu m = navView.getMenu();
        if(b.getString("tipo").equals("A")){
            m.findItem(R.id.Umenu_section_2).setTitle("Consumo de Clientes");
            m.findItem(R.id.Umenu_section_3).setTitle("Clientes");

        }else{
            m.findItem(R.id.Umenu_section_2).setTitle("Mi Consumo");
            m.findItem(R.id.Umenu_section_3).setTitle("Mi Informacion");
        }

        navView.setNavigationItemSelectedListener(this);

        View header = navView.getHeaderView(0);
        txt_usuario = (TextView) header.findViewById(R.id.h_usuario);
        txt_usuario.setText(b.getString("nombres")+" "+b.getString("apellidos"));

        circleImageView = (CircleImageView) header.findViewById(R.id.h_circle_image);
        Glide.with(this)
                .load(b.getString("imgPerfil"))
                //.load("https://s22.postimg.cc/572fvlmg1/vlad-baranov-767980-unsplash.jpg")
                .into(circleImageView);

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menuadmin) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menuadmin);
        MenuItem m = menuadmin.findItem(R.id.btnSign_off);
        m.setTitle("Salir");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout_admin);
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        if(id == R.id.btnSign_off) {
            Intent intent = new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void PasoDeDatos(){
        b.putString("nombres", b.getString("nombres"));
        b.putString("apellidos", b.getString("apellidos"));
        b.putString("usuario", b.getString("usuario"));
        b.putString("clave", b.getString("clave"));
        b.putString("tipo", b.getString("tipo"));
    }


}