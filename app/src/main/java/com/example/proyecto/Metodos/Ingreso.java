package com.example.proyecto.Metodos;

public class Ingreso {
    private int id;
    private int userId;
    private String fecha;
    private double cantidadIngreso;
    private int idCategoria;
    private String descripcion;

    public Ingreso(int id, int userId, String fecha, double cantidadIngreso, int idCategoria, String descripcion) {
        this.id = id;
        this.userId = userId;
        this.fecha = fecha;
        this.cantidadIngreso = cantidadIngreso;
        this.idCategoria = idCategoria;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getFecha() {
        return fecha;
    }

    public double getCantidadIngreso() {
        return cantidadIngreso;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return "Gasto{" +
                "id=" + id +
                ", userId=" + userId +
                ", fecha='" + fecha + '\'' +
                ", cantidadGasto=" + cantidadIngreso +
                ", idCategoria=" + idCategoria +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
