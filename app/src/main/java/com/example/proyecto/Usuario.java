package com.example.proyecto;

import java.io.Serializable;

public class Usuario implements Serializable {
    String usuario;
    String contrasena;
    public String getUsuario() {

        return usuario;
    }

    public void setUsuario(String usuario) {

        this.usuario = usuario;
    }
    public String getContrasena() {

        return contrasena;
    }

    public void setContrasena(String contrasena) {

        this.contrasena = contrasena;
    }
}
