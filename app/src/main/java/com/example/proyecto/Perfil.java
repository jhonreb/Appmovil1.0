package com.example.proyecto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto.auth_user.DBHelper_auth;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

public class Perfil extends AppCompatActivity {
    private TextInputEditText textInputNombre;
    private TextInputEditText textInputApellido;
    private TextInputEditText textInputFechaNacimiento;
    private TextInputEditText textInputDireccion;
    private TextInputEditText textInputTelefono;
    private Button buttonGuardar;
    private DBHelper_auth dbHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil);

        dbHelper = new DBHelper_auth(this);

        textInputNombre = findViewById(R.id.textInputNombre);
        textInputApellido = findViewById(R.id.textInputApellido);
        textInputFechaNacimiento = findViewById(R.id.textInputFechaNacimiento);
        textInputDireccion = findViewById(R.id.textInputDireccion);
        textInputTelefono = findViewById(R.id.textInputTelefono);
        buttonGuardar = findViewById(R.id.buttonGuardar);

        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        userId = preferences.getInt("userId", -1);

        if (userId == -1) {
            // Manejar error o redirigir al usuario
            finish();
            return;
        }

        // Cargar los datos del perfil si existen
        com.example.proyecto.Metodos.Perfil perfil = dbHelper.obtenerPerfil(userId);
        if (perfil != null) {
            textInputNombre.setText(perfil.getNombre());
            textInputApellido.setText(perfil.getApellido());
            textInputFechaNacimiento.setText(perfil.getFechaNacimiento());
            textInputDireccion.setText(perfil.getDireccion());
            textInputTelefono.setText(perfil.getTelefono());
        }

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = textInputNombre.getText().toString();
                String apellido = textInputApellido.getText().toString();
                String fechaNacimiento = textInputFechaNacimiento.getText().toString();
                String direccion = textInputDireccion.getText().toString();
                String telefono = textInputTelefono.getText().toString();

                // Verificar si se debe insertar o actualizar
                if (perfil == null) {
                    // Insertar nuevo perfil
                    boolean result = dbHelper.insertarPerfil(userId, nombre, apellido, fechaNacimiento, direccion, telefono);
                    if (result) {
                        Toast.makeText(Perfil.this, "Perfil creado con éxito", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Perfil.this, "Error al crear el perfil", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Actualizar perfil existente
                    boolean result = dbHelper.actualizarPerfil(userId, nombre, apellido, fechaNacimiento, direccion, telefono);
                    if (result) {
                        Toast.makeText(Perfil.this, "Perfil actualizado con éxito", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Perfil.this, "Error al actualizar el perfil", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Configuración adicional de interfaz de usuario si es necesario
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.menuHome) {
                Intent intent = new Intent(Perfil.this, Menu.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.menuProfile) {
                // Estamos en la actividad de perfil, no se necesita hacer nada
                return true;
            }
            return false;
        });
    }
}


