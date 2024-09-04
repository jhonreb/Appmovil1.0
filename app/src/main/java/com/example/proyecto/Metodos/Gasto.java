package com.example.proyecto.Metodos;

public class Gasto {
    private int id;
    private int userId;
    private String fecha;
    private double cantidadGasto;
    private int idCategoria;
    private String descripcion;

    public Gasto(int id, int userId, String fecha, double cantidadGasto, int idCategoria, String descripcion) {
        this.id = id;
        this.userId = userId;
        this.fecha = fecha;
        this.cantidadGasto = cantidadGasto;
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

    public double getCantidadGasto() {
        return cantidadGasto;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String seleccionarCategoria(int categoria){
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
    @Override
    public String toString() {
        return "Gasto{" +
                "id=" + id +
                ", userId=" + userId +
                ", fecha='" + fecha + '\'' +
                ", cantidadGasto=" + cantidadGasto +
                ", idCategoria=" + idCategoria +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
