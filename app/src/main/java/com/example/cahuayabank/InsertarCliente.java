package com.example.cahuayabank;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import UtilWCQ.UtilWCQ;

public class InsertarCliente extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    TextInputLayout tilIdCliente, tilNombre, tilApellido, tilEmail, tilTelefono;
    EditText edtIdCliente, edtNombre, edtApellido, edtEmail, edtTelefono;
    Button btnRegistrar;

    ProgressDialog progreso;
    RequestQueue requestQueue; //Cola de requerimientos para que la app espere a que responda la BD
    JsonObjectRequest jsonObjectRequest;//adatar a la forma que necesito = webService

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_cliente);

        tilIdCliente=findViewById(R.id.tilIdCliente);
        tilNombre=findViewById(R.id.tilNombreCliente);
        tilApellido=findViewById(R.id.tilApellidoCliente);
        tilEmail=findViewById(R.id.tilEmailCliente);
        tilTelefono=findViewById(R.id.tilTelefonoCliente);

        requestQueue= Volley.newRequestQueue(this);

        btnRegistrar=findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarCliente();
            }
        });

        edtIdCliente=tilIdCliente.getEditText().findViewById(R.id.edtIdCliente);
        edtNombre=tilNombre.getEditText().findViewById(R.id.edtNombreCliente);
        edtApellido=tilApellido.getEditText().findViewById(R.id.edtApellidoCliente);
        edtEmail=tilEmail.getEditText().findViewById(R.id.edtEmailCliente);
        edtTelefono=tilTelefono.getEditText().findViewById(R.id.edtTelefonoCliente);



    }

    private void registrarCliente() {
        progreso=new ProgressDialog(this);
        progreso.setMessage("Insertando");
        progreso.show();
        String idc=edtIdCliente.getText().toString();
        String nom=edtNombre.getText().toString();
        String ape=edtApellido.getText().toString();
        String ema=edtEmail.getText().toString();
        String tel=edtTelefono.getText().toString();

        String url= UtilWCQ.RUTA+"insertarcliente.php?idcliente="+idc+"&nombres="+nom+"&apellidos="+ape+"&email="+"&telefono="+tel;
        url= url.replace(" ","%20");
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();
        Toast.makeText(this, "Registro Correcto", Toast.LENGTH_SHORT).show();
        edtIdCliente.setText("");
        edtNombre.setText("");
        edtApellido.setText("");
        edtEmail.setText("");
        edtTelefono.setText("");
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(this, "Error de inserción", Toast.LENGTH_SHORT).show();
        Log.i("error",error.toString());
    }
}