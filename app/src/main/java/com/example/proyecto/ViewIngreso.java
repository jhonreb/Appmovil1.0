package com.example.proyecto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.Metodos.Ingreso;
import com.example.proyecto.Metodos.IngresoAdapter;
import com.example.proyecto.auth_user.DBHelper_auth;

import java.util.List;
public class ViewIngreso extends AppCompatActivity {
    private RecyclerView recyclerView;
    private IngresoAdapter ingresoAdapter;
    private DBHelper_auth dbHelper;
    private int userId; // Variable para almacenar userId

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ingreso);

        recyclerView = findViewById(R.id.recycler_view_ingreso);
        dbHelper = new DBHelper_auth(this);

        // Obtener userId del Intent
        userId = getIntent().getIntExtra("userId", -1);

        if (userId == -1) {
            // Manejar el caso donde no se recibe userId correctamente
            finish();
            return;
        }

        // Configurar RecyclerView y Adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ingresoAdapter = new IngresoAdapter(this);
        recyclerView.setAdapter(ingresoAdapter);

        // Obtener y mostrar gastos del usuario
        List<Ingreso> listaIngreso = dbHelper.obtenerIngreso(userId);
        ingresoAdapter.setIngresos(listaIngreso);

        // Configurar el botón de regresar
        Button btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Al hacer clic en Regresar, iniciar la actividad RegistroGastos
                Intent intent = new Intent(ViewIngreso.this, GestionIngresos.class);
                startActivity(intent);
            }
        });
    }
}
