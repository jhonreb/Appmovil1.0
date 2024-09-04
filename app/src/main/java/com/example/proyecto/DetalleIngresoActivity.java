package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto.Metodos.Ingreso;
import com.example.proyecto.auth_user.DBHelper_auth;

public class DetalleIngresoActivity extends AppCompatActivity {
    private TextView txtFecha, txtDescripcion, txtCantidad;
    private Button BtnRegresar, BtnModificar, BtnEliminar;
    private int ingresoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_ingreso);

        // Obtener el ID del gasto desde el Intent
        ingresoId = getIntent().getIntExtra("ingresoId", -1);

        if (ingresoId == -1) {
            // Manejar el caso donde no se recibe correctamente el ID del gasto
            finish();
            return;
        }

        // Inicializar vistas
        txtFecha = findViewById(R.id.txtFechaIn);
        txtDescripcion = findViewById(R.id.txtDescripcionIn);
        txtCantidad = findViewById(R.id.txtCantidadIn);
        BtnRegresar = findViewById(R.id.btnRegresar);
        BtnModificar = findViewById(R.id.btnModificar);
        BtnEliminar = findViewById(R.id.btnEliminar);

        // Aquí deberías implementar la lógica para obtener los detalles reales del gasto según su ID
        DBHelper_auth dbHelper = new DBHelper_auth(this); // Instancia de tu DBHelper o clase de acceso a datos

        // Obtener detalles del gasto desde la base de datos o cualquier otra fuente de datos
        Ingreso ingreso = dbHelper.obtenerIngresoPorId(ingresoId); // Método ficticio, debes implementarlo en tu DBHelper

        // Verificar si se encontró el gasto en la base de datos
        if (ingreso == null) {
            // Manejar el caso donde el gasto no existe o no se puede encontrar
            finish();
            return;
        }

        // Asignar los detalles del gasto a las vistas correspondientes
        txtFecha.setText("Fecha del ingreso: " + ingreso.getFecha());
        txtDescripcion.setText("Descripción del ingreso: " + ingreso.getDescripcion());
        txtCantidad.setText("Cantidad del ingreso: " + ingreso.getCantidadIngreso());

        // Funcionalidad del botón Regresar
        BtnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirigir a ViewGastos con el userId adecuado
                Intent intent = new Intent(DetalleIngresoActivity.this, ViewIngreso.class);
                intent.putExtra("userId", ingreso.getUserId());
                startActivity(intent);
                finish(); // Finalizar esta actividad para no volver atrás con el botón de retroceso
            }
        });
        // Funcionalidad del botón Eliminar
        BtnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean eliminado = dbHelper.eliminarIngreso(ingresoId);

                if (eliminado) {
                    Toast.makeText(DetalleIngresoActivity.this, "Gasto eliminado correctamente", Toast.LENGTH_SHORT).show();
                    // Redirigir a ViewGastos con el userId adecuado
                    Intent intent = new Intent(DetalleIngresoActivity.this, ViewIngreso.class);
                    intent.putExtra("userId", ingreso.getUserId());
                    startActivity(intent);
                    finish(); // Finalizar esta actividad para no volver atrás con el botón de retroceso
                } else {
                    Toast.makeText(DetalleIngresoActivity.this, "Error al eliminar el gasto", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Funcionalidad del botón Modificar
        BtnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalleIngresoActivity.this, ModificarGastoActivity.class);
                intent.putExtra("gastoId", ingresoId);
                startActivity(intent);
            }
        });
    }
}