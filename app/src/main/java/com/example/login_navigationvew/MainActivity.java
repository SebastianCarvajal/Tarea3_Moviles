package com.example.login_navigationvew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    private static final String URL3 = "https://smart-meter-project-35c6b-default-rtdb.firebaseio.com/Usuarios.json";

    TextView txtUser;
    TextView txtPass;
    Button btnLogin;

    Bundle b = new Bundle();
    String nombres = "", apellidos = "", usuario = "", clave = "", tipo = "", imgPerfil = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

        txtUser=(TextView) findViewById(R.id.txtUser);
        txtPass=(TextView) findViewById(R.id.txtPassword);
        txtUser.setText("");
        txtPass.setText("");

        btnLogin=(Button) findViewById(R.id.btn_log_in);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //IniciarSesion("A");
                jsonArrayRequestFireBase();
            }
        });
    }

    private void jsonArrayRequestFireBase(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, URL3, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray names = response.names();
                        int count = 0;
                        for(int i = 0; i <= response.length(); i++){
                            try{
                                JSONObject jsonObject = new JSONObject(response.get(names.getString(i)).toString());
                                //Toast.makeText(MainActivity.this,jsonObject.getString("Nombres"),Toast.LENGTH_LONG).show();
                                nombres = jsonObject.getString("Nombres");
                                apellidos = jsonObject.getString("Apellidos");
                                usuario = jsonObject.getString("Usuario");
                                clave = jsonObject.getString("Clave");
                                tipo = jsonObject.getString("Tipo");
                                imgPerfil = jsonObject.getString("ImgPerfil");
                                if(usuario.equals(txtUser.getText().toString())){
                                    count++;
                                    //Toast.makeText(MainActivity.this,"Contando",Toast.LENGTH_LONG).show();
                                    break;
                                }
                            }catch (JSONException e){}

                        }
                        if(count > 0){
                            if(clave.equals(getMD5(txtPass.getText().toString()))){
                                //Toast.makeText(MainActivity.this,tipo,Toast.LENGTH_LONG).show();
                                IniciarSesion(tipo);
                            }else{
                                Toast.makeText(MainActivity.this,"WARNING \nIncorrect password",Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this,"WARNING \nUser not found",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    public void IniciarSesion(String rol) {
        Intent intent = new Intent(this, AdminActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PasoDeDatos();
        intent.putExtras(b);
        startActivity(intent);

    }

    public void PasoDeDatos(){
        b.putString("nombres", nombres);
        b.putString("apellidos", apellidos);
        b.putString("usuario", usuario);
        b.putString("clave", clave);
        b.putString("tipo", tipo);
        b.putString("imgPerfil", imgPerfil);
    }

    private String getMD5(final String s) {
        try{
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for(int i = 0; i < messageDigest.length; i++){
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while(h.length() < 2){
                    h = "0" + h;
                }
                hexString.append(h);
            }
            return hexString.toString();
        }catch (NoSuchAlgorithmException e){
            Log.e("MD5", "md5() NoSuchAlgorithmException: " + e.getMessage());
        }
        return "";
    }

    public void llenarDatos(){
        nombres = "Carlos Sebastian";
        apellidos = "Carvajal Florencia";
        usuario = "ccarvajalf";
        clave = "12345";
        tipo = "A";
    }
}