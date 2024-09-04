package com.example.proyecto;

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

public class RegistroGasto extends AppCompatActivity {

    Context context;
    Spinner spnCategoria;
    EditText txtMonto, txtDescripcion, txtFecha;
    Button btnRegistroRegresar, btnRegistrarGasto, btnMostrarGastos;
    DBHelper_auth regasto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro_gasto);

        spnCategoria = findViewById(R.id.spnCategoria);
        txtMonto = findViewById(R.id.txtMonto);
        txtDescripcion = findViewById(R.id.txtDescripcionIn);
        txtFecha = findViewById(R.id.txtFechaIn);
        btnRegistrarGasto = findViewById(R.id.btnRegistrarGasto);
        btnRegistroRegresar = findViewById(R.id.btnRegistroRegresar);
        btnMostrarGastos = findViewById(R.id.btnMostrarGastos); // Asegúrate de que el ID sea correcto en el layout
        regasto = new DBHelper_auth(this);

        // Recuperar el ID del usuario de SharedPreferences
        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        int userId = preferences.getInt("userId", -1);

        // Verifica que el userId sea válido
        if (userId == -1) {
            // Maneja el error apropiadamente (por ejemplo, redirigir al login)
            finish();
            return;
        }

        // Configurar el listener para el EditText de fecha
        txtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Deshabilitar la edición del EditText de fecha
        txtFecha.setKeyListener(null);

        btnRegistrarGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fecha = txtFecha.getText().toString();
                double costo = Double.parseDouble(txtMonto.getText().toString());
                String descripcion = txtDescripcion.getText().toString();
                int idCategoria = Categoria();

                boolean registroExitoso = regasto.insertarGasto(userId, fecha, costo, idCategoria, descripcion);
                if (registroExitoso) {
                    Toast.makeText(RegistroGasto.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegistroGasto.this, "ERROR AL REGISTRAR", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnRegistroRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReg = new Intent(RegistroGasto.this, Menu.class);
                RegistroGasto.this.startActivity(intentReg);
            }
        });

        btnMostrarGastos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad ViewGastos y pasar el ID del usuario como extra
                Intent intent = new Intent(RegistroGasto.this, ViewGastos.class);
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

    public int Categoria() {
        return spnCategoria.getSelectedItemPosition() + 1;
    }

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
                        txtFecha.setText(selectedDate);
                    }
                }, year, month, day);

        // Mostrar el diálogo
        datePickerDialog.show();
    }
}



