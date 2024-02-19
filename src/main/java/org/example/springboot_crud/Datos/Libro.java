package org.example.springboot_crud.Datos;

public class Libro {

    private String titulo, autor, sinopsis;

    public Libro(String titulo, String autor, String sinopsis) {
        this.titulo = titulo;
        this.autor = autor;
        this.sinopsis = sinopsis;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }
}
