package com.example.proyecto;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto.auth_user.DBHelper_auth;

import java.util.Calendar;
import java.util.Locale;


public class GestionIngresos extends AppCompatActivity {

    Context context;
    Spinner spnIngresoCategoria;
    EditText txtIngresoMonto, txtIngresoFecha, txtIngresoDescripcion;
    Button btnGestionRegresar, btnIngresoRegistrar, btnIngresoMostrar;
    DBHelper_auth reingreso;
    //@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gestion_ingresos);

        spnIngresoCategoria = findViewById(R.id.spnIngresoCategoria);
        txtIngresoMonto = findViewById(R.id.txtIngresoMonto);
        txtIngresoFecha = findViewById(R.id.txtIngresoFecha);
        txtIngresoDescripcion = findViewById(R.id.txtIngresoDescripcion);
        btnGestionRegresar = findViewById(R.id.btnGestionRegresar);
        btnIngresoRegistrar = findViewById(R.id.btnIngresoRegistrar);
        btnIngresoMostrar = findViewById(R.id.btnIngresoMostrar);
        reingreso = new DBHelper_auth(this);

        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        int userId = preferences.getInt("userId", -1);

        if (userId == -1) {
            // Maneja el error apropiadamente (por ejemplo, redirigir al login)
            finish();
            return;
        }

        txtIngresoFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        txtIngresoFecha.setKeyListener(null);


        btnIngresoRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fecha = txtIngresoFecha.getText().toString();
                double monto = Double.parseDouble(txtIngresoMonto.getText().toString());
                String descripcion = txtIngresoDescripcion.getText().toString();
                int idCategoria = Categoria();

                boolean registroExitoso = reingreso.insertarIngreso(userId, fecha, monto, idCategoria, descripcion);
                if (registroExitoso) {
                    Toast.makeText(GestionIngresos.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(GestionIngresos.this, "ERROR AL REGISTRAR", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnGestionRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReg = new Intent(GestionIngresos.this, Menu.class);
                GestionIngresos.this.startActivity(intentReg);
            }
        });

        btnIngresoMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad ViewGastos y pasar el ID del usuario como extra
                Intent intent = new Intent(GestionIngresos.this, ViewIngreso.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public int Categoria() {return spnIngresoCategoria.getSelectedItemPosition() + 1; }
    private void showDatePickerDialog() {
        // Obtener la fecha actual para establecerla como predeterminada en el diálogo
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Crear el DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Formatear la fecha seleccionada
                        String selectedDate = String.format(Locale.getDefault(), "%02d-%02d-%d", dayOfMonth, monthOfYear + 1, year);
                        txtIngresoFecha.setText(selectedDate);
                    }
                }, year, month, day);

        // Mostrar el diálogo
        datePickerDialog.show();
    }
}