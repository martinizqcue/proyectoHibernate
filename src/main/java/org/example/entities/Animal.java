package org.example.entities;


import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "animal")
public class Animal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    @Enumerated(EnumType.STRING)
    private Animales especie;

    private Integer edad;

    @Column(name = "descripcion_perdida", length = 1000)
    private String descripcionPerdida;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @ManyToOne
    @JoinColumn(name = "familia_id")
    private Familia familia;

    //Constructores
    public Animal() {

    }

    /**
     *
     * @param nombre
     * @param especie
     * @param edad
     * @param descripcionPerdida
     * @param estado
     * @param familia
     */
    public Animal(String nombre, Animales especie, Integer edad, String descripcionPerdida, Estado estado, Familia familia) {
        this.nombre = nombre;
        this.especie = especie;
        this.edad = edad;
        this.descripcionPerdida = descripcionPerdida;
        this.estado = estado;
        this.familia = familia;
    }

    //Getters y Setters


    public Integer getId() {
        return id;
    }


    public String getNombre() {
        return nombre;
    }

    public Animales getEspecie() {
        return especie;
    }

    public Integer getEdad() {
        return edad;
    }

    public String getDescripcionPerdida() {
        return descripcionPerdida;
    }

    public Estado getEstado() {
        return estado;
    }

    public Familia getFamilia() {
        return familia;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public void setEspecie(Animales especie) {
        this.especie = especie;
    }

    public void setDescripcionPerdida(String descripcionPerdida) {
        this.descripcionPerdida = descripcionPerdida;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }


    @Override
    public String toString() {
        return "=== Animal ===\n" +
                "ID: " + id + "\n" +
                "Nombre: " + nombre + "\n" +
                "Especie: " + especie + "\n" +
                "Edad: " + edad + " años\n" +
                "Descripción de pérdida: " + descripcionPerdida + "\n" +
                "Estado: " + estado + "\n" +
                "Familia: " + (familia != null ? familia.toString() : "Sin asignar") + "\n";
    }

}
