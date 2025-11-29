package com.example.practica1;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetalleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        TextView tvDatos = findViewById(R.id.tvDatosCompletos);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String datos = extras.getString("DATOS");
            tvDatos.setText(datos);
        }
    }
}