package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto.Metodos.Gasto;
import com.example.proyecto.auth_user.DBHelper_auth;

public class DetalleGastoActivity extends AppCompatActivity {

    private TextView txtFecha, txtDescripcion, txtCantidad;
    private Button BtnRegresar, BtnModificar, BtnEliminar;
    private int gastoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_gasto);

        // Obtener el ID del gasto desde el Intent
        gastoId = getIntent().getIntExtra("gastoId", -1);

        if (gastoId == -1) {
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
        Gasto gasto = dbHelper.obtenerGastoPorId(gastoId); // Método ficticio, debes implementarlo en tu DBHelper

        // Verificar si se encontró el gasto en la base de datos
        if (gasto == null) {
            // Manejar el caso donde el gasto no existe o no se puede encontrar
            finish();
            return;
        }

        // Asignar los detalles del gasto a las vistas correspondientes
        txtFecha.setText("Fecha del gasto: " + gasto.getFecha());
        txtDescripcion.setText("Descripción del gasto: " + gasto.getDescripcion());
        txtCantidad.setText("Cantidad del gasto: " + gasto.getCantidadGasto());

        // Funcionalidad del botón Regresar
        BtnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirigir a ViewGastos con el userId adecuado
                Intent intent = new Intent(DetalleGastoActivity.this, ViewGastos.class);
                intent.putExtra("userId", gasto.getUserId());
                startActivity(intent);
                finish(); // Finalizar esta actividad para no volver atrás con el botón de retroceso
            }
        });

        // Funcionalidad del botón Eliminar
        BtnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean eliminado = dbHelper.eliminarGasto(gastoId);

                if (eliminado) {
                    Toast.makeText(DetalleGastoActivity.this, "Gasto eliminado correctamente", Toast.LENGTH_SHORT).show();
                    // Redirigir a ViewGastos con el userId adecuado
                    Intent intent = new Intent(DetalleGastoActivity.this, ViewGastos.class);
                    intent.putExtra("userId", gasto.getUserId());
                    startActivity(intent);
                    finish(); // Finalizar esta actividad para no volver atrás con el botón de retroceso
                } else {
                    Toast.makeText(DetalleGastoActivity.this, "Error al eliminar el gasto", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Funcionalidad del botón Modificar
        BtnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalleGastoActivity.this, ModificarGastoActivity.class);
                intent.putExtra("gastoId", gastoId);
                startActivity(intent);
            }
        });

    }
}



