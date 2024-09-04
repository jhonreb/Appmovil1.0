package com.example.proyecto;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto.R;
import com.example.proyecto.auth_user.DBHelper_auth;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;


public class Menu extends AppCompatActivity {

    Button btnRegistrarGasto, btnGestionIngresos, btnCategorizacion, btnInformes, btnAlertas, btnSalir;

    @Override
    protected void onStart() {
        super.onStart();

        // Verificar si el usuario está autenticado
        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        if (!isLoggedIn) {
            // Si el usuario no está autenticado, redirigir a la pantalla de inicio de sesión
            Intent intent = new Intent(Menu.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);



        btnRegistrarGasto = findViewById(R.id.btnRegistrarGasto);
        btnRegistrarGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, RegistroGasto.class);
                startActivity(intent);
            }
        });

        btnGestionIngresos = findViewById(R.id.btnGestionIngresos);
        btnGestionIngresos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, GestionIngresos.class);
                startActivity(intent);
            }
        });

        btnCategorizacion = findViewById(R.id.btnCategorizacion);
        btnCategorizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, CategorizacionGastos.class);
                startActivity(intent);
            }
        });

        btnInformes = findViewById(R.id.btnInformes);
        btnInformes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el ID del usuario desde SharedPreferences
                SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
                int userId = preferences.getInt("userId", -1);

                // Iniciar InformesGastos con el ID del usuario
                Intent intent = new Intent(Menu.this, InformesGastos.class);
                intent.putExtra("userId", userId); // Pasar el userId como extra
                startActivity(intent);
            }
        });

        btnAlertas = findViewById(R.id.btnAlertas);
        btnAlertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
                int userId = preferences.getInt("userId", -1);
                if (userId != -1) {
                    DBHelper_auth dbHelper = new DBHelper_auth(Menu.this);
                    double totalGastos = dbHelper.obtenerTotalGastos(userId);

                    // Crear y mostrar el AlertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(Menu.this);
                    builder.setTitle("Total Gastos");
                    builder.setMessage("El total de tus gastos es: " + totalGastos);
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        btnSalir = findViewById(R.id.Exit);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Eliminar datos del usuario almacenados en SharedPreferences
                SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();

                // Redirigir a la pantalla de inicio de sesión
                Intent intent = new Intent(Menu.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // Finaliza Menu activity para evitar que el usuario regrese a ella con el botón de retroceso
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.menuHome) {
                    // Lógica para navegar a la actividad de inicio
                    Intent intent = new Intent(Menu.this, Menu.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.menuProfile) {
                    // Lógica para navegar a la actividad de perfil
                    SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
                    int userId = preferences.getInt("userId", -1);
                    Intent intent = new Intent(Menu.this, Perfil.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }
}

