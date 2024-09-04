package com.example.proyecto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.anychart.chart.common.dataentry.DataEntry;
import com.example.proyecto.Metodos.Gasto;
import com.example.proyecto.auth_user.DBHelper_auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleToLongFunction;

public class ViewGastosTotales extends AppCompatActivity {
    ListView listView;
    TextView totalSum;
    Button btnRegresarGra;
    private DBHelper_auth dbHelper;
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_gastos_totales);
        dbHelper = new DBHelper_auth(this);
        listView = findViewById(R.id.listView);
        totalSum = findViewById(R.id.totalSum);
        userId = obtenerUserIdActual();
        mostrarTotal();
        double sumTotal = dbHelper.obtenerTotalGastos(userId);
        totalSum.setText("Total:" + sumTotal);
        btnRegresarGra = findViewById(R.id.btnRegresarGra);
        btnRegresarGra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewGastosTotales.this, InformesGastos.class);
                startActivity(intent);
            }
        });
    }

    private void mostrarTotal(){
        List<Gasto> listaGastos =  dbHelper.obtenerGastos(userId);

        if (listaGastos.isEmpty()) {
            Toast.makeText(ViewGastosTotales.this, "No hay gastos registrados", Toast.LENGTH_SHORT).   show();
            return;
        }

        Map<Integer, Double> totalPorCategoria = new HashMap<>();
        for(Gasto gasto : listaGastos){
            int categoria = gasto.getIdCategoria();
            double monto = gasto.getCantidadGasto();
            if(totalPorCategoria.containsKey(categoria)){
                totalPorCategoria.put(categoria, totalPorCategoria.get(categoria) + monto);
            }else{
                totalPorCategoria.put(categoria, monto);
            }
        }
        List<Map<String, String>> dataList = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry: totalPorCategoria.entrySet()){
            Map<String, String> dataMap = new HashMap<>();
            String nombreCategoria = seleccionarCategoria(entry.getKey());
            dataMap.put("Categoria", nombreCategoria);
            dataMap.put("Total", String.format("$%.2f", entry.getValue()));
            dataList.add(dataMap);
        }
        String[] from = {"Categoria", "Total"};
        int[] to = {R.id.textCategory, R.id.textTotal};

        SimpleAdapter adapter1 = new SimpleAdapter(this, dataList, R.layout.gasto_total, from, to);
        listView.setAdapter(adapter1);
    }
    private int obtenerUserIdActual() {
        // Implement this function according to your authentication logic
        // For demonstration purposes, assuming user ID is stored in SharedPreferences
        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        return preferences.getInt("userId", -1); // Replace -1 with default value if not found
    }
    private String seleccionarCategoria(int categoria){
        String categoriaID = "";
        switch (categoria){
            case 1: categoriaID = "Comestibles"; break;
            case 2: categoriaID = "Restaurantes"; break;
            case 3: categoriaID = "Bebidas"; break;
            case 4: categoriaID = "Matrícula"; break;
            case 5: categoriaID = "Materiales escolares"; break;
            case 6: categoriaID = "Cursos"; break;
            case 7: categoriaID = "Cine"; break;
            case 8: categoriaID = "Conciertos"; break;
            case 9: categoriaID = "Recreativos"; break;
            case 10: categoriaID = "Consultas médicas"; break;
            case 11: categoriaID = "Medicamentos"; break;
            case 12: categoriaID = "Seguros médicos"; break;
            case 13: categoriaID = "Transporte"; break;
            case 14: categoriaID = "Mantenimiento"; break;
            case 15: categoriaID = "Viajes"; break;
            case 16: categoriaID = "Alquiler"; break;
            case 17: categoriaID = "Servicios públicos"; break;
            case 18: categoriaID = "Mantenimiento"; break;
            case 19: categoriaID = "Ropa"; break;
            case 20: categoriaID = "Calzado"; break;
            case 21: categoriaID = "Accesorios"; break;
            case 22: categoriaID = "Alojamiento"; break;
            case 23: categoriaID = "Transporte publico"; break;
            case 24: categoriaID = "Turismo"; break;
            case 25: categoriaID = "Otros"; break;
        }
        return categoriaID;
    }
}