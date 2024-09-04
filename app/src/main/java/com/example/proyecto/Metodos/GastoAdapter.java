package com.example.proyecto.Metodos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import com.example.proyecto.DetalleGastoActivity;
import com.example.proyecto.R;

public class GastoAdapter extends RecyclerView.Adapter<GastoAdapter.GastoViewHolder> {

    private Context context;
    private List<Gasto> listaGastos;

    public GastoAdapter(Context context) {
        this.context = context;
    }

    // Método para actualizar la lista de gastos
    public void setGastos(List<Gasto> listaGastos) {
        this.listaGastos = listaGastos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GastoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el layout del item de gasto
        View view = LayoutInflater.from(context).inflate(R.layout.item_gasto, parent, false);
        return new GastoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GastoViewHolder holder, int position) {
        // Obtener el gasto en la posición actual
        Gasto gasto = listaGastos.get(position);

        // Asignar valores a las vistas del ViewHolder
        holder.txtFecha.setText(gasto.getFecha());
        holder.txtDescripcion.setText(gasto.getDescripcion());
        holder.txtCantidad.setText(String.valueOf(gasto.getCantidadGasto()));

        // Manejar clic en el elemento del RecyclerView
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Al hacer clic, enviar a la actividad de detalle del gasto
                Intent intent = new Intent(context, DetalleGastoActivity.class);
                intent.putExtra("gastoId", gasto.getId()); // Envía el ID del gasto o toda la información necesaria
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaGastos != null ? listaGastos.size() : 0;
    }

    // ViewHolder para mantener las referencias de las vistas de cada item de gasto
    public static class GastoViewHolder extends RecyclerView.ViewHolder {

        TextView txtFecha, txtDescripcion, txtCantidad;

        public GastoViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializar las vistas
            txtFecha = itemView.findViewById(R.id.txtFechaIn);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcionIn);
            txtCantidad = itemView.findViewById(R.id.txtCantidadIn);
            // Configura más vistas si es necesario
        }
    }
}


