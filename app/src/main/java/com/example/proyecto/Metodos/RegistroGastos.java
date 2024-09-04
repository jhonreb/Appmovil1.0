package com.example.proyecto.Metodos;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistroGastos {

    private double costo;
    private String fecha;
    private String categoria;
    private String descripcion;
    private String repeticion;

    public RegistroGastos(double costo, String fecha, String categoria, String descripcion, String repeticion){
        this.costo = costo;
        this.fecha = fecha;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.repeticion = repeticion;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRepeticion() {
        return repeticion;
    }

    public void setRepeticion(String repeticion) {
        this.repeticion = repeticion;
    }
}
