package com.example.practica1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText etCedula, etNombres, etFecha, etCiudad, etCorreo, etTelefono;
    private RadioGroup rgGenero;
    private Button btnLimpiar, btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        etCedula = findViewById(R.id.etCedula);
        etNombres = findViewById(R.id.etNombres);
        etFecha = findViewById(R.id.etFecha);
        etCiudad = findViewById(R.id.etCiudad);
        etCorreo = findViewById(R.id.etCorreo);
        etTelefono = findViewById(R.id.etTelefono);
        rgGenero = findViewById(R.id.rgGenero);
        btnLimpiar = findViewById(R.id.btnLimpiar);
        btnEnviar = findViewById(R.id.btnEnviar);

        etFecha.setOnClickListener(v -> mostrarCalendarioSpinner());
        btnLimpiar.setOnClickListener(v -> limpiarFormulario());
        btnEnviar.setOnClickListener(v -> enviarFormulario());
    }

    // CALENDARIO TIPO "SPINNER" (Rueditas)
    private void mostrarCalendarioSpinner() {
        final Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int anio = calendar.get(Calendar.YEAR);

        DatePickerDialog datePicker = new DatePickerDialog(this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                (view, year, month, dayOfMonth) -> {
                    String diaFmt = (dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                    String mesFmt = ((month + 1) < 10) ? "0" + (month + 1) : String.valueOf(month + 1);
                    etFecha.setText(diaFmt + "/" + mesFmt + "/" + year);
                }, anio, mes, dia);

        datePicker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        datePicker.show();
    }

    private void limpiarFormulario() {
        etCedula.setText("");
        etNombres.setText("");
        etFecha.setText("");
        etCiudad.setText("");
        etCorreo.setText("");
        etTelefono.setText("");
        rgGenero.clearCheck();
        etCedula.requestFocus();
    }

    private void enviarFormulario() {
        String cedula = etCedula.getText().toString().trim();
        String nombres = etNombres.getText().toString().trim().toUpperCase();
        String fecha = etFecha.getText().toString().trim();
        String ciudad = etCiudad.getText().toString().trim().toUpperCase();
        String correo = etCorreo.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();

        String genero = "";
        int selectedId = rgGenero.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton rb = findViewById(selectedId);
            genero = rb.getText().toString();
        }

        // --- VALIDACIONES ESTRICTAS UTEQ ---

        if (cedula.isEmpty() || cedula.length() != 10) {
            etCedula.setError("Cédula debe tener 10 dígitos");
            return;
        }

        // Validación Nombres: Solo letras y espacios (NO números)
        if (nombres.isEmpty()) {
            etNombres.setError("Campo requerido"); return;
        }
        if (!nombres.matches("^[A-ZÑÁÉÍÓÚ\\s]+$")) {
            etNombres.setError("Solo letras (No números)");
            return;
        }

        if (fecha.isEmpty()) {
            etFecha.setError("Seleccione Fecha"); return;
        }

        // Validación Ciudad: Solo letras
        if (ciudad.isEmpty()) {
            etCiudad.setError("Campo requerido"); return;
        }
        if (!ciudad.matches("^[A-ZÑÁÉÍÓÚ\\s]+$")) {
            etCiudad.setError("Solo letras (No números)");
            return;
        }

        if (genero.isEmpty()) {
            Toast.makeText(this, "Seleccione Género", Toast.LENGTH_SHORT).show(); return;
        }

        if (correo.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            etCorreo.setError("Correo inválido"); return;
        }

        // Validación Teléfono 10 dígitos y empieza con 09
        if (telefono.isEmpty()) {
            etTelefono.setError("Campo requerido"); return;
        }
        if (telefono.length() != 10) {
            etTelefono.setError("Debe tener 10 dígitos"); return;
        }
        if (!telefono.startsWith("09")) {
            etTelefono.setError("Debe iniciar con 09"); return;
        }


        Intent intent = new Intent(MainActivity.this, DetalleActivity.class);
        intent.putExtra("DATOS", "Cédula: " + cedula + "\n" +
                "Nombres: " + nombres + "\n" +
                "Fecha: " + fecha + "\n" +
                "Ciudad: " + ciudad + "\n" +
                "Género: " + genero + "\n" +
                "Correo: " + correo + "\n" +
                "Teléfono: " + telefono);
        startActivity(intent);
    }
}