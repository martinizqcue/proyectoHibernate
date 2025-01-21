package org.example.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "familia")
public class Familia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private Integer edad;
    private String ciudad;

    public Familia() {

    }

    public Familia(Integer id, String nombre, Integer edad, String ciudad) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.ciudad = ciudad;
    }

    //Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "Familia{" + "id=" + id + ", nombre=" + nombre + ", edad=" + edad + ", ciudad=" + ciudad + '}';
    }

}
