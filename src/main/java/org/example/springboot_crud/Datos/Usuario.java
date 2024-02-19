package org.example.springboot_crud.Datos;

import java.util.List;

public class Usuario {
    private String usuario,password,email;
    private List<Libro> libroList;

    public Usuario(String usuario, String password, String email, List<Libro> libroList) {
        this.usuario = usuario;
        this.password = password;
        this.email = email;
        this.libroList = libroList;
    }


    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Libro> getLibroList() {
        return libroList;
    }

    public void setLibroList(List<Libro> libroList) {
        this.libroList = libroList;
    }
}
