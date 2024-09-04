package com.example.proyecto;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto.Metodos.Gasto;
import com.example.proyecto.auth_user.DBHelper_auth;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ModificarGastoActivity extends AppCompatActivity {

    private TextInputEditText editFecha, editDescripcion, editCantidad;
    private Button btnGuardar;
    private int gastoId;
    private DBHelper_auth dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_gasto);

        // Obtener el ID del gasto desde el Intent
        gastoId = getIntent().getIntExtra("gastoId", -1);

        if (gastoId == -1) {
            // Manejar el caso donde no se recibe correctamente el ID del gasto
            finish();
            return;
        }

        // Inicializar vistas
        editFecha = findViewById(R.id.editFecha);
        editDescripcion = findViewById(R.id.editDescripcion);
        editCantidad = findViewById(R.id.editCantidad);
        btnGuardar = findViewById(R.id.btnGuardar);
        dbHelper = new DBHelper_auth(this);

        // Obtener detalles del gasto desde la base de datos
        Gasto gasto = dbHelper.obtenerGastoPorId(gastoId);

        if (gasto == null) {
            // Manejar el caso donde el gasto no existe o no se puede encontrar
            finish();
            return;
        }

        // Cargar detalles del gasto en los campos editables
        editFecha.setText(gasto.getFecha());
        editDescripcion.setText(gasto.getDescripcion());
        editCantidad.setText(String.valueOf(gasto.getCantidadGasto()));

        // Configurar el selector de fecha para editFecha
        editFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDatePicker();
            }
        });

        // Funcionalidad del botón Guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nuevaFecha = editFecha.getText().toString();
                String nuevaDescripcion = editDescripcion.getText().toString();
                String nuevaCantidadStr = editCantidad.getText().toString();

                if (nuevaFecha.isEmpty() || nuevaDescripcion.isEmpty() || nuevaCantidadStr.isEmpty()) {
                    Toast.makeText(ModificarGastoActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                double nuevaCantidad = Double.parseDouble(nuevaCantidadStr);

                // Actualizar el gasto en la base de datos
                boolean actualizado = dbHelper.actualizarGasto(gastoId, nuevaFecha, nuevaDescripcion, nuevaCantidad);

                if (actualizado) {
                    Toast.makeText(ModificarGastoActivity.this, "Gasto actualizado correctamente", Toast.LENGTH_SHORT).show();

                    // Redirigir a DetalleGastoActivity para ver los cambios
                    Intent intent = new Intent(ModificarGastoActivity.this, DetalleGastoActivity.class);
                    intent.putExtra("gastoId", gastoId);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ModificarGastoActivity.this, "Error al actualizar el gasto", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void mostrarDatePicker() {
        // Obtener la fecha actual del editFecha si está definida
        String fechaActual = editFecha.getText().toString();
        Calendar calendario = Calendar.getInstance();
        if (!fechaActual.isEmpty()) {
            try {
                Date date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(fechaActual);
                calendario.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Crear y mostrar el DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Formatear la fecha seleccionada y mostrarla en el editFecha
                        Calendar nuevaFecha = Calendar.getInstance();
                        nuevaFecha.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        String fechaSeleccionada = sdf.format(nuevaFecha.getTime());
                        editFecha.setText(fechaSeleccionada);
                    }
                },
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
}
